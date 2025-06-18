package com.thread.controller;

import com.thread.biz.ThreadBiz;
import com.thread.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thread")
public class threadController {
    private static final Logger logger = LoggerFactory.getLogger(threadController.class);

    @Autowired
    private ThreadBiz threadBiz;

    @RequestMapping(value = "/sendMsg",method = RequestMethod.POST)
    public Result<String> sendMsg(@RequestParam("email") String email, @RequestParam("data") String data){
        logger.info("注册成功邮件发送中...");
        //异步处理中间操作-手动处理第一个线程
        threadBiz.test1(email);
        threadBiz.test2(email);
        threadBiz.test3(email);

        // todo 对于需要所有线程执行完成之后的处理，可以采用如下方式
        //调用收集所有异步信息之后的处理逻辑
        threadBiz.test4(email);
        threadBiz.publishEvent(email,data);
        return Result.success("注册成功邮件发送中...",null);
    }
}
