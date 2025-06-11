package com.thread.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;

@Configuration
public class AsyncExceptionConfig implements AsyncConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(AsyncExceptionConfig.class);

    /*
    * 导入线程池
     */
    @Autowired
    @Qualifier("taskExecutor")
    private AsyncTaskExecutor asyncTaskExecutor;

    /*
    * 提供一个用于执行异步任务的线程池
    * 返回已经创建的线程池，线程池的配置在AsyncConfig类中
     */
    @Override
    public Executor getAsyncExecutor() {
        return asyncTaskExecutor;
    }

    /*
    * 异步任务异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            logger.error("异步任务异常: {},异常信息为{}", method.getName(),ex.getMessage());
        };
    }
}
