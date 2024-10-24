package com.saikrupafinance.service;





import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saikrupafinance.model.Admin;

import com.saikrupafinance.repository.AdminRepository;



@Service
public class AdminServiceImpl  implements AdminService{

	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public void saveAdmin(Admin admin) {
	  
		adminRepository.save(admin);
		
	}

	@Override
	public Admin findByEmail(String email) {
		return adminRepository.findByEmail(email) ;
	}

	

	@Override
	public Optional<Admin> findById(Long id) {
		// TODO Auto-generated method stub
		return adminRepository.findById(id);
	}

	@Override
	public List<Admin> findAllAdmins() {
		// TODO Auto-generated method stub
		return null;
	}

}