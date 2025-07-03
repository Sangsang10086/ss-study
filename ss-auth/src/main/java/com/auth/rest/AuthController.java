package com.auth.rest;

import com.auth.biz.AuthBiz;
import com.auth.entity.User;
import com.auth.util.JwtUtil;
import com.common.auth.AuthenticationRequest;
import com.common.auth.TokenVerifyResult;
import com.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;


    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //    用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        // 认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 保存认证状态
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        //获取用户信息
        User user = (User) authenticate.getPrincipal();

        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        String token = jwtUtil.generateToken(map);

        return Result.success("登录成功", token);
    }
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public TokenVerifyResult verify(String token) {
        TokenVerifyResult result = new TokenVerifyResult();
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            result.setValid(true);
            result.setUserDetails(userDetails);
            result.setAuthentication(authentication);
            result.setUsername(username);
        }
        return result;
    }
}
