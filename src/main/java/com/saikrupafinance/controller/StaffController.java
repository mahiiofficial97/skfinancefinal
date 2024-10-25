package com.saikrupafinance.controller;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saikrupafinance.model.JsonResponseclass;
import com.saikrupafinance.model.PasswordResetToken;
import com.saikrupafinance.model.Staff;
import com.saikrupafinance.service.StaffServiceImpl;
import com.saikrupafinance.service.PasswordResetTokenService; // Assuming these services exist
import com.saikrupafinance.service.EmailService; // Assuming email service exists
import jakarta.servlet.http.HttpSession;

@RequestMapping("/staff")
@RestController
public class StaffController {

	@Autowired
	StaffServiceImpl staffServiceImpl;

	@Autowired
	PasswordResetTokenService passwordResetTokenService; // Autowire the service

	@Autowired
	EmailService emailService; // Autowire the email service

	@PostMapping("/login")
	public JsonResponseclass login(@RequestBody Staff staff, HttpSession session) {
		JsonResponseclass response = new JsonResponseclass();

		// Check if staff is already logged in by checking the session
		Staff sessionStaff = (Staff) session.getAttribute("staffinstance");
		if (sessionStaff != null) {
			response.setStatus("400");
			response.setMessage("Staff is already logged in");
			response.setResult("failure");
			return response; // Stop further execution if already logged in
		}

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
			session.removeAttribute("staffinstance");
			response.setStatus("200");
			response.setMessage("Logout successfully");
			response.setResult("success");
		}

		return response;
	}

	@PostMapping("/forgot-password")
	public JsonResponseclass forgotPassword(@RequestParam("email") String email) {
		JsonResponseclass response = new JsonResponseclass();

		System.out.println("Received email: " + email); // Debugging line

		// Find staff by email
		Staff staff = staffServiceImpl.findByEmail(email);

		if (staff == null) {
			response.setStatus("400");
			response.setMessage("Email not found");
			response.setResult("failure");
			return response;
		}

		// Generate reset token
		String token = UUID.randomUUID().toString();
		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setToken(token);
		resetToken.setStaff(staff);
		resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600000)); // Token expires in 1 hour

		// Save the token
		passwordResetTokenService.save(resetToken); // This should work if the service is set up correctly

		// Send reset email
		String resetUrl = "http://localhost:8080/staff/reset-password?token=" + token;
		emailService.sendSimpleMessage(email, "Reset Password", "Click the link to reset your password: " + resetUrl);

		response.setStatus("200");
		response.setMessage("Password reset email sent");
		response.setResult("success");

		return response;
	}

}
