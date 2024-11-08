package com.saikrupafinance.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.saikrupafinance.model.Admin;

@Service
public interface AdminService {
	public void saveAdmin(Admin admin);

	public Admin findByEmail(String email);

	public List<Admin> findAllAdmins();

	public  Optional<Admin> findById(Long id);

	public  void deleteAllAdmins();

	public  void deleteSpecificAdmin(Long id);
}
