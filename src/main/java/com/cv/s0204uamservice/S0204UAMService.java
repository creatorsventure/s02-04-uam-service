package com.cv.s0204uamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class S0204UAMService {

    public static void main(String[] args) {
        SpringApplication.run(S0204UAMService.class, args);
    }

}
