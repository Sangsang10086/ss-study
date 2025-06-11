package com.aop.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    /**
     * 启用AOP
     */


    /**
     * aop应用场景
     * 1.日志记录  --使用  @Before等
     * 2.事务管理  --使用  @Transactional
     * 3.性能监控  --使用  @Around 测试耗时
     * 4.安全检查  --在方法执行前检查参数或者管理员权限
     * 5.缓存管理
     */
}
