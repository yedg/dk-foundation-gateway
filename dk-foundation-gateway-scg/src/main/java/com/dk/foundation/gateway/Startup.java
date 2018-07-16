package com.dk.foundation.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class Startup {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Startup.class, args);
    }

}
