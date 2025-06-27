package com.auth.rest;

import com.common.auth.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    /**
     * 登录
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//
//    }
    @RequestMapping(value = "/login")
    public String createAuthenticationToken() {
        return "login";
    }


    /**
     * 验证token
     * @param token
     * @return
     */
    @RequestMapping(value = "/validate",method = RequestMethod.GET)
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
