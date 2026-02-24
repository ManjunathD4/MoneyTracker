package com.pro.expensetracker.service;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	/*
	 * private final JavaMailSender mailsender;
	 * 
	 * @Value("${brevo.api.key}") private String brevoApiKey;
	 * 
	 * @Value("${brevo.sender.email}") private String senderEmail;
	 * 
	 * @Value("${spring.mail.properties.mail.smtp.from}") private String fromEmail;
	 * 
	 * public void sendEmail(String to,String subject,String body) { try {
	 * 
	 * SimpleMailMessage message = new SimpleMailMessage();
	 * message.setFrom(fromEmail); message.setTo(to); message.setSubject(subject);
	 * message.setText(body); mailsender.send(message);
	 * 
	 * 
	 * } catch (Exception e) { throw new RuntimeException(e.getMessage()); } }
	 */

	
	  @Value("${BREVO_API_KEY}") 
	  private String apiKey;
	  
	  @Value("${BREVO_FROM_MAIL}")
	  private String fromEmail;
	  
	  @Value("${brevo.sender.name}")
	  private String senderName;
	  
	  private final RestTemplate restTemplate = new RestTemplate();
	  
	  public void sendEmail(String to, String subject, String body) { try { String
	  url = "https://api.brevo.com/v3/smtp/email";
	  
	  HttpHeaders headers = new HttpHeaders(); 
	  headers.add("accept",
	  "application/json");
	  headers.add("content-type", "application/json");
	  headers.add("api-key", apiKey);
	  
	  Map<String, Object> requestBody = Map.of( "sender", Map.of( "name",
	  senderName, "email", fromEmail ), "to", List.of( Map.of("email", to) ),
	  "subject", subject, "htmlContent", body );
	  
	  HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody,
	  headers);
	  
	
	  ResponseEntity<String> response = restTemplate.postForEntity(url, request,
	  String.class);
	  
	 
	  
	  } catch (Exception e) { e.printStackTrace(); throw new
	  RuntimeException("Failed to send email: " + e.getMessage()); } }
	 

}
