package com.search.app.messaging;

import org.springframework.beans.factory.annotation.Autowired;

import com.search.app.HospitalApplication;

public class Publisher {

    @Autowired
    EmailFormat emailFormat;

    private static final String TOPIC_NAME = "email_topic";

    public Publisher(EmailFormat emailFormat){
        this.emailFormat=emailFormat;
    }

    public void publishMessage(){
        
        HospitalApplication.jmsTemplate.convertAndSend(TOPIC_NAME, emailFormat);
        System.out.println("Message sent to TOPIC"+ emailFormat);
    }
    
}
