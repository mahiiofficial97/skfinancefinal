package com.saikrupafinance.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
@Entity
@Table(name="AdminTable")
@Data
public class Admin {
	

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String username;
	    private String password;
	    private String email;
	    private String role; // "ADMIN" or "STAFF"
	    private String isActiveAdmin = "Active";
	    
	    @Temporal(TemporalType.DATE)
		@CreationTimestamp
	    @DateTimeFormat(pattern = "dd-MM-yyyy")
		private Date Admincreated;

	 // One Admin can create multiple Clients
	    @OneToMany(mappedBy = "admin") // Correctly mapped
	    private Set<Client> clients; // Initialize with a Set if needed

	

}
