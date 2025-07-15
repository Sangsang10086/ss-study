package com.lock.biz;

import org.springframework.stereotype.Service;


@Service
public interface ILock extends AutoCloseable{       // { Redis实现分布式锁的接口 }
    /**
     * 尝试获取锁
     *
     * @param timeoutSec 锁持有的超时时间，过期后自动释放
     * @return true代表获取锁成功；false代表获取锁失败
     */
    boolean tryLock(long timeoutSec);

    /**
     * 释放锁
     */
    void unlock();


}
