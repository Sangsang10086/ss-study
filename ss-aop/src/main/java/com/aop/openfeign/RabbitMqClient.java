package com.aop.openfeign;

import com.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rabbitMq-service")
public interface RabbitMqClient {

    /**
     * 发送消息
     * @param exchange 交换机
     * @param routingKey 路由key
     * @param data 数据
     * @param delay 延迟时间
     * @return
     */
    @RequestMapping(value = "/rabbitMq/MqSender", method = RequestMethod.POST)
    public Result<Object> sendQueue(@RequestParam("exchange") String exchange,
                                    @RequestParam("routingKey") String routingKey,
                                    @RequestParam("data") Object data,
                                    @RequestParam("delay") String delay);
}
