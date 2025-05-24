package com.bthndlr.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataGeneratorConfig {

    @Value("${data.client.type:gpt}") 
    private String dataClientType;

    @Bean
    public IDataGenerator dataService() {
        switch (dataClientType.toLowerCase()) {
            case "gpt":
                return new GPTextension(); 
            default:
                throw new RuntimeException("Unsupported HttpClient type: " + dataClientType);
        }
    }
}
