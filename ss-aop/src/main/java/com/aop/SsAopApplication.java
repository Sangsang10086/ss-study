package com.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SsAopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsAopApplication.class, args);
    }

}
