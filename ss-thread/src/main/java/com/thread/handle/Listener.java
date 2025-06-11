package com.thread.handle;

import com.thread.event.Event1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    /**
     * 创建不同的监听器，用于监听同一个事件中的不同逻辑，
     * 一旦事件被发布， 则会调用监听器中的方法
     *
     * @param event
     */
    @Async("taskExecutor")
    @EventListener
    public void listenEventData(Event1 event) {
        logger.info("==== listenEventData ====");
        try {
            final String data = event.getData();
            logger.info("{} 开始处理事件: {}", Thread.currentThread().getName(), data);
            // 模拟耗时操作
            Thread.sleep(10000);
            logger.info(" {}事件处理完成", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    @Async("taskExecutor")
    @EventListener
    public void listenEventMsg(Event1 event) {
        logger.info("==== listenEventMsg ====");
        try {
            final String msg = event.getMsg();

            logger.info("{} 获取到的数据为: {}", Thread.currentThread().getName(), msg);

            Thread.sleep(2000);

            logger.info(" {}事件处理完成", Thread.currentThread().getName());

            // todo 模拟异常
//            int result = 10 / 0; // 这里会抛出除零异常

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
