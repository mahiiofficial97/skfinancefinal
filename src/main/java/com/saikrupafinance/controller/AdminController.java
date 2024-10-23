package com.saikrupafinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import com.saikrupafinance.dto.StaffCreationRequest;
import com.saikrupafinance.model.Admin;
import com.saikrupafinance.model.JsonResponseclass;
import com.saikrupafinance.model.Staff;
import com.saikrupafinance.service.AdminServiceImpl;
import com.saikrupafinance.service.StaffServiceImpl;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Autowired
	AdminServiceImpl adminServiceImpl;
	
	
	@Autowired
	StaffServiceImpl staffServiceImpl; 

	//create Admin
		@PostMapping("/saveadmin")
		public JsonResponseclass saveadmin(@RequestBody Admin admin) {

			JsonResponseclass response = new JsonResponseclass();

			Admin email = adminServiceImpl.findByEmail(admin.getEmail());
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


			if (email == null) {
	            admin.setPassword(passwordEncoder.encode(admin.getPassword()));

				adminServiceImpl.saveAdmin(admin);

				response.setStatus("200");
				response.setMessage("admin created sucessfully");
				response.setResult("success");
			} else {
				response.setStatus("400");
				response.setResult("unsuccessful");
				response.setMessage("this id is already created please create new id ");
			}

			return response;
		}
		
		//update Admin
		@PutMapping("/updateadmin")
		public JsonResponseclass updateAdmin(@RequestBody Admin admin, HttpSession session) {
		    JsonResponseclass response = new JsonResponseclass();

		    // Retrieve the logged-in admin from session
		    Admin existingAdminFromSession = (Admin) session.getAttribute("admininstance");

		    // Check if the admin is logged in
		    if (existingAdminFromSession == null) {
		        response.setStatus("403");
		        response.setMessage("Unauthorized: Please log in to update your profile");
		        response.setResult("unauthorized");
		        return response;
		    }

		    // Check if the admin is trying to update their own information
		    if (!existingAdminFromSession.getEmail().equals(admin.getEmail())) {
		        response.setStatus("403");
		        response.setMessage("Unauthorized: You can only update your own profile");
		        response.setResult("unauthorized");
		        return response;
		    }

		    // Find the existing admin by ID (or email if needed)
		    Optional<Admin> existingAdminOptional = adminServiceImpl.findById(existingAdminFromSession.getId());

		    if (existingAdminOptional.isPresent()) {
		        Admin existingAdmin = existingAdminOptional.get();

		        // Update the admin's details with the new values
		        existingAdmin.setUsername(admin.getUsername());
		        existingAdmin.setEmail(admin.getEmail());
		        existingAdmin.setRole(admin.getRole()); // Role can be updated if allowed by logic
		        existingAdmin.setIsActiveAdmin(admin.getIsActiveAdmin()); // Optional field to update

		        // Optionally update the password if provided
		        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
		            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		            existingAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
		        }

		        // Save the updated admin using the existing saveAdmin method
		        adminServiceImpl.saveAdmin(existingAdmin);

		        response.setStatus("200");
		        response.setMessage("Admin profile updated successfully");
		        response.setResult("success");
		    } else {
		        response.setStatus("404");
		        response.setMessage("Admin not found");
		        response.setResult("not_found");
		    }

		    return response;
		}

		// Staff Create by Admin
		@PostMapping("/createstaff")
		public JsonResponseclass createStaff(@RequestBody StaffCreationRequest request, HttpSession session) {
		    JsonResponseclass response = new JsonResponseclass();

		    // Retrieve the admin from the session
		    Admin existingAdminFromSession = (Admin) session.getAttribute("admininstance");

		    // Check if the admin is logged in
		    if (existingAdminFromSession == null) {
		        response.setStatus("403");
		        response.setMessage("Unauthorized: Please log in to create staff");
		        response.setResult("unauthorized");
		        return response;
		    }

		    // Verify the admin's email matches the request (optional but a good practice)
		    if (!existingAdminFromSession.getEmail().equals(request.getAdmin().getEmail())) {
		        response.setStatus("403");
		        response.setMessage("Unauthorized: You can only create staff under your own account");
		        response.setResult("unauthorized");
		        return response;
		    }

		    // Now that we have verified the admin, we can proceed with staff creation
		    Staff staff = request.getStaff(); // Get staff details from the request
		    
		    // Set the admin in the staff entity to maintain the reference to the logged-in admin
		    staff.setAdmin(existingAdminFromSession); // This ensures admin_id is set correctly in the Staff entity
		    
		    // Save the staff using the service implementation
		    staffServiceImpl.createStaff(staff); // Create the staff

		    response.setStatus("200");
		    response.setMessage("Staff created successfully");
		    response.setResult("success");
		    return response;
		}
	
		// Update staff by Admin
	    @PutMapping("/updatestaff/{id}")
	    public JsonResponseclass updateStaff(@PathVariable("id") Long id, @RequestBody Staff staff, HttpSession session) {
	        JsonResponseclass response = new JsonResponseclass();

	        // Retrieve the admin from the session
	        Admin existingAdminFromSession = (Admin) session.getAttribute("admininstance");

	        // Check if the admin is logged in
	        if (existingAdminFromSession == null) {
	            response.setStatus("403");
	            response.setMessage("Unauthorized: Please log in to update staff");
	            response.setResult("unauthorized");
	            return response;
	        }

	        // Check if the staff ID is valid
	        if (id == null || id <= 0) {
	            response.setStatus("400");
	            response.setMessage("Bad Request: Invalid staff ID");
	            response.setResult("id_invalid");
	            return response;
	        }

	        // Find the existing staff member by ID
	        Optional<Staff> existingStaffOptional = staffServiceImpl.findById(id);

	        if (existingStaffOptional.isPresent()) {
	            Staff existingStaff = existingStaffOptional.get();

	            // Update staff fields with new values
	            existingStaff.setStaffname(staff.getStaffname());
	            existingStaff.setEmail(staff.getEmail());
	            existingStaff.setRole(staff.getRole());
	            existingStaff.setIsActiveStaff(staff.getIsActiveStaff());

	            // Optionally update the password if provided
	            if (staff.getPassword() != null && !staff.getPassword().isEmpty()) {
	                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	                existingStaff.setPassword(passwordEncoder.encode(staff.getPassword()));
	            }

	            // Save the updated staff using the service implementation
	            staffServiceImpl.updateStaff(existingStaff);

	            response.setStatus("200");
	            response.setMessage("Staff updated successfully");
	            response.setResult("success");
	        } else {
	            response.setStatus("404");
	            response.setMessage("Staff not found");
	            response.setResult("not_found");
	        }

	        return response;
	    }
	
		
		// Endpoint for admin login
		@RequestMapping(value = "/login", method = RequestMethod.POST)
		@ResponseBody
		public JsonResponseclass loginAdmin(@RequestBody Admin admin, HttpSession session) {

			JsonResponseclass response = new JsonResponseclass();

			Object adminSession = session.getAttribute("admininstance");

			if (adminSession != null) {
				response.setMessage("You are already logged in the System");
				response.setStatus("200");
				response.setResult("success");
				return response;
			}

			Admin existingAdmin = adminServiceImpl.findByEmail(admin.getEmail());

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (existingAdmin != null && existingAdmin.getIsActiveAdmin().equals("Active")) {
				boolean passwordVerify = passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword());

				if (passwordVerify) {
					session.setAttribute("admininstance", existingAdmin);
					response.setStatus("200");
					response.setMessage("Login successfully");
					response.setResult("success");
				} else {
					response.setStatus("300");
					response.setMessage("Enter valid password");
					response.setResult("failure");
				}
			} else {
				response.setStatus("400");
				response.setMessage("This email does not exist.or account is not active ");
				response.setResult("failure");
			}

			return response;
		}

		
		 // Endpoint for admin logout
		@RequestMapping(value = "/logout", method = RequestMethod.POST)
		@ResponseBody
		public JsonResponseclass logoutAdmin(HttpSession session) {

			JsonResponseclass response = new JsonResponseclass();

			Object adminSession = session.getAttribute("admininstance");

			if (adminSession == null) {
				response.setStatus("300");
				response.setMessage("Session already expired or logged out");
				response.setResult("failure");
			} else {
				session.removeAttribute("admininstance");
				response.setStatus("200");
				response.setMessage("Logout successfully");
				response.setResult("success");
			}

			return response;
		}

		public Admin findById(Long id) {
		    return adminServiceImpl.findById(id).orElse(null);
		}
		
}
