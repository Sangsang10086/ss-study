package com.allinto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ServletComponentScan
@EnableFeignClients(
        basePackages = {
                "com.auth.openfeign",
        }
)
public class AllIntoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllIntoApplication.class, args);
    }

}
