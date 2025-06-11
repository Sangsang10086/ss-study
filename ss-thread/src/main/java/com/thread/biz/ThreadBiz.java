package com.thread.biz;

import com.thread.event.Event1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;


@Service
public class ThreadBiz {
    private static final Logger logger = LoggerFactory.getLogger(ThreadBiz.class);


    @Autowired
    private ApplicationEventPublisher eventPublisher;


    /**
     * 异步线程服务-简单
     * ==== 最常用的线程异步实现方法 ====
     * @param email
     */
    @Async("taskExecutor")
    public void test1(String email){
        try {
            sleep(1000);
            logger.info("线程1执行完成,发送的信息为{}", email);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("taskExecutor")
    public void test2(String email){
        try {
            sleep(10000);
            logger.info("线程2执行完成,发送的信息为{}", email);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Async("taskExecutor")
    public void test3(String email){
        try {
            sleep(5000);
            logger.info("线程3执行完成,发送的信息为{}", email);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }





    /**
     * 异步线程服务-. CompletableFuture（复杂异步编排）
     * 1.创建线程
     * 2.收集线程处理之后的结果，统一处理
     * @param email
     */
    @Async("taskExecutor")
    public CompletableFuture<String> CompletableFuture1(String email){
        try {
            sleep(1000);
            logger.info("线程1执行完成,发送的信息为{}", email);
            return CompletableFuture.completedFuture("线程1发送成功");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<String> CompletableFuture2(String email){
        try {
            sleep(10000);
            logger.info("线程2执行完成,发送的信息为{}", email);
            return CompletableFuture.completedFuture("线程2发送成功");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<String> CompletableFuture3(String email){
        try {
            sleep(5000);
            logger.info("线程3执行完成,发送的信息为{}", email);
            return CompletableFuture.completedFuture("线程3发送成功");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 等待所有线程执行完成之后执行，异步收集所有的信息再进行处理
     */
    public void test4(String email){
        CompletableFuture.allOf(CompletableFuture1(email),CompletableFuture2(email),CompletableFuture3(email))
                .thenRun(() -> {
                    logger.info("所有线程执行完成");
                });
    }


    /**
     * 异步线程服务-事件监听
     * 1.定义事件-定义在事件监听类中
     * 2.发布事件
     * 3.监听事件-监听在事件监听类中
     */

    //发布事件
    public void publishEvent(String message,String data) {
        // 创建订单逻辑...
        logger.info("==== 主逻辑 == 创建订单逻辑 ====");

        //使用新建事件对象的方法来发布事件
        eventPublisher.publishEvent(new Event1(this, message,data));
    }
}
