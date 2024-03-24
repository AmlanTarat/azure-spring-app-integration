package com.search.app.messaging;

import java.io.Serializable;

public class EmailFormat implements Serializable{

    private static final long serialVersionUID = -295422703255886286L;
    
    private String name;
    private String emailId;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
}
