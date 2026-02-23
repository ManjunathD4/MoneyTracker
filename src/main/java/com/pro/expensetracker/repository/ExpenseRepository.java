package com.pro.expensetracker.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro.expensetracker.entity.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {

	List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);
	
	List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);
	
	
	@Query("Select SUM(e.amount) FROM ExpenseEntity e where e.profile.id=:profileId")
	BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);
	
	
	//Select * from tbl_expense where profileid=? and date between ? and ? and name like "%?%"
	List<ExpenseEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
			Long profileId,
			LocalDate startDate,
			LocalDate endDate,
			String keyword,
			Sort sort
			);
	
	
	List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId,LocalDate startDate,
			LocalDate endDate);
	
	List<ExpenseEntity> findByProfileIdAndDate(Long profileId,LocalDate Date);
	
}
