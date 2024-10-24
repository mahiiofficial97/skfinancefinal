package com.saikrupafinance.service;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saikrupafinance.model.Client;
import com.saikrupafinance.model.Staff;
import com.saikrupafinance.repository.ClientRepository;
import com.saikrupafinance.repository.StaffRepository;

import jakarta.persistence.EntityNotFoundException;
@Service
public class ClientService {
	@Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StaffRepository staffRepository;

    public void createClient(Client client) {
        // Check if the client already exists based on email or other unique attributes
        Client existingClient = clientRepository.findByEmail(client.getEmail());

        if (existingClient != null) {
            throw new RuntimeException("Client already exists with this email.");
        }

        // Save the new client to the database
        clientRepository.save(client);
    }

    // Original method for Staff to create a client
    public void createClient(Client client, Long staffId) {
        // Check if the client already exists based on email or other unique attributes
        Client existingClient = clientRepository.findByEmail(client.getEmail());

        if (existingClient != null) {
            throw new RuntimeException("Client already exists with this email.");
        }

        // Set the staff object if a staffId is provided
        if (staffId != null) {
            Staff staff = staffRepository.findById(staffId)
                    .orElseThrow(() -> new RuntimeException("Staff with ID " + staffId + " does not exist."));
            client.setStaff(staff);
        }

        // Save the new client to the database
        clientRepository.save(client);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
    
    
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
    public void updateClient(Client client, Long staffId) {
        // Check if the client exists
        if (client.getId() == null) {
            throw new IllegalArgumentException("Client ID must not be null");
        }

        Optional<Client> existingClient = clientRepository.findById(client.getId());
        if (existingClient.isPresent()) {
            Client updatedClient = existingClient.get();
            // Update fields as necessary
            updatedClient.setClientName(client.getClientName());
            updatedClient.setEmail(client.getEmail());
            updatedClient.setClientPhone(client.getClientPhone());
            updatedClient.setAddress(client.getAddress());
            updatedClient.setKycStatus(client.getKycStatus());
            
            // Optionally, set the staff member who is updating the client
            if (staffId != null) {
                Staff staff = staffRepository.findById(staffId)
                        .orElseThrow(() -> new RuntimeException("Staff with ID " + staffId + " does not exist."));
                updatedClient.setStaff(staff);
            }

            // Save the updated client
            clientRepository.save(updatedClient);
        } else {
            throw new EntityNotFoundException("Client not found");
        }
    }
    
    
    
    
    
    // reassign part
    public void reassignStaffToClient(Long id, Long staffId) {
        // Fetch the client by ID
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            throw new RuntimeException("Client not found with ID: " + id);
        }

        // Fetch the staff member by ID
        Optional<Staff> staffOptional = staffRepository.findById(staffId);
        if (!staffOptional.isPresent()) {
            throw new RuntimeException("Staff not found with ID: " + staffId);
        }

        // Assign the new staff member to the client
        Client client = clientOptional.get();
        client.setStaff(staffOptional.get());

        // Save the updated client entity
        clientRepository.save(client);
    }
    
    
   

	public Page<Client> findAllClients(PageRequest pageable) {
		return clientRepository.findAll(pageable);
	}

}