package com.lock.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LockController{

    @RequestMapping("/lock")
    public String lock() {
        return "lock";
    }

}
