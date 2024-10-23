package com.saikrupafinance.model;

public class DTO {
	private int id;
	private String clientName; // Name of the client
    private String email; // Email of the client
    private String clientPhone; // Phone number of the client
    private String address;
    private String kycStatus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getKycStatus() {
		return kycStatus;
	}
	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}
	@Override
	public String toString() {
		return "DTO [id=" + id + ", clientName=" + clientName + ", email=" + email + ", clientPhone=" + clientPhone
				+ ", address=" + address + ", kycStatus=" + kycStatus + "]";
	}
    
	
	

}
