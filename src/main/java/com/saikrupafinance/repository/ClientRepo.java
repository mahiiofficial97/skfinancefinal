package com.saikrupafinance.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saikrupafinance.model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
	Optional<Client> findByClientPhone(String phone);

	Optional<Client> getById(Long id);
	
	//Page<Client> findAll(Pageable pageable);

	//Optional<Client> findbyclientphone(String clientPhone);

	//Optional<Client> findbyclientphone(String clientPhone);
}
