package com.saikrupafinance.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String clientName; // Name of the client
    private String email; // Email of the client
    private String clientPhone; // Phone number of the client
    private String address;
    private String kycStatus;   
    private String image;

    // Foreign key to link the client to a staff member
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = true) // Can be null
    private Staff staff; // Staff member who created the client

    // Foreign key to link the client to an admin member
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true) // Can be null
    private Admin admin; // Admin member who created the client

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date clientCreatedAt;
    
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastUpdate = new Date(); // Default to current date
}
