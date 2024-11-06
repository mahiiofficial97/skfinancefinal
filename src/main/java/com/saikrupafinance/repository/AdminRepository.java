package com.saikrupafinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saikrupafinance.model.Admin;

@Repository
public interface AdminRepository  extends JpaRepository<Admin, Long>{

	@Query("SELECT a FROM Admin a WHERE a.email=:email")
	Admin findByEmail(String email);

}
