package com.http.rest;

import com.common.rest.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class HttpController extends BaseController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public void hello(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            Logger.getLogger("请求体解析失败");
        }
    }
}
