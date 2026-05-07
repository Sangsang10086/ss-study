package com.swagger.ssswagger.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10599
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "helloWorld";
    }

}