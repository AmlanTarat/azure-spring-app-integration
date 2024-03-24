package com.search.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableJms
public class HospitalApplication {

    @Autowired
    public static JmsTemplate jmsTemplate;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HospitalApplication.class, args);
        jmsTemplate = context.getBean(JmsTemplate.class);
    }

	@Configuration
	public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/","classpath:/img/")
            .setCachePeriod(0);
        }
	}

}
