package com.search.app.messaging;

import org.springframework.context.annotation.Configuration;

@Configuration
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailFormat /*implements Serializable*/{

   // private static final long serialVersionUID = -295422703255886286L;
    
    public String name;
    public String emailId;

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
