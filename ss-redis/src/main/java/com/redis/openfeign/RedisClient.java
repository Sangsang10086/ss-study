package com.redis.openfeign;

import com.common.redis.Redis;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("redis-service")
public interface RedisClient {

    @RequestMapping(value = "/redis/set",method = RequestMethod.POST)
    void set(Redis redis);

    @RequestMapping(value = "/redis/set",method = RequestMethod.POST)
    void setByTimeout(Redis redis);

    @RequestMapping(value = "/redis/get",method = RequestMethod.GET)
    Object get(Redis redis);

    @RequestMapping(value = "/redis/delete",method = RequestMethod.DELETE)
    void delete(Redis redis);

    @RequestMapping(value = "/redis/hasKey",method = RequestMethod.GET)
    boolean hasKey(Redis redis);
}
