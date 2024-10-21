package com.saikrupafinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saikrupafinance.model.JsonResponseclass;
import com.saikrupafinance.model.Staff;
import com.saikrupafinance.service.StaffServiceImpl;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/staff")
@RestController
public class StaffController {
	
	@Autowired
	StaffServiceImpl staffServiceImpl;
	
	@PostMapping("/login")
	public JsonResponseclass login(@RequestBody Staff staff, HttpSession session) {
	    JsonResponseclass response = new JsonResponseclass();

	    // Find staff by email
	    Staff existingStaff = staffServiceImpl.findByEmail(staff.getEmail());

	    // Check if the staff exists and if the password matches
	    if (existingStaff != null && staff.getPassword().equals(existingStaff.getPassword())) {
	        session.setAttribute("staffinstance", existingStaff); // Store staff instance in session
	        response.setStatus("200");
	        response.setMessage("Login successful");
	        response.setResult("success");
	    } else {
	        response.setStatus("400");
	        response.setMessage("Invalid email or password");
	        response.setResult("failure");
	    }

	    return response;
	}


	 // Endpoint for admin logout
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponseclass logoutAdmin(HttpSession session) {

		JsonResponseclass response = new JsonResponseclass();

		Object adminSession = session.getAttribute("staffinstance");

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
   


}
