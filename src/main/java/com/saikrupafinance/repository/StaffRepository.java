package com.saikrupafinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.saikrupafinance.model.Staff;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query("SELECT a FROM Staff a WHERE a.email=:email")
	Staff findByEmail(String email);

}