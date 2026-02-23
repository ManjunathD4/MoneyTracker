package com.pro.expensetracker.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro.expensetracker.entity.ExpenseEntity;
import com.pro.expensetracker.entity.IncomesEntity;

public interface IncomeRepository extends JpaRepository<IncomesEntity , Long > {
List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);
	
	List<IncomesEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);
	
	
	@Query("Select SUM(e.amount) FROM IncomesEntity e where e.profile.id=:profileId")
	BigDecimal findTotalIncomeByProfileId(@Param("profileId") Long profileId);
	
	
	//Select * from tbl_income where profileid=? and date between ? and ? and name like "%?%"
	List<IncomesEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
			Long profileId,
			LocalDate startDate,
			LocalDate endDate,
			String keyword,
			Sort sort
			);
	
	
	List<IncomesEntity> findByProfileIdAndDateBetween(Long profileId,LocalDate startDate,
			LocalDate endDate);
	
}
