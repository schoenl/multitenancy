package com.schoenl.multitenancy;

import com.schoenl.multitenancy.multitenancy.MultitenancyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MultitenancyProperties.class)
public class MultitenancyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultitenancyApplication.class, args);
    }

}
