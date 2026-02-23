package com.pro.expensetracker.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //for table in database 
@Table(name = "tbl_profiles") // Custom table name in DB
@Data   // for generates getters, setters, toString, equals, hashCode using lambok dependency
@AllArgsConstructor  // generates constructor with all fields
@NoArgsConstructor //  generates empty constructor
@Builder // allows building object using builder pattern
public class ProfileEntity {
	
	@Id // Primary key of the table
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
	private Long id;
	private String fullname;
	@Column(unique = true)  // Email must be unique in database
	private String email;
	private String password;
	private String profileImageUrl;
	@Column(updatable = false) // Value cannot be updated once set
	@CreationTimestamp // Automatically sets time when record is created
	private LocalDateTime createdAt;
	@UpdateTimestamp // ⚠️ Better to use @UpdateTimestamp for updates
	private LocalDateTime updatedAt;
	private Boolean isActive;
	private String activationToken;
	
	@PrePersist
	public void prePersist() {
			if(this.isActive == null) {
					isActive = false;
			}
	}

	
	
}
