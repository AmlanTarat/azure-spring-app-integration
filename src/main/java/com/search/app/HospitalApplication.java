package com.search.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.azure.spring.messaging.implementation.annotation.EnableAzureMessaging;

@ComponentScan
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableAzureMessaging
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
       
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
    /*@Configuration
    public class CredentialManager extends DefaultAzureCredentialBuilder{

        @Bean(name="azureCredentialBuilderFactory")
        TokenCredential tokenCredential() {
            return new DefaultAzureCredentialBuilder().build();
        }
       
    }*/

    /*@Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all auto-configured defaults to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some settings if necessary.
        return factory;
    }*/

}
