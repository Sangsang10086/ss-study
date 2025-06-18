package com.easyexcel.biz.impl;

import com.easyexcel.entity.StopPoint;
import com.easyexcel.entity.Teacher;
import com.easyexcel.mapper.TeacherMapper;
import com.easyexcel.biz.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sang
 * @since 2025-06-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    private static final Logger logger =  LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Override
    public void insertBeach(StopPoint stopPoint) {
        logger.info("本次导入的数据为{}", stopPoint);
    }
}
