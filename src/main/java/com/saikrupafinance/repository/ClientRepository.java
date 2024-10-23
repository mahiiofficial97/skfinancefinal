package com.saikrupafinance.repository;

import java.awt.print.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saikrupafinance.model.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("SELECT a FROM Client a WHERE a.email=:email")
	Client findByEmail(String email);

	org.springframework.data.domain.Page<Client> findAll(Pageable pageable);

	

	

//	Optional<Client> findbymobleno(String clientPhone);

	//Optional<Client> findByMobileNumber(String clientPhone);
	
	

	

}
