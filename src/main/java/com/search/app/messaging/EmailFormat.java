package com.search.app.messaging;

import org.springframework.context.annotation.Configuration;

@Configuration
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailFormat /*implements Serializable*/{

   // private static final long serialVersionUID = -295422703255886286L;
    
    public String date;
    public String email;
    public String doctorName;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getEmailId() {
        return email;
    }
    public void setEmailId(String emailId) {
        this.email = emailId;
    }

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
}
