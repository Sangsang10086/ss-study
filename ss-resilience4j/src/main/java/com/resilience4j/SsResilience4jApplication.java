package com.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SsResilience4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsResilience4jApplication.class, args);
    }

}
