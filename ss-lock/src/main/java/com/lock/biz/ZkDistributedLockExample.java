package com.lock.biz;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ZkDistributedLockExample {

    private static final String ZK_ADDRESS = "127.0.0.1:2181";
    private static final String LOCK_PATH = "/distributed_lock";

    public static void main(String[] args) {
        CuratorFramework client = null;
        try {
            //1.创建Curator客户端
            client = CuratorFrameworkFactory.builder()
                    .connectString(ZK_ADDRESS)
                    .sessionTimeoutMs(60000)
                    .retryPolicy(new ExponentialBackoffRetry(1000,3)) // 重试策略，初始等待1秒，重试3次
                    .build();

            // 2.启动客户端
            client.start();
            client.blockUntilConnected(); // 阻塞当前线程，直到连接成功
            log.info("创建Curator客户端成功");

            //3.创建分布式锁实例
            InterProcessMutex lock = new InterProcessMutex(client, LOCK_PATH);

            //模拟多个线程同时获取锁
            for (int i = 0; i < 5; i++) {
                new Thread(()->{
                    try{
                        log.info("线程{}开始获取锁", Thread.currentThread().getName());
                        if(lock.acquire(10, TimeUnit.SECONDS)){
                            try {
                                log.info("线程{}获取锁成功", Thread.currentThread().getName());
                                Thread.sleep(5000);
                            }finally {
                                lock.release();
                                log.info("线程{}释放锁",Thread.currentThread().getName());
                            }
                        }else{
                            log.info("{}获取锁失败",Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        log.error("获取锁失败：{}", e.getMessage(), e);
                    }
                },"Thread-" + i).start();
            }

            Thread.sleep(15000);
        } catch (InterruptedException e) {
            log.error("创建Curator客户端失败：{}", e.getMessage(), e);
        }finally {
            if(client != null){
                client.close();
            }
        }
    }
}
