package com.search.app.dto;

import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.table.TableServiceEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name="DOCTOR")
public class DoctorAzureEntity extends TableServiceEntity {

    @Id
    private String email;
    private String password;
    private String name;
    private String degree;
    private String specialization;
    private String city;
    private String state;

	public DoctorAzureEntity(String email1, String password1){
        this.partitionKey = email1;
        this.rowKey = password1;
    }

    public DoctorAzureEntity(){    }


    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
