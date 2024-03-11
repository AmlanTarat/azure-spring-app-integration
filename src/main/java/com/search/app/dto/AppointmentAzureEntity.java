package com.search.app.dto;

import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.table.TableServiceEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name="APPOINTMENT")
public class AppointmentAzureEntity extends TableServiceEntity {

    @Id
    private String appId;
	private String email;
	private String docId;
	private String docName;
	private String docSpecial;
	private String status;
	private String date;

	public AppointmentAzureEntity(String appointmentId1, String status1){
        this.partitionKey = appointmentId1;
        this.rowKey = status1;
    }

    public AppointmentAzureEntity(){    }


    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId=docId;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocSpecial() {
		return docSpecial;
	}
	public void setDocSpecial(String docSpecial) {
		this.docSpecial = docSpecial;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
