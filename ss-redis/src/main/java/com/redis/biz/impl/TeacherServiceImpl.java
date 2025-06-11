package com.redis.biz.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redis.biz.ITeacherService;
import com.redis.biz.RedisService;
import com.redis.mapper.TeacherMapper;
import com.redis.po.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sang
 * @since 2025-06-06
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private RedisService redisService;

    @Override
//    @Cacheable(value = "teacherAddress",key = "#idAddress")
    public void getTeacher(String idAddress) {
        Teacher teacher = teacherMapper.selectById(idAddress);
        logger.info("==== 开始向缓存中插入数据 ====");
        redisService.set("teacherAddress",teacher,30, TimeUnit.MINUTES);
        logger.info("==== 缓存数据插入成功 ====");
        if(redisService.hasKey("teacherAddress")){
            logger.info("==== 缓存数据为 {} ====", redisService.get("teacherAddress"));
//            redisService.delete("teacherAddress");
        }
    }
}
