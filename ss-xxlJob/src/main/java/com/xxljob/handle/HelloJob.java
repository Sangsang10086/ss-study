package com.xxljob.handle;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HelloJob {
    private static final Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Value("${server.port}")
    private String appPort;

    @XxlJob("helloJob")
    public ReturnT<String> hello() throws Exception {
        logger.info("==== xxlJob执行 ====");
        logger.info("helloJob：{},端口号{}", LocalDateTime.now(), appPort);
        return ReturnT.SUCCESS;
    }
}