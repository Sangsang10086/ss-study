package com.redis.controller;

import com.redis.biz.ITeacherService;
import com.redis.biz.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sang
 * @since 2025-06-06
 */
@RestController
@RequestMapping("/redis")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private ITeacherService teacherService;


    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    public void getTeacher() {
        String idAddress = "5";
        teacherService.getTeacher(idAddress);
    }
}
