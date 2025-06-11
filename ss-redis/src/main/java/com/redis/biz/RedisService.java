package com.redis.ssredis.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {           //创建一个 Redis 服务类来封装 Redis 操作


    /**
     * RedisTemplate 是 Spring Data Redis 提供的模板类，用于操作 Redis 数据库。
     * 通过 RedisTemplate 可以执行 Redis 所有命令，如设置缓存、获取缓存、删除缓存、判断缓存是否存在等。
     * 如果项目需要更灵活的 Redis 操作（如自定义缓存逻辑、复杂数据结构、分布式锁），建议封装 Redis 服务类
     */
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    //设置缓存,存储数据（键值对）
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    //存储数据并设置过期时间
    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    //获取缓存数据
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    //删除缓存数据
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    //判断缓存数据是否存在
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
