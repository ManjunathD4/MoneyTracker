package com.pro.expensetracker.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.expensetracker.service.DashBoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

	private final DashBoardService dashboardService;
	
	
	@GetMapping
	public ResponseEntity<Map<String,Object>> getDashboardData(){
		Map<String,Object> dashboardData=dashboardService.getDashBoardData();
		return ResponseEntity.ok(dashboardData);
	}
	
}
