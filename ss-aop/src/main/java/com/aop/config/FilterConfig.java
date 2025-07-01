package com.aop.config;

import com.aop.handle.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * 注册日志过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> registerLogFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<AuthFilter>();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/*"); // 指定过滤路径,可以指定多个路径
        registrationBean.setOrder(1); // 设置执行顺序，值越小优先级越高
        return registrationBean;
    }
}
