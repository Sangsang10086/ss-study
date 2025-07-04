package com.allinto.auth.filter;

import com.auth.openfeign.AuthClient;
import com.common.auth.TokenVerifyResult;
import com.common.filter.IgnoreToken;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

@Order(1)
@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private AuthClient authClient;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (servletRequest instanceof HttpServletRequest httpServletRequest) {
            try {
                // 获取Spring应用上下文
                WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
                        httpServletRequest.getServletContext());

                // 获取RequestMappingHandlerMapping Bean
                RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);

                // 获取当前请求的HandlerMethod
                HandlerMethod handlerMethod = (HandlerMethod) handlerMapping.getHandler(httpServletRequest).getHandler();

                // 检查方法或类上是否有IgnoreToken注解
                if(handlerMethod.hasMethodAnnotation(IgnoreToken.class) ||
                        handlerMethod.getBeanType().isAnnotationPresent(IgnoreToken.class)){
                    // 存在 IgnoreToken 注解，直接放行
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }

                // Token验证逻辑
                String token = extractTokenFromRequest(httpServletRequest);
                if(token == null){
                    logger.info("请先登录");
                    return;
                }
                TokenVerifyResult verify = authClient.verify(token);
                if (verify != null && verify.isValid()) {
                    filterChain.doFilter(servletRequest, servletResponse); // token 验证通过，放行
                } else {
                    logger.info("token验证失败");
                }
            } catch (Exception e) {
                // 处理异常，例如记录日志并继续执行过滤器链
                logger.error("获取HandlerMethod失败", e);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            // 非HTTP请求，直接放行
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
