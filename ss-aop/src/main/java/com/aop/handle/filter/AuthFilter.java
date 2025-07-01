package com.aop.handle.filter;

import com.alibaba.spring.util.AnnotationUtils;
import com.aop.openfeign.AuthClient;
import com.common.auth.TokenVerifyResult;
import com.common.filter.IgnoreToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Autowired
    private AuthClient authClient;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest httpRequest){
            try {
                HandlerExecutionChain handlerExecutionChain = this.handlerMapping.getHandler(httpRequest);
                HandlerMethod handlerMethod = (HandlerMethod) handlerExecutionChain.getHandler();
                if(AnnotationUtils.findAnnotations(handlerMethod.getMethod(), IgnoreToken.class)!= null ||
                   AnnotationUtils.findAnnotations(handlerMethod.getBeanType().getEnclosingMethod(), IgnoreToken.class)!= null){
                    filterChain.doFilter(servletRequest, servletResponse);   //若添加注释，则直接放行本次过滤
                    return;
                }
            } catch (Exception e) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "网络错误");
                return;
            }
            TokenVerifyResult verify = authClient.verify(httpRequest);
            if (verify != null && verify.isValid()) {
                filterChain.doFilter(servletRequest, servletResponse);   //若验证通过，则直接放行本次过滤
            }else{
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "校验失败");
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);      //如果不是http请求，则直接放行
        }
    }
}
