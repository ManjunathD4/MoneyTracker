package com.pro.expensetracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pro.expensetracker.dto.ExpenseDTO;
import com.pro.expensetracker.dto.IncomeDTO;
import com.pro.expensetracker.entity.CategoryEntity;
import com.pro.expensetracker.entity.ExpenseEntity;
import com.pro.expensetracker.entity.IncomesEntity;
import com.pro.expensetracker.entity.ProfileEntity;
import com.pro.expensetracker.repository.CategoryRepository;
import com.pro.expensetracker.repository.ExpenseRepository;
import com.pro.expensetracker.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {
	private final CategoryService categoryService;
	private final IncomeRepository incomeRepository;
	
	private final CategoryRepository categoryRepository;
	
	private final ProfileService profileService;
	
	
	public List<IncomeDTO> filterIncomes(LocalDate startDate,LocalDate endDate,String keyword,Sort sort){
		ProfileEntity profile = profileService.getCurrentProfile();
		List<IncomesEntity> list = incomeRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
		return list.stream().map(this::toDTO).toList();
	}
	
	
	public List<IncomeDTO> getLatestincomeForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		List<IncomesEntity> list = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
		return list.stream().map(this::toDTO).toList();
	}
	
	
	public BigDecimal getTotaleIncomeForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		BigDecimal total = incomeRepository.findTotalIncomeByProfileId(profile.getId());
		return total!=null ? total:BigDecimal.ZERO;
	}
	
	
	public void deleteIncome(Long incomeId) {
		ProfileEntity profile = profileService.getCurrentProfile();
		  IncomesEntity entity = incomeRepository.findById(incomeId)
		.orElseThrow(() -> new RuntimeException("Income Not Found"));
		if(!entity.getProfile().getId().equals(profile.getId())) {
			throw new RuntimeException("Unauthorized to delete this income");
		}
		
		incomeRepository.delete(entity);
	}
	
	
	public List<IncomeDTO> getCurrentMonthIncomeForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		LocalDate now = LocalDate.now();
		LocalDate startDate = now.withDayOfMonth(1);
		LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
		List<IncomesEntity> list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
		return list.stream().map(this::toDTO).toList();
	}
	
	
	public IncomeDTO addExpense(IncomeDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category Not Found"));
		IncomesEntity newIncome= toEntity(dto ,profile,categoryEntity);
		newIncome= incomeRepository.save(newIncome);
		return toDTO(newIncome);
	}
	
	private IncomesEntity toEntity(IncomeDTO dto,ProfileEntity profile,CategoryEntity category) {
		return IncomesEntity.builder()
				.name(dto.getName())
				.icon(dto.getIcon())
				.amount(dto.getAmount())
				.date(dto.getDate())
				.profile(profile)
				.category(category)
				.build();
	}
	
	private IncomeDTO toDTO(IncomesEntity entity) {
		return IncomeDTO.builder()
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
