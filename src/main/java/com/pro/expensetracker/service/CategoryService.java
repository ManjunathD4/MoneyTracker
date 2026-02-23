package com.pro.expensetracker.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pro.expensetracker.dto.CategoryDTO;
import com.pro.expensetracker.entity.CategoryEntity;
import com.pro.expensetracker.entity.ProfileEntity;
import com.pro.expensetracker.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final ProfileService profileService;
	private final CategoryRepository categoryRepository;
	
	
	public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
		ProfileEntity profile = profileService.getCurrentProfile();
		if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(),profile.getId()))
		{
			throw new RuntimeException("Category already exist");
		}
     
	CategoryEntity newCategory=toEntity(categoryDTO,profile);
		//CategoryEntity newCategory=toEntity(categoryDTO, profile);
     newCategory = categoryRepository.save(newCategory);
     return toDTO(newCategory);
		
	}
	
	private CategoryEntity toEntity(CategoryDTO categoryDTO,ProfileEntity profile) {
		 
		return CategoryEntity.builder()
				.name(categoryDTO.getName())
				.icon(categoryDTO.getIcon())
				.profile(profile)
				.type(categoryDTO.getType())
				.build();
		
	}
	
	private CategoryDTO toDTO(CategoryEntity entity) {
		return CategoryDTO.builder()
				.id(entity.getId())
				.profileId(entity.getProfile()!=null ? entity.getProfile().getId():null)
				.name(entity.getName())
				.icon(entity.getIcon())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdateAt())
				.type(entity.getType())
				.build();
	}
	
	//get details of one user
	public List<CategoryDTO> getCategoriesforCurrentUser(){
		List<CategoryEntity> categories = categoryRepository.findByProfileId(profileService.getCurrentProfile().getId());
		return categories.stream().map(this::toDTO).toList();
		//stream() - to arrange the details properly in list order
		//map - for convert entity to DTO
	}
	
	//get details of one user by type
	public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type){
		List<CategoryEntity> categories = categoryRepository.findByTypeAndProfileId(type,profileService.getCurrentProfile().getId());
		return categories.stream().map(this::toDTO).toList();
	}
	
	public CategoryDTO updateCategory(Long categoryId,CategoryDTO dto) {
		ProfileEntity profile=profileService.getCurrentProfile();
		CategoryEntity exsitingCategory = categoryRepository.findByIdAndProfileId(categoryId, profile.getId())
		.orElseThrow(()-> new RuntimeException("Category Not Found"));
		exsitingCategory.setName(dto.getName());
		exsitingCategory.setIcon(dto.getIcon());
		exsitingCategory=categoryRepository.save(exsitingCategory);
		return toDTO(exsitingCategory);
		
	}
	
}
