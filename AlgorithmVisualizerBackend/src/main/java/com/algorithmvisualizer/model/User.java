package com.algorithmvisualizer.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotBlank
    @Size(max = 30)
	@Column(name = "name", unique = true, nullable = false, length = 30)
	private String name;
	
    @Size(max = 30)
	@Column(name = "username", unique = true, length = 20)
	private String username;
	
	@NaturalId (mutable = false)
	@NotBlank
	@Size(max = 30)
	@Email
	@Column(name = "email", unique = true, nullable = false, length = 30)
	private String email;
	
	//@NotBlank
    @Size(max = 60)
	@Column(name = "password", unique = true, length = 60)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "provider_type")
	private String providerType;
	
	@Column(name="token", unique = true, length = 256)
	private String token;
	
	private Boolean verified;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date created_At;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date updated_At;
	
	@PrePersist
	private void onCreate() {
		created_At = new Date();
	}
	
	@PreUpdate
	private void onUpdate() {
		updated_At = new Date();
	}
}
