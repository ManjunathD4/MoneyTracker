package com.pro.expensetracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.expensetracker.dto.ExpenseDTO;
import com.pro.expensetracker.dto.IncomeDTO;

import com.pro.expensetracker.service.IncomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {
	private final IncomeService incomeService;
	
	@PostMapping
	public ResponseEntity<IncomeDTO> addExpense(@RequestBody IncomeDTO dto){
		IncomeDTO saved = incomeService.addExpense(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	@GetMapping
	public ResponseEntity<List<IncomeDTO>> getCurrentMonthIncomeForCurrentUser(){
		List<IncomeDTO> incomes = incomeService.getCurrentMonthIncomeForCurrentUser();
		return ResponseEntity.ok(incomes);
	}
	
	@DeleteMapping("/{incomeId}")
	public ResponseEntity<Void> deleteIncome(@PathVariable Long incomeId){
		 incomeService.deleteIncome(incomeId);
		return ResponseEntity.noContent().build();
	}

}
