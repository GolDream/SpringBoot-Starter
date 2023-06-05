package com.goldream.middleware.whitelist.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.goldream.middleware.*"})
public class ApiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiTestApplication.class, args);
    }

}

