package com.monesh.authify.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	public  EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	
	@Value("${spring.mail.properties.mail.smtp.from}")
	private String fromEmail;
	
	
	public void sendWelcome(String toEmail,String name) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("Welcome to Our Platform");
		message.setText("Hello "+name+", \n\n Thanks for registering with us! \n\n Regards , \n Authity Team");
		mailSender.send(message);
	}
	
	public void sentResetOtpEmail(String toEmail,String otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("Password Reset otp");
		message.setText("your otp for resetting your password is "+otp+" . Use this Otp to proceed with resetting your password. \n\n Thanks for with us! \n\n Regards , \n Authity Team");
		mailSender.send(message);
	}
	
	public void sendOtpEmail(String toEmail, String otp ) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("Account Verfication otp");
		message.setText("your otp is "+otp+" .\n  verify your account using this OTP.  \n\n Thanks for with us! \n\n Regards , \n Authity Team ");
		mailSender.send(message);
	}

}
