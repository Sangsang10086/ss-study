package com.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfig {
    private static final Logger logger = LoggerFactory.getLogger(GlobalFilterConfig.class);

    /**
     * 全局过滤器
     * @return
     */
    @Bean
    @Order(-1)
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {

            //前置处理
            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .header("X-Global-Filter","activated")
                    .build();

            //记录请求开始时间
            long startTime = System.currentTimeMillis();

            return chain.filter(exchange.mutate().request(request).build())
                    .then(Mono.fromRunnable(()->{
                        //后置处理
                        long duration = System.currentTimeMillis() - startTime;
                        logger.info("方法 {} 花费 {} ms", exchange.getRequest().getURI(), duration);
                    }));
        };
    }
}
