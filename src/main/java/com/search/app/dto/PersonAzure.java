package com.search.app.dto;

import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.table.TableServiceEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name="PERSON")
public class PersonAzure extends TableServiceEntity{

    @Id
    private String email;
	private String password;
    

    public PersonAzure(String lastName, String firstName){
        this.partitionKey = lastName;
        this.rowKey = firstName;
    }

    public PersonAzure(){    }

    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
}
