package com.resilience4j.openfeign;

import com.alibaba.nacos.api.model.v2.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "rabbitMq-service")
public interface RabbitMqClient {


    @RequestMapping(value = "/rabbitMq/MqSender", method = RequestMethod.POST)
    public Result<Object> sendQueue(@RequestParam("exchange") String exchange,
                                    @RequestParam("routingKey") String routingKey,
                                    @RequestParam("data") Object data,
                                    @RequestParam("delay") String delay);
}
