package com.aop.openfeign;

import com.common.auth.AuthenticationRequest;

import com.common.auth.TokenVerifyResult;
import com.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service")  //鉴权服务
public interface AuthClient {

    /**
     * 登录,获取token
     * @param authenticationRequest
     * @return
     */
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    Result login(@RequestBody AuthenticationRequest authenticationRequest);

    /**
     * 验证token
     * @param token 从header中提取的token字符串
     * @return
     */
    @RequestMapping(value = "/auth/verify", method = RequestMethod.POST)
    TokenVerifyResult verify(String token);
}
