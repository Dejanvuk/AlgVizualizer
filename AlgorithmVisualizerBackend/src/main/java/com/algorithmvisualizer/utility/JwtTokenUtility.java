package com.algorithmvisualizer.utility;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.algorithmvisualizer.config.CustomUserDetails;
import com.algorithmvisualizer.config.JwtProperties;
import com.algorithmvisualizer.model.User;
import com.algorithmvisualizer.repository.UserRepository;
import com.algorithmvisualizer.response.Status;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenUtility {
	@Autowired
	JwtProperties jwtProps;
	private static final Logger log = LoggerFactory.getLogger(JwtTokenUtility.class);
	
	@Autowired
	UserRepository userRepository;
	
	public String generateToken(Authentication authentication) {
        Date expireDate = new Date(new Date().getTime() + jwtProps.getEXPIRATION_TIME());
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String token = Jwts.builder()
        		.setSubject(user.getUsername())
        		.setIssuedAt(new Date())
        		.setExpiration(expireDate)
        		.setAudience(jwtProps.getTOKEN_AUDIENCE())
        		.setIssuer(jwtProps.getTOKEN_ISSUER())
        		.signWith(SignatureAlgorithm.HS512, jwtProps.getJWT_SECRET())
        		.compact();
        return token;
	}
	
	public String generateTokenByEmail(String email) {
        Date expireDate = new Date(new Date().getTime() + jwtProps.getEXPIRATION_TIME());
        String token = Jwts.builder()
        		.setSubject(email)
        		.setIssuedAt(new Date())
        		.setExpiration(expireDate)
        		.setAudience(jwtProps.getTOKEN_AUDIENCE())
        		.setIssuer(jwtProps.getTOKEN_ISSUER())
        		.signWith(SignatureAlgorithm.HS512, jwtProps.getJWT_SECRET())
        		.compact();
        return token;
	}
	
	public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationTokenFromJwt(String jwt) {
		try {
			var jwtBody = Jwts.parser().setSigningKey(jwtProps.getJWT_SECRET()).parseClaimsJws(jwt.replace("Bearer ", "")).getBody();
			String username = jwtBody.getSubject();
			com.algorithmvisualizer.model.User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new UsernameNotFoundException("Username or email dont exist! " + username));
			Set<GrantedAuthority> roles = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
			
			CustomUserDetails currentUser = new CustomUserDetails(user.getId(),user.getName(),user.getUsername(),user.getEmail(),user.getPassword(),user.getVerified(), roles, null);
			return new UsernamePasswordAuthenticationToken(currentUser, null, roles);
		} catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", jwt, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", jwt, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", jwt, exception.getMessage());
        } catch (SignatureException exception) {
            log.warn("Request to parse JWT with invalid signature : {} failed : {}", jwt, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", jwt, exception.getMessage());
        }
		return null;
	}
	
	public String validateToken(String token) {
		String status = "";
		
		Optional<User> optionalUser = userRepository.findUserByToken(token);
		
		if(optionalUser.isPresent()) {
			if(!(isTokenExpired(token))) {
				User user = optionalUser.get();
				user.setToken(null);
				user.setVerified(true);
				userRepository.save(user);
				status = Status.IS_VALID.name();
			}
			else status = Status.TOKEN_EXPIRED.name();
		}
		else status = Status.ERROR.name();
		
		return status;
	}
	
	public Boolean isTokenExpired(String token) {
		var jwtBody = Jwts.parser().setSigningKey(jwtProps.getJWT_SECRET()).parseClaimsJws(token).getBody();
		Date expirationTime = jwtBody.getExpiration();
		Date todayTime = new Date();
		return expirationTime.before(todayTime);
	}
}
