package com.search.app.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class Publisher implements CommandLineRunner{

    @Autowired
    EmailFormat emailFormat;

    @Autowired
    private JmsTemplate  jmsTemplate;

    private static final String TOPIC_NAME = "email_topic";

    public Publisher(EmailFormat emailFormat){
        this.emailFormat=emailFormat;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending Message to TOPIC"+ emailFormat);
        //serviceBusTemplate.sendAsync(TOPIC_NAME, MessageBuilder.withPayload(emailFormat).build());
        jmsTemplate.convertAndSend(TOPIC_NAME, emailFormat.toString());
        System.out.println("Message sent to TOPIC"+ emailFormat);
    }
    
}
