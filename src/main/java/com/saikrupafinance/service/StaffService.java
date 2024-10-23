package com.saikrupafinance.service;

import java.util.List;
import java.util.Optional;
import com.saikrupafinance.model.Staff;

public interface StaffService {

    // Method to create a new staff member
    public void createStaff(Staff staff);

    // Method to find a staff member by their ID
    Optional<Staff> findById(Long staffId);

    // Method to get all staff members
    List<Staff> findAllStaff();

    // Method to delete a specific staff member by their ID
    void deleteSpecificStaff(Long staffId);

    // Method to update an existing staff member
    void updateStaff(Staff staff);
}
