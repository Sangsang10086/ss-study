package com.lock.biz;

import org.springframework.stereotype.Service;

@Service
public class SimpleRedisLock implements ILock{
    private final StringRedisTemplate stringRedisTemplate;
    private final String name;

    public SimpleRedisLock(StringRedisTemplate stringRedisTemplate, String name) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.name = name;
    }

    private static final String KEY_PREFIX = "lock:";


    @Override
    public boolean tryLock(long timeoutSec) {
        //获取线程标识
        long threadId = Thread.currentThread().getId();

        //获取锁
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(KEY_PREFIX + name,)
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public void close() throws Exception {

    }
}
