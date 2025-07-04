//package com.globalexcetion.filter;
//
//import com.aop.handle.filter.AuthFilter;
//import com.auth.openfeign.AuthClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//@Configuration
//public class FilterConfig {
//
//    @Autowired
//    private AuthClient authClient;
//
//    @Autowired
//    private RequestMappingHandlerMapping handlerMapping;
//
//    /**
//     * 注册校验过滤器
//     *
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean<AuthFilter> registerLogFilter() {
//        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<AuthFilter>();
//        registrationBean.setFilter(new AuthFilter(authClient, handlerMapping));
//        registrationBean.addUrlPatterns("/*"); // 指定过滤路径,可以指定多个路径
//        registrationBean.setOrder(1); // 设置执行顺序，值越小优先级越高
//        return registrationBean;
//    }
//}
