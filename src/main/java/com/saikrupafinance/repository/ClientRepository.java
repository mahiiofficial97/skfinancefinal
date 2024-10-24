package com.saikrupafinance.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saikrupafinance.model.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("SELECT a FROM Client a WHERE a.email=:email")
	Client findByEmail(String email);

	

	

	

//	Optional<Client> findbymobleno(String clientPhone);

	//Optional<Client> findByMobileNumber(String clientPhone);
	
	

	

}
