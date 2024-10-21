package com.saikrupafinance.dto;

import com.saikrupafinance.model.Admin;
import com.saikrupafinance.model.Staff;
public class StaffCreationRequest {
    private Admin admin;
    private Staff staff;

    // Getters and Setters
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
