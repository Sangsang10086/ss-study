package com.aop.biz;

import com.aop.handle.Loggable;
import com.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerBiz {
    private static final Logger logger = LoggerFactory.getLogger(LoggerBiz.class);

    @Loggable
    public Result<Object> logger() throws InterruptedException {
        logger.info("==== 方法体 ====");
        String data = "hello world";
        Thread.sleep(2000);
        return Result.success(data);
    }
}
