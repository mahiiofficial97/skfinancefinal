package com.saikrupafinance.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Staff {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String staffname;
    private String password;
    private String email;
    private String role; // "ADMIN" or "STAFF"
    private String isActiveStaff = "Active";
    
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date Admincreated;

    // One Staff can have many Clients
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Client> clients = new ArrayList<>(); // Initialize with an ArrayList

    // Foreign key to link the Staff to an admin member
    @ManyToOne
    @JoinColumn(name = "admin_id") // Can be null
    private Admin admin; // Admin member who created the client

    
   
}
