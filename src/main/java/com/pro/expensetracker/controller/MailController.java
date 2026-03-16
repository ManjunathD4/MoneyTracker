package com.pro.expensetracker.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.expensetracker.dto.CategoryDTO;
import com.pro.expensetracker.entity.ProfileEntity;
import com.pro.expensetracker.service.EmailService;
import com.pro.expensetracker.service.EmailServiceByPort;
import com.pro.expensetracker.service.ExcelService;
import com.pro.expensetracker.service.ExpenseService;
import com.pro.expensetracker.service.IncomeService;
import com.pro.expensetracker.service.ProfileService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class MailController {

	
	private final ExcelService excelService;
	private final IncomeService incomeService;
	private final ExpenseService expenseService;
	private final EmailService emailService;
	private final EmailServiceByPort emailServicePort;
	private final ProfileService profileService;
	
	
	@GetMapping("/income-excel")
	public  ResponseEntity<Void> emailIncomeExcel() throws IOException, MessagingException{
		ProfileEntity profile = profileService.getCurrentProfile();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeIncomesToExcel(baos, incomeService.getCurrentMonthIncomeForCurrentUser());
		emailServicePort.sendEmailWithAttachment(profile.getEmail(),
					"Monthly Income Report",
					"Please Find Attached your income report", 
					baos.toByteArray(), 
									"income.xlsx");
		return ResponseEntity.ok(null);
	}
	
	

	@GetMapping("/expense-excel")
	public  ResponseEntity<Void> emailExpenseExcel() throws IOException, MessagingException{
		ProfileEntity profile = profileService.getCurrentProfile();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeExpensesToExcel(baos, expenseService.getCurrentMonthExpenseForCurrentUser());
		emailServicePort.sendEmailWithAttachment(profile.getEmail(),
					"Monthly Expense Report",
					"Please Find Attached your expense report", 
					baos.toByteArray(), 
									"expense.xlsx");
		return ResponseEntity.ok(null);
	}
	
}
