package com.saikrupafinance.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.saikrupafinance.dto.StaffCreationRequest;
import com.saikrupafinance.model.Admin;
import com.saikrupafinance.model.Client;
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
		
		//create client by the Admin
		@PostMapping("/createstaff")
		public JsonResponseclass createStaff(@RequestBody StaffCreationRequest request, HttpSession session) {
		    JsonResponseclass response = new JsonResponseclass();

		    // Retrieve admin details from the request
		    Staff staff = request.getStaff();

		    // Retrieve the admin from the session
		    Admin existingAdminFromSession = (Admin) session.getAttribute("admininstance");

		    // Check if the admin is logged in
		    if (existingAdminFromSession == null) {
		        response.setStatus("403");
		        response.setMessage("Unauthorized: Please log in to create staff");
		        response.setResult("unauthorized");
		        return response;
		    }

		    // Find the existing admin in the database
		    Admin existingAdmin = adminServiceImpl.findByEmail(existingAdminFromSession.getEmail());

		    // Verify the password using BCryptPasswordEncoder
		    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		    if (existingAdmin != null && passwordEncoder.matches(existingAdminFromSession.getPassword(), existingAdmin.getPassword())) {
		        // Check if the existing admin has the ADMIN role
		        if ("ADMIN".equals(existingAdmin.getRole())) {
		            // Log staff details for debugging
		            System.out.println("Creating staff: " + staff); // Log the staff object

		            staffServiceImpl.createStaff(staff); // Proceed to create staff

		            response.setStatus("200");
		            response.setMessage("Staff created successfully");
		            response.setResult("success");
		            return response;
		        } else {
		            response.setStatus("403");
		            response.setMessage("Unauthorized: Only Admins can create staff");
		            response.setResult("unauthorized");
		        }
		    } else {
		        response.setStatus("403");
		        response.setMessage("Unauthorized: Invalid email or password");
		        response.setResult("unauthorized");
		    }

		    return response; // Return the response at the end
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
          
		@GetMapping("/getone/{id}")
		public Optional<Admin> findById(@PathVariable Long id) {
		    return adminServiceImpl.findById(id);
		}
		
		@GetMapping("/getClientsByAdminId/{id}")
		public ResponseEntity<Map<String, Object>> getClientsByAdminId(@PathVariable Long id) {
		    Optional<Admin> adminOptional = adminServiceImpl.findById(id);
		    Map<String, Object> response = new HashMap<>();

		    if (adminOptional.isPresent()) {
		        Admin admin = adminOptional.get();
		        response.put("admin", admin);

		        // Fetch clients associated with the admin
		        Set<Client> clients = admin.getClients(); // Ensure this returns the correct set

		        // Add clients and client count to the response
		        response.put("clients", clients); // This may need a DTO conversion
		        response.put("clientCount", clients.size());

		        return ResponseEntity.ok(response);
		    } else {
		        response.put("message", "Admin not found");
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		    }
		}
		    
		}

