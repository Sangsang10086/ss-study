package com.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SsAuthApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "12345";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }

}
