package com.pro.expensetracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pro.expensetracker.dto.ExpenseDTO;
import com.pro.expensetracker.entity.CategoryEntity;
import com.pro.expensetracker.entity.ExpenseEntity;
import com.pro.expensetracker.entity.ProfileEntity;
import com.pro.expensetracker.repository.CategoryRepository;
import com.pro.expensetracker.repository.ExpenseRepository;
import com.pro.expensetracker.service.CategoryService;
import com.pro.expensetracker.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
	
	private final CategoryService categoryService;
	private final CategoryRepository categoryRepository;
	private final ExpenseRepository expenseRepository;
	private final ProfileService profileService;
	
	public List<ExpenseDTO> getExpenseForUserOnDate(Long profileId,LocalDate Date){
		
		ProfileEntity profile = profileService.getCurrentProfile();
		List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDate(profile.getId(), Date);
		return list.stream().map(this::toDTO).toList();
	}
	
	
	public List<ExpenseDTO> filterExpenses(LocalDate startDate,LocalDate endDate,String keyword,Sort sort){
		ProfileEntity profile = profileService.getCurrentProfile();
		List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
		return list.stream().map(this::toDTO).toList();
	}
	
	
	public List<ExpenseDTO> getLatestexpenseForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		List<ExpenseEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
		return list.stream().map(this::toDTO).toList();
	}
	
	
	public BigDecimal getTotalExpenseForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		BigDecimal total = expenseRepository.findTotalExpenseByProfileId(profile.getId());
		return total!=null ? total:BigDecimal.ZERO;
	}
	
	
	public void deleteExpense(Long expenseId) {
		ProfileEntity profile = profileService.getCurrentProfile();
		ExpenseEntity entity = expenseRepository.findById(expenseId)
		.orElseThrow(() -> new RuntimeException("Expense Not Found"));
		if(!entity.getProfile().getId().equals(profile.getId())) {
			throw new RuntimeException("Unauthorized to delete this expense");
		}
		
		expenseRepository.delete(entity);
	}
	
	
	public List<ExpenseDTO> getCurrentMonthExpenseForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		LocalDate now = LocalDate.now();
		LocalDate startDate = now.withDayOfMonth(1);
		LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
		List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
		return list.stream().map(this::toDTO).toList();
	}
	
	
	public ExpenseDTO addExpense(ExpenseDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category Not Found"));
		ExpenseEntity newExpense= toEntity(dto ,profile,categoryEntity);
		newExpense= expenseRepository.save(newExpense);
		return toDTO(newExpense);
	}
	
	private ExpenseEntity toEntity(ExpenseDTO dto,ProfileEntity profile,CategoryEntity category) {
		return ExpenseEntity.builder()
				.name(dto.getName())
				.icon(dto.getIcon())
				.amount(dto.getAmount())
				.date(dto.getDate())
				.profile(profile)
				.category(category)
				.build();
	}
	
	private ExpenseDTO toDTO(ExpenseEntity entity) {
		return ExpenseDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.icon(entity.getIcon())
				.categoryId(entity.getCategory()!=null ? entity.getCategory().getId():null)
				.categoryName(entity.getCategory()!=null ? entity.getCategory().getName():"N/A")
				.amount(entity.getAmount())
				.date(entity.getDate())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdateAt())
				.build();
	}
	
	
	
}
