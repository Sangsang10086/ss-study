package com.redis.ssredis.controller;

import com.redis.ssredis.biz.ITeacherService;
import com.redis.ssredis.biz.RedisService;
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
@RequestMapping
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    public String getTeacher() {
        String idAddress = "5";
        logger.info("==== 开始获取Teacher中的address信息 ====");
        String address = teacherService.getTeacher(idAddress);
        logger.info("Redis中键为{}的值为{}","teacherAddress",redisService.get("teacherAddress"));
        return address;
    }
}
