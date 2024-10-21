package com.saikrupafinance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saikrupafinance.model.Admin;
import com.saikrupafinance.model.Client;
import com.saikrupafinance.model.Staff;
import com.saikrupafinance.repository.StaffRepository;



@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	StaffRepository staffRepository;
	
	 public void createStaff(Staff staff) {
		 
		 
		 Staff existingStaff = staffRepository.findByEmail(staff.getEmail());
	        
	        if (existingStaff != null) {
	            // Check if the staff is active or inactive
	        	 throw new IllegalStateException("Staff already exists with the name and emailid: " + staff.getEmail());
	        }

	        // Save new staff if no existing record found
	        staffRepository.save(staff);	 
	        
	 }   

	 public Optional<Staff> findById(Long staffId) {
	        return staffRepository.findById(staffId);
	    }
	        
	 
	 
	 public Staff findByEmail(String email) {
			return staffRepository.findByEmail(email) ;
		}
	 }

	 

