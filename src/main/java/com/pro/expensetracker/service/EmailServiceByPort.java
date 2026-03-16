package com.pro.expensetracker.service;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceByPort {
	 private final JavaMailSender mailsender;
	 
	  @Value("${brevo.smtp.key}")
	  private String brevoApiKey;
	  
	  @Value("${brevo.sender.name}")
	  private String senderEmail;
	  
	  @Value("${spring.mail.properties.mail.smtp.from}") 
	  private String fromEmail;
	  
	  public void sendEmailbyPort(String to,String subject,String body) { try {
	  
	  SimpleMailMessage message = new SimpleMailMessage();
	  message.setFrom(fromEmail); message.setTo(to); message.setSubject(subject);
	  message.setText(body); mailsender.send(message);
	  
	  
	  } catch (Exception e) { throw new RuntimeException(e.getMessage()); } }
	  
	  
	  public void sendEmailWithAttachment(String to,String subject,String body,byte[] attachment,String fileName) throws MessagingException {
		  MimeMessage message = mailsender.createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(message,true);
		  helper.setFrom(fromEmail);
		  helper.setTo(to);
		  helper.setSubject(subject);
		  helper.setText(body);
		  helper.addAttachment(fileName, new ByteArrayResource(attachment));
		  mailsender.send(message);
		  
		  
		  
		  
		  
	  }
	 
}
