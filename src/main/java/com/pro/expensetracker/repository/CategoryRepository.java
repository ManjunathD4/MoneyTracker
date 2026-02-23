package com.pro.expensetracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.expensetracker.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	
	
	//select * from tbl_catagories where profile_id=?
	List<CategoryEntity> findByProfileId(Long profileId);
	
	
	//select * from tbl_catagories where id=? and profile_id=?
	Optional<CategoryEntity>findByIdAndProfileId(Long id,Long ProfileId);
	
	//select * from tbl_catagories where type=? and profile_id=?
	List<CategoryEntity> findByTypeAndProfileId(String type,Long ProfileId);
	
	Boolean existsByNameAndProfileId(String name,Long ProfileId);
}
