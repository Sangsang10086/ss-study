package com.allinto.auth.rest;

import cn.hutool.http.HttpUtil;
import com.auth.openfeign.AuthClient;
import com.common.auth.AuthenticationRequest;
import com.common.auth.AuthenticationResponse;
import com.common.filter.IgnoreToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10599
 */
@RestController
@RequestMapping("/allInto")
public class AuthController {

    @Autowired
    private AuthClient authClient;

    /**
     * 生成token
     */
    @IgnoreToken
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authTest(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse token = authClient.login(authenticationRequest);
        return token.getToken();
    }


    @RequestMapping(value = "/getCorn", method = RequestMethod.GET)
    public void getCorn() {
        for (int num = 1; num <= 1; num++) {
            String url = "https://www.cnhnb.com/p/yumili-0-0-0-0-" + num + "/";
            String s = HttpUtil.get(url);
            Document parse = Jsoup.parse(s);
            Element body = parse.body();
            System.out.println(body);
        }

    }
}
