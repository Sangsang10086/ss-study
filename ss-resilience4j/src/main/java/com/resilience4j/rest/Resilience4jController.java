package com.resilience4j.rest;

import com.alibaba.nacos.api.model.v2.Result;
import com.resilience4j.openfeign.RabbitMqClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/resilience4j")
public class Resilience4jController {
    private static final Logger logger = LoggerFactory.getLogger(Resilience4jController.class);


    /**
     * 执行顺序： Retry ( CircuitBreaker ( RateLimiter ( TimeLimiter ( Bulkhead ( Function ) ) ) ) )
     */




    @Autowired
    private RabbitMqClient rabbitMqClient;

    /**
     * 《 远程调用 熔断器 》
     * 针对openfeign服务调用来进行的fallback
     *
     * 降级回调处理，其中name为断路器实例名需要在yaml中进行启用相关配置才能实现，否则只能实现降级，fallbackMethod为降级处理方法名称
     */
    @RequestMapping(value = "/MqSender",method = RequestMethod.POST)
    @CircuitBreaker(name = "resilience4j-service",fallbackMethod = "openfeignFallback")
    public Result<Object> MqSender(){
        return rabbitMqClient.sendQueue("direct.exchange", "hello-key", "hello","1000");
    }

    public Result<Object> openfeignFallback(Throwable throwable) {
        return Result.success("服务器异常");
    }

    /**
     * 《 熔断策略 》
     * 针对controller来进行的fallback
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/Fallback",method = RequestMethod.GET)
    @CircuitBreaker(name = "resilience4j-service",fallbackMethod = "Fallback")
    public String resilience4jFallbackTest() throws Exception{
        int age = 10/0;
        return "success";
    }

    public String Fallback(Throwable throwable) {            //注意值只携带一个参数即可，参数为Throwable
        return "Fallback response due to: " + throwable.getMessage();
    }


    /**
     * 《 隔离策略 》
     * 针对controller来进行的bulkhead fallback
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/Bulkhead",method = RequestMethod.GET)
    @Bulkhead(name = "resilience4j-service",fallbackMethod = "BulkheadFallback",type = Bulkhead.Type.SEMAPHORE)
    public CompletableFuture<String> Bulkhead() {
        return CompletableFuture.supplyAsync(() -> {
            String beginDate = new Date().toString();
            // 模拟耗时操作
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String endDate = new Date().toString();
            return beginDate + "----->" + endDate;
        });
    }

    public CompletableFuture<String> BulkheadFallback(Throwable throwable) {     //注意值只携带一个参数即可，参数为Throwable
        return CompletableFuture.completedFuture("线程耗尽，请稍后重试");
    }



    /**
     * 《 线程池 隔离策略 》
     * 针对controller来进行的bulkhead fallback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/BulkheadPool",method = RequestMethod.GET)
    @Bulkhead(name = "resilience4j-service",fallbackMethod = "BulkheadPoolFallback",type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> BulkheadPool() {
        return CompletableFuture.supplyAsync(() -> {
            String beginDate = new Date().toString();
            // 模拟耗时操作
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String endDate = new Date().toString();
            return beginDate + "----->" + endDate;
        });
    }


    public CompletableFuture<String> BulkheadPoolFallback(Throwable throwable) {     //注意值只携带一个参数即可，参数为Throwable
        return CompletableFuture.completedFuture("线程耗尽，请稍后重试");
    }


    /**
     * 《 限流策略 》
     * 针对controller来进行的rateLimiter fallback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/RateLimiter",method = RequestMethod.GET)
    @RateLimiter(name = "resilience4j-service",fallbackMethod = "RateLimiterFallback")
    public String RateLimiter(){
        return "success";
    }

    public String RateLimiterFallback(Throwable throwable) {            //注意值只携带一个参数即可，参数为Throwable
        return "你被限流了，禁止访问/(ㄒoㄒ)/~~";
    }



    /**
     * 《 超时策略 》
     * 针对controller来进行的timeLimiter fallback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/TimeLimiter",method = RequestMethod.GET)
    @RateLimiter(name = "resilience4j-service",fallbackMethod = "TimeLimiterFallback")
    public String TimeLimiter(){
        return "success";
    }

    public String TimeLimiterFallback(Throwable throwable) {            //注意值只携带一个参数即可，参数为Throwable
        return "你超时了，禁止访问/(ㄒoㄒ)/~~";
    }


    /**
     * 《 重试策略 》
     * 针对controller来进行的retry fallback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Retry",method = RequestMethod.GET)
    @Retry(name = "resilience4j-service",fallbackMethod = "retryFallback")
    public String retry(){
        return "success";
    }

    public String retryFallback(Throwable throwable) {            //注意值只携带一个参数即可，参数为Throwable
        return "重试失败";
    }






}