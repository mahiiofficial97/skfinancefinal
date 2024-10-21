package com.saikrupafinance.service;

import java.util.Optional;

import com.saikrupafinance.model.Staff;

public interface StaffService {

	public void createStaff(Staff staff);
	
    Optional<Staff> findById(Long staffId);


}
