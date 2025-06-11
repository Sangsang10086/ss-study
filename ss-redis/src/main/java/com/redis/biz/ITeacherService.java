package com.redis.ssredis.biz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redis.ssredis.po.Teacher;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sang
 * @since 2025-06-06
 */
public interface ITeacherService extends IService<Teacher> {

    String getTeacher(String idAddress);
}
