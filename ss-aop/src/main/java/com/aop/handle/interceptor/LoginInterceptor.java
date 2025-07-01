//package com.aop.handle.interceptor;
//
//import com.aop.openfeign.AuthClient;
//import com.common.auth.TokenVerifyResult;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
//
//    @Autowired
//    private AuthClient authClient;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("LoginInterceptor preHandle(目标方法执行前执行).....");
//        String token = request.getHeader("Authorization");
//        logger.info("拿到token:{}", token);
//        //验证token逻辑
//        final TokenVerifyResult booleanResponseEntity = authClient.verify(request);
//        return booleanResponseEntity.equals(ResponseEntity.ok(true));
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("LoginInterceptor postHandle(目标方案执行后执行).....");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.info("LoginInterceptor afterCompletion(视图渲染完毕后执行,最后执行).....");
//    }
//}