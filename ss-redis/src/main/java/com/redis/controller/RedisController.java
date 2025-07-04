package com.redis.controller;

import com.common.biz.BaseBiz;
import com.common.redis.Redis;
import com.common.rest.BaseController;
import com.redis.biz.RedisBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sang
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/redis")
public class RedisController extends BaseController<RedisBiz, Redis> {
    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisBiz redisBiz;

    @RequestMapping(value = "/set",method = RequestMethod.POST)
    public void set(Redis redis){
        redisBiz.set(redis.getKey(),redis.getValue());
    }

    @RequestMapping(value = "/set",method = RequestMethod.POST)
    public void setByTimeout(Redis redis){
        redisBiz.set(redis.getKey(),redis.getValue(),redis.getTimeout(),redis.getUnit());
    }
    
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Object get(Redis redis){
        return redisBiz.get(redis.getKey());
    }
    
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public void delete(Redis redis){
        redisBiz.delete(redis.getKey());
    }
    
    @RequestMapping(value = "/hasKey",method = RequestMethod.GET)
    public boolean hasKey(Redis redis){
        return redisBiz.hasKey(redis.getKey());
    }
}
