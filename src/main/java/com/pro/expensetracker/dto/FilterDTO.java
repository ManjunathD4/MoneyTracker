package com.pro.expensetracker.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FilterDTO {

	private String type;
	private LocalDate startDate;
	private LocalDate endDate;
	private String keyword;
	private String sortField;//amount,date,name
	private String sortOrder;//asc or desc
	
}
