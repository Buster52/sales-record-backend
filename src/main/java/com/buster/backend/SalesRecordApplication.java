package com.buster.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SalesRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesRecordApplication.class, args);
    }

}
