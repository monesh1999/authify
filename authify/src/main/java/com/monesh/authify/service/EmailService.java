package com.monesh.authify.service;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	public  EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}
	
	
	@Value("${spring.mail.properties.mail.smtp.from}")
	private String fromEmail;
	
	
//	public void sendWelcome(String toEmail,String name) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom(fromEmail);
//		message.setTo(toEmail);
//		message.setSubject("Welcome to Our Platform");
//		message.setText("Hello "+name+", \n\n Thanks for registering with us! \n\n Regards , \n Authity Team");
//		mailSender.send(message);
//	}
	
//	public void sentResetOtpEmail(String toEmail,String otp) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom(fromEmail);
//		message.setTo(toEmail);
//		message.setSubject("Password Reset otp");
//		message.setText("your otp for resetting your password is "+otp+" . Use this Otp to proceed with resetting your password. \n\n Thanks for with us! \n\n Regards , \n Authity Team");
//		mailSender.send(message);
//	}
//	
//	public void sendOtpEmail(String toEmail, String otp ) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom(fromEmail);
//		message.setTo(toEmail);
//		message.setSubject("Account Verfication otp");
//		message.setText("your otp is "+otp+" .\n  verify your account using this OTP.  \n\n Thanks for with us! \n\n Regards , \n Authity Team ");
//		mailSender.send(message);
//	}
	
	public void sendOtpEmail(String toEmail, String otp ) throws MessagingException {
		Context context = new Context();
		context.setVariable("email", toEmail);
		context.setVariable("otp", otp);
		String process = templateEngine.process("verify-email", context);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		helper.setFrom(fromEmail);
		helper.setTo(toEmail);
		helper.setSubject("Account Verfication otp");
		helper.setText(process,true);
		
		mailSender.send(mimeMessage);
		
	}
	public void sentResetOtpEmail(String toEmail,String otp) throws MessagingException {
		Context context = new Context();
		context.setVariable("email", toEmail);
		context.setVariable("otp", otp);
		String process = templateEngine.process("password-reset-email", context);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		helper.setFrom(fromEmail);
		helper.setTo(toEmail);
		helper.setSubject("Password Reset otp");
		helper.setText(process,true);
		mailSender.send(mimeMessage);
	}
	public void sendWelcome(String toEmail,String name) throws MessagingException{
		Context context = new Context();
		context.setVariable("email", toEmail);
		context.setVariable("name", name);
		String process = templateEngine.process("welcome-page", context);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		helper.setFrom(fromEmail);
		helper.setTo(toEmail);
		helper.setSubject("Welcome to Our Platform");
		helper.setText(process,true);
		mailSender.send(mimeMessage);
		
	}
	
	

}
