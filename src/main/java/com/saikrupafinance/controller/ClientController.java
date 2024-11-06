package com.saikrupafinance.controller;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saikrupafinance.model.Admin;
import com.saikrupafinance.model.Client;
import com.saikrupafinance.model.JsonResponseclass;
import com.saikrupafinance.model.Staff;
import com.saikrupafinance.service.AdminServiceImpl;
import com.saikrupafinance.service.ClientService;
import com.saikrupafinance.service.StaffServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private StaffServiceImpl staffServiceImpl;

    // Endpoint to create a client (accessible by both Admin and Staff)
    @PostMapping("/create")
    public JsonResponseclass createClient(@RequestBody Client client,
                                          @RequestParam(required = false) Long adminId,
                                          @RequestParam(required = false) Long staffId,
                                          HttpSession session) {
        JsonResponseclass response = new JsonResponseclass();

        // Validate the session for admin
        Admin existingAdminFromSession = (Admin) session.getAttribute("admininstance");
        if (existingAdminFromSession != null && adminId != null && adminId.equals(existingAdminFromSession.getId())) {
            client.setAdmin(existingAdminFromSession); // Associate client with Admin
            clientService.createClient(client, null); // No staff association for Admin
            response.setStatus("200");
            response.setMessage("Client created successfully by Admin.");
            response.setResult("success");
            return response;
        }

        // Validate the session for staff
        Staff existingStaffFromSession = (Staff) session.getAttribute("staffinstance");
        if (existingStaffFromSession != null && staffId != null && staffId.equals(existingStaffFromSession.getId())) {
            Optional<Staff> existingStaff = staffServiceImpl.findById(staffId);
            if (existingStaff.isPresent() && "STAFF".equals(existingStaff.get().getRole())) {
                client.setStaff(existingStaff.get()); // Associate client with Staff
                clientService.createClient(client, staffId); // Staff ID association
                response.setStatus("200");
                response.setMessage("Client created successfully by Staff.");
                response.setResult("success");
                return response;
            }
        }

        // Updated unauthorized response
        response.setStatus("403");
        response.setMessage("Unauthorized: Please log in as an Admin or Staff to create clients.");
        response.setResult("unauthorized");
        return response;
    }

    
    //get the 
    @GetMapping("/all")
    public List<Client> getAllClients() {
        return clientService.findAllClients();
    }
    
    
    //adin get the client by mobile number
    @GetMapping("/getone/{clientPhone}")
	public Client getShopById(@PathVariable String clientPhone) {

		Optional<Client> data = clientService.getclientphone(clientPhone);

		if (data.isPresent()) {
			return data.get();
		}
		throw new RuntimeException("Data not present" + clientService);
	}
    
    
    
//    
//    @GetMapping("/all1")
//    public ResponseEntity<?> getAllClients(Pageable pageable) {
//        try {
//            Page clients = clientService.findAllClients(pageable);
//            return ResponseEntity.ok(clients);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving clients: " + e.getMessage());
//        }
    

    
    }