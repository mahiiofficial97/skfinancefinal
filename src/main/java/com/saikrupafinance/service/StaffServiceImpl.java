package com.saikrupafinance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public List<Staff> findAllStaff() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSpecificStaff(Long staffId) {
		// TODO Auto-generated method stub
		
	}

	 @Override
	    public void updateStaff(Staff staff) {
	        Optional<Staff> existingStaffOptional = staffRepository.findById(staff.getId());
	        if (!existingStaffOptional.isPresent()) {
	            throw new IllegalStateException("Staff not found with ID: " + staff.getId());
	        }

	        Staff existingStaff = existingStaffOptional.get();
	        existingStaff.setStaffname(staff.getStaffname()); // Update staff name
	        existingStaff.setEmail(staff.getEmail());
	        existingStaff.setRole(staff.getRole());
	        existingStaff.setIsActiveStaff(staff.getIsActiveStaff()); // Update isActive status
	        staffRepository.save(existingStaff);
	    }
	}
	 

	 

