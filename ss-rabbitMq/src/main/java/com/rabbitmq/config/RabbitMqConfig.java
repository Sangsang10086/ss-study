package com.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(RabbitTemplate.class)       //有RabbitTemplate才加载
public class RabbitMqConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

    /**
     * 配置消息转换器
     * @return
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 配置RabbitTemplate,设置消息确认机制，消息返回机制
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置消息转换器
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        //消息是否从 生产者 -> 交换机
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                logger.info("消息未能到达交换机{}{}",correlationData,cause);
            }else {
                logger.info("消息已成功到达交换机");
            }

        });

        //消息是否从 交换机 -> 队列
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            logger.info("消息发送失败{}{}{}{}{}",returnedMessage.getMessage(),
                    returnedMessage.getReplyCode(),//代码
                    returnedMessage.getReplyText(),//消息
                    returnedMessage.getRoutingKey(),//路由Key
                    returnedMessage.getExchange()  //交换机
            );
        });

        return rabbitTemplate;
    }

}
