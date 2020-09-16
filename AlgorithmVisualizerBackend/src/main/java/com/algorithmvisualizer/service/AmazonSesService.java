package com.algorithmvisualizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.algorithmvisualizer.model.User;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Component
public class AmazonSesService {
	
	String from = "";
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	public void sendVerificationEmail(User user) {
		
		String title = "Verify Your Email Address";
		
		String bodyHtml = "Thank you for creating account.To complete the registration process, please click the link below to verify your e-mail address. "
				+ "<a href=\"http://ec2-18-191-231-165.us-east-2.compute.amazonaws.com:8080/web-algo/email-verification.html?token=$token\"> LINK </a>";
		String bodyText = "Thank you for creating account.To complete the registration process, please click the link below to verify your e-mail address"
				+ "http://ec2-18-191-231-165.us-east-2.compute.amazonaws.com:8080/web-algo/email-verification.html?token=$token";
		
		AmazonSimpleEmailService amazonSES = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		
		String bodyHtmlWithToken = bodyHtml.replace("$token", user.getToken());
		String bodyTextWithToken = bodyText.replace("$token", user.getToken());
		
		SendEmailRequest emailRequest = new SendEmailRequest()
				.withDestination(
			              new Destination().withToAddresses(user.getEmail()))
			          .withMessage(new Message()
			              .withBody(new Body()
			                  .withHtml(new Content()
			                      .withCharset("UTF-8").withData(bodyHtmlWithToken))
			                  .withText(new Content()
			                      .withCharset("UTF-8").withData(bodyTextWithToken)))
			              .withSubject(new Content()
			                  .withCharset("UTF-8").withData(title)))
			          .withSource(from);
		
		try {
			amazonSES.sendEmail(emailRequest);
		}catch (Exception ex) {
		      System.out.println("The email was not sent. Error message: " + ex.getMessage());
		    }
	}
	
	public void sendResetPasswordEmail(User user, String password) {
		
		String title = "Change your password request";
		
		String bodyHtml = "You recently requested to reset your password for your account,below is a temporary generated password you can use to log in and change your password. "
				+ " " + password + " "
				+ "If you did not request this password change please feel free to ignore it.";
		String bodyText = "You recently requested to reset your password for your account,below is a temporary generated password you can use to log in and change your password. "
				+ " " + password + " "
				+ "If you did not request this password change please feel free to ignore it.";
		
		AmazonSimpleEmailService amazonSES = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		
		SendEmailRequest emailRequest = new SendEmailRequest()
				.withDestination(
			              new Destination().withToAddresses(user.getEmail()))
			          .withMessage(new Message()
			              .withBody(new Body()
			                  .withHtml(new Content()
			                      .withCharset("UTF-8").withData(bodyHtml))
			                  .withText(new Content()
			                      .withCharset("UTF-8").withData(bodyText)))
			              .withSubject(new Content()
			                  .withCharset("UTF-8").withData(title)))
			          .withSource(from);
		
		try {
			amazonSES.sendEmail(emailRequest);
			
		}catch (Exception ex) {
		      System.out.println("The email was not sent. Error message: " + ex.getMessage());
		}
	}
}
