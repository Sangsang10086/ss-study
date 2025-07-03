package com.globalexcetion.config;


import com.globalexcetion.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${my.interceptor.enable}")
    private boolean interceptorEnable;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器对象
        if(interceptorEnable){
            registry.addInterceptor(new LoginInterceptor())
                    .addPathPatterns("/**")//设置拦截器拦截的请求路径（ /** 表⽰拦截所有请求
                    .excludePathPatterns(
                            "/jwt/token"            //排除登录接口
//                        "/xxl-job-admin/**", // 排除 XXL-Job 调度中心接口       xxl-job默认不会被拦截
//                        "/xxl-job/**"       // 排除 XXL-Job 执行器接口
                    )
                    .order(1);                  //设置拦截器的优先级，数字越小优先级越高
        }

    }
}