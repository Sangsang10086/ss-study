package com.resilience4j.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    /**
     * 重试
     * @return
     */
    @Bean
    public Retryer retryer(){
        // Retryer.Default（ long period, long maxPeriod, int maxAttempts）
//        return new Retryer.Default(100,1,3);
        return Retryer.NEVER_RETRY;
    }
}