package com.pro.expensetracker.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pro.expensetracker.entity.ProfileEntity;
import com.pro.expensetracker.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	private final ProfileRepository profileRepository;
	private final EmailService emailService;
	private final ExpenseService expenseService;
	
	@Value("${money.manager.frontend.url}")
	private String frontendurl;
	
	
	//@Scheduled(cron = "0 0 22 * * *",zone="IST")
	public void sendDailyIncomeExpenseReminder() {
		log.info("Job started:sendDailyIncomeExpenseReminder()");
		List<ProfileEntity> profiles = profileRepository.findAll();
		for(ProfileEntity profile : profiles) {
			String body = "Hi "+ profile.getFullname() + ",<br><br>"
					+"This is a friendly reminder to add your income and expenses for today in Money Manager.<br><br>"
					+"<a href="+frontendurl+" style='display:inline-block;padding:10px 20px;background-color:#fff;text-decoration:none;border-radius:5px;font-weight:bold;'>Go To Money Manager</a>"
					+"<br><br>Best regards,<br>Money Manager Team";
			emailService.sendEmail(profile.getEmail(), "Daily reminder: Add your income and expenses", body);
		}
		
	}
	
	public void sendDilyExpenseSummary() {
		log.info("Job sendDilyExpenseSummary()");
		List<ProfileEntity> profiles = profileRepository.findAll();
		
		for(ProfileEntity profile : profiles) {
			expenseService.getExpenseForUserOnDate(profile.getId(), LocalDate.now(ZoneId.of("Asia/Kolkata")));
			String body = "Hi "+ profile.getFullname() + ",<br><br>"
					+"This is a friendly reminder to add your income and expenses for today in Money Manager.<br><br>"
					+"<a href="+frontendurl+" style='display:inline-block;padding:10px 20px;background-color:#fff;text-decoration:none;border-radius:5px;font-weight:bold;'>Go To Money Manager</a>"
					+"<br><br>Best regards,<br>Money Manager Team";
			emailService.sendEmail(profile.getEmail(), "Daily reminder: Add your income and expenses", body);
		}
		
	}
	
}
