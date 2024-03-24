package com.search.app.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Id;

@Component
public class AppointmentAzureDelete {

    @Id
    private String email;
	private String date;
	private String doctorName;

    public AppointmentAzureDelete(){    }

    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
    
}
