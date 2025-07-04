package com.aop.biz;

import com.aop.openfeign.RabbitMqClient;
import com.auth.openfeign.AuthClient;
import com.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerBiz {
    private static final Logger logger = LoggerFactory.getLogger(LoggerBiz.class);

    @Autowired
    private RabbitMqClient rabbitMqClient;

    @Autowired
    private AuthClient authClient;

//    @Loggable
    public Result<Object> logger() throws InterruptedException {
        logger.info("==== 方法体 ====");
        String data = "hello world";
//        rabbitMqClient.sendQueue("direct.exchange", "hello-key", data,"1000");
        Thread.sleep(2000);
        return Result.success(data);
    }
}
