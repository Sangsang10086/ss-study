package com.aop.rest;

import com.aop.biz.LoggerBiz;
import com.common.filter.IgnoreToken;
import com.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AopController {

    @Autowired
    private LoggerBiz loggerBiz;

//    @IgnoreToken
    @RequestMapping(value = "/logger",method = RequestMethod.GET)
    public Result<Object> aop() throws InterruptedException {
        return loggerBiz.logger();
    }
}
