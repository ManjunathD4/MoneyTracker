package com.pro.expensetracker.service;

import java.util.Map;

import javax.management.RuntimeErrorException;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service

public class EmailService {

	/*
	 * private final JavaMailSender mailsender;
	 * 
	 * @Value("${spring.mail.properties.mail.smtp.from}") private String fromEmail;
	 * 
	 * public void sendEmail(String to,String subject,String body) { try {
	 * SimpleMailMessage message = new SimpleMailMessage();
	 * message.setFrom(fromEmail); message.setTo(to); message.setSubject(subject);
	 * message.setText(body); mailsender.send(message); } catch (Exception e) {
	 * throw new RuntimeException(e.getMessage()); } }
	 */
	
	  @Value("${BREVO_API_KEY}")
	    private String apiKey;

	    private final RestTemplate restTemplate = new RestTemplate();

	    public void sendEmail(String to, String subject, String body) {
	        try {
	            String url = "https://api.brevo.com/v3/smtp/email";

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_JSON);
	            headers.set("api-key", apiKey);
	            headers.set("accept", "application/json");

	            Map<String, Object> requestBody = Map.of(
	                    "sender", Map.of(
	                            "name", "Expense Tracker",
	                            "email", "desaimanju2001@gmail.com"   // MUST be verified in Brevo
	                    ),
	                    "to", new Object[]{
	                            Map.of("email", to)
	                    },
	                    "subject", subject,
	                    "htmlContent", body
	            );

	            HttpEntity<Map<String, Object>> request =
	                    new HttpEntity<>(requestBody, headers);

	            restTemplate.exchange(url, HttpMethod.POST, request, String.class);

	        } catch (Exception e) {
	            throw new RuntimeException("Failed to send email: " + e.getMessage());
	        }
	    }
}
