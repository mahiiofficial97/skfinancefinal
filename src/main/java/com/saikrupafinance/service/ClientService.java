package com.saikrupafinance.service;



import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saikrupafinance.model.Client;
import com.saikrupafinance.model.Staff;
import com.saikrupafinance.repository.AdminRepository;
import com.saikrupafinance.repository.ClientRepo;
import com.saikrupafinance.repository.ClientRepository;
import com.saikrupafinance.repository.StaffRepository;

@Service
public class ClientService {
	@Autowired
    private AdminRepository adminRepository;
	
	@Autowired
	private ClientRepo repo;
	

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
    
	public Optional<Client> getclientphone(String clientPhone) {
		return repo.findByClientPhone(clientPhone);
		
	}

	public Optional<Client> getclientbyid(Long id) {
		return repo.getById(id);
		
	}

//	   public Page<Client> findAllClients(Pageable pageable) {
//	        return clientRepository.findAll(pageable);
//	    }
	
	
}