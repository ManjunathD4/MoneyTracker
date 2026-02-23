package com.pro.expensetracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.expensetracker.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>  {

	
	// query to search email
	//optional because if mail does not find there should not be return null(nullpointer exception)
	Optional<ProfileEntity> findByEmail(String email);
	
	//where activation_token=?
	Optional<ProfileEntity> findByActivationToken(String activationToken);
	
}
