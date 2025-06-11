package com.rabbitmq.handle;


import com.rabbitmq.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class StoreListener {
    private static final Logger logger = LoggerFactory.getLogger(StoreListener.class);

    // todo 将监听方法提取到common模块中，后续对监听方法进行重写

    /**
     * 监听direct.queue队列
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            //队列名、持久化、参数（惰性队列）
            value = @Queue(
                    name = "direct.queue" ,
                    durable = "true",
                    arguments = @Argument(name = "x-queue-mode", value = "lazy")),     //队列类型，惰性队列
            //交换机名、类型
            exchange = @Exchange(
                    name = "direct.exchange" ,
                    type = "x-delayed-message",                                       //定义延时消息交换机类型
                    durable = "true",                                                  //持久化
                    arguments = @Argument(name = "x-delayed-type", value = "direct")   //延时消息交换机类型
            ),
            //队列绑定的key
            key = "hello-key"
    ))
    public void listenDirectQueue(Result<String> msg){
        logger.info("==== 消息正在消费... ====");
        logger.info("消息: {}", msg);
        logger.info("==== 消息消费完毕!!! ====");
    }
}
