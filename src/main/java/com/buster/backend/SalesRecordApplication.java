package com.buster.backend;

import com.buster.backend.config.SwaggerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class SalesRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesRecordApplication.class, args);
    }

}
