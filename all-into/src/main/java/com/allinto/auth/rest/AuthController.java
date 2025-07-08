package com.allinto.auth.rest;

import com.auth.openfeign.AuthClient;
import com.common.auth.AuthenticationRequest;
import com.common.auth.AuthenticationResponse;
import com.common.filter.IgnoreToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/allInto")
public class AuthController {

    @Autowired
    private AuthClient authClient;

    /**
     * 生成token
     */
    @IgnoreToken
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String AuthTest(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse token = authClient.login(authenticationRequest);
        return token.getToken();
    }
}
