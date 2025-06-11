package com.rabbitmq.controller;

import com.rabbitmq.config.RabbitMqConfig;
import com.rabbitmq.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitMq")
public class RabbitMqController {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 封装发送消息接口
     * @param exchange
     * @param routingKey
     * @param data
     * @param delay
     * @return
     */
    @RequestMapping(value = "/MqSender", method = RequestMethod.POST)
    public Result<Object> SendQueue(@RequestParam("exchange") String exchange,
                          @RequestParam("routingKey") String routingKey,
                          @RequestParam("data") Object data,
                          @RequestParam("delay") String delay){
        logger.info("==== 开始发送消息 ====");
        rabbitTemplate.convertAndSend(exchange, routingKey, Result.success(data), message -> {
            message.getMessageProperties().setDelayLong(Long.valueOf(delay));
            return message;
        });
        return Result.success("发送成功",data);
    }
}
