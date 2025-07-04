package com.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsAuthApplication.class, args);
    }

}
