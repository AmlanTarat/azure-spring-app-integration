package com.search.app.messaging;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Publisher {

    @Autowired
    EmailFormat emailFormat;

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    JmsTemplate  jmsTemplate;

    private static final String TOPIC_NAME = "email_topic";

    @PostConstruct
    public void initialize(){
        jmsTemplate = beanFactory.getBean(JmsTemplate.class);
    }

    public Publisher(EmailFormat emailFormat){
        this.emailFormat=emailFormat;
        
    }

    
    public void publish(JmsTemplate jmsTemplate1) throws Exception {
        System.out.println("Sending Message to TOPIC"+ emailFormat);
        //serviceBusTemplate.sendAsync(TOPIC_NAME, MessageBuilder.withPayload(emailFormat).build());
        //JmsTemplate  jmsTemplate = context.getBean(JmsTemplate.class);
        System.out.println("JMS Template in publish method***"+jmsTemplate);
        jmsTemplate1.convertAndSend(TOPIC_NAME, emailFormat.toString());
        
        System.out.println("Message sent to TOPIC"+ emailFormat);
    }
    
}
