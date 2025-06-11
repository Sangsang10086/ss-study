package com.redis.ssredis.biz.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redis.ssredis.biz.ITeacherService;
import com.redis.ssredis.biz.RedisService;
import com.redis.ssredis.mapper.TeacherMapper;
import com.redis.ssredis.po.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String getTeacher(String idAddress) {
        Teacher teacher = teacherMapper.selectById(idAddress);
        logger.info("==== 开始向缓存中插入数据 ====");
        redisService.set("teacherAddress",teacher.getAddress());

        return teacher.getAddress();
    }
}
