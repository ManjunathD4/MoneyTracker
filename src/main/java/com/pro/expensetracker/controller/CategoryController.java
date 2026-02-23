package com.pro.expensetracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.expensetracker.dto.CategoryDTO;
import com.pro.expensetracker.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO){
		CategoryDTO savedCategory=categoryService.saveCategory(categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getCategoriesforCurrentUser(){
		List<CategoryDTO> categories= categoryService.getCategoriesforCurrentUser();
		return  ResponseEntity.ok(categories);
	}
	
	@GetMapping("/{type}")
	public ResponseEntity<List<CategoryDTO>> getCategoriesByTypeForCurrentUser(@PathVariable String type){
		List<CategoryDTO> categories= categoryService.getCategoriesByTypeForCurrentUser(type);
		return  ResponseEntity.ok(categories);
	}
	
	
	@PutMapping("/{categoryID}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryID,@RequestBody CategoryDTO categoryDTO){
		CategoryDTO categories= categoryService.updateCategory(categoryID, categoryDTO);
		return  ResponseEntity.ok(categories);
	}
	
}
