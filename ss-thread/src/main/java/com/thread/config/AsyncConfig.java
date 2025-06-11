package com.thread.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 创建一个线程池
     * @return
     */

    @Value("${async.pool.core-size}")
    private int corePoolSize;

    @Value("${async.pool.max-size}")
    private int maxPoolSize;

    @Value("${async.pool.queue-capacity}")
    private int queueCapacity;

    @Bean("taskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);    //核心线程数
        executor.setMaxPoolSize(maxPoolSize);    //最大线程数
        executor.setQueueCapacity(queueCapacity);  //队列容量
        executor.setThreadNamePrefix("threadPool-");
        executor.initialize();

        //绑定线程池指标到 MicroMeter


        return executor;
    }



}
