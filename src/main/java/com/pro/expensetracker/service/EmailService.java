package com.pro.expensetracker.service;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailsender;
	
	@Value("${spring.mail.properties.mail.smtp.from}")
	private String fromEmail;
	
	public void sendEmail(String to,String subject,String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			mailsender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/*
	 * @Value("${BREVO_API_KEY}") private String apiKey;
	 * 
	 * @Value("${brevo.sender.email}") private String fromEmail;
	 * 
	 * @Value("${brevo.sender.name}") private String senderName;
	 * 
	 * private final RestTemplate restTemplate = new RestTemplate();
	 * 
	 * public void sendEmail(String to, String subject, String body) { try { String
	 * url = "https://api.brevo.com/v3/smtp/email";
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.add("accept",
	 * "application/json"); headers.add("content-type", "application/json");
	 * headers.add("api-key", apiKey);
	 * 
	 * Map<String, Object> requestBody = Map.of( "sender", Map.of( "name",
	 * senderName, "email", fromEmail ), "to", List.of( Map.of("email", to) ),
	 * "subject", subject, "htmlContent", body );
	 * 
	 * HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody,
	 * headers);
	 * 
	 * System.out.println("➡ Calling Brevo API...");
	 * System.out.println("➡ API KEY PRESENT: " + (apiKey != null));
	 * 
	 * ResponseEntity<String> response = restTemplate.postForEntity(url, request,
	 * String.class);
	 * 
	 * System.out.println("✅ Brevo Response Status: " + response.getStatusCode());
	 * System.out.println("✅ Brevo Response Body: " + response.getBody());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); throw new
	 * RuntimeException("Failed to send email: " + e.getMessage()); } }
	 */
	
		
}
