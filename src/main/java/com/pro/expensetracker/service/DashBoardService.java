package com.pro.expensetracker.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pro.expensetracker.dto.ExpenseDTO;
import com.pro.expensetracker.dto.IncomeDTO;
import com.pro.expensetracker.dto.RecentTransactionDTO;
import com.pro.expensetracker.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import static java.util.stream.Stream.concat;



@Service
@RequiredArgsConstructor
public class DashBoardService {

	private final IncomeService incomeService;
	private final ExpenseService expenseService;
	private final ProfileService profileService;
	
	
	public Map<String,Object> getDashBoardData(){
		ProfileEntity profile = profileService.getCurrentProfile();
		 Map<String,Object> returnValue = new LinkedHashMap<>();
		List<IncomeDTO> latestIncomes = incomeService.getLatestincomeForCurrentUser();
		List<ExpenseDTO> latestExpense = expenseService.getLatestexpenseForCurrentUser();
	List<RecentTransactionDTO> recentTransaction = concat(latestIncomes.stream().map(income ->
		RecentTransactionDTO.builder()
		.id(income.getId())
		.profileId(profile.getId())
		.name(income.getName())
		.icon(income.getIcon())
		.amount(income.getAmount())
		.date(income.getDate())
		.createdAt(income.getCreatedAt())
		.updatedAt(income.getUpdatedAt())
		.type("income")
		.build()),
		latestExpense.stream().map(expense ->
				RecentTransactionDTO.builder()
				.id(expense.getId())
				.profileId(profile.getId())
				.name(expense.getName())
				.icon(expense.getIcon())
				.amount(expense.getAmount())
				.date(expense.getDate())
				.createdAt(expense.getCreatedAt())
				.updatedAt(expense.getUpdatedAt())
				.type("expense")
				.build()))
				.sorted((a,b)->{
					int cmp = b.getDate().compareTo(a.getDate());
					if(cmp == 0 && a.getCreatedAt()!= null && b.getCreatedAt()!=null) {
						return b.getCreatedAt().compareTo(a.getCreatedAt());
					}
					return cmp;
				}).collect(Collectors.toList());
	
	returnValue.put("totalBalance", incomeService.getTotaleIncomeForCurrentUser()
			.subtract(expenseService.getTotalExpenseForCurrentUser()));
	returnValue.put("totalIncome", incomeService.getTotaleIncomeForCurrentUser());
	returnValue.put("totalExpense",expenseService.getTotalExpenseForCurrentUser());
	returnValue.put("recent5Expense",latestExpense);
	returnValue.put("recent5Incomes",latestIncomes);
	returnValue.put("recentTransaction",recentTransaction);
	
	return returnValue;
	
		
	
	}
	
}
