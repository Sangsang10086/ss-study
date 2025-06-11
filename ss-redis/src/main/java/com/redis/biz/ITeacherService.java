package com.redis.biz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redis.po.Teacher;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sang
 * @since 2025-06-06
 */
@Service
public interface ITeacherService extends IService<Teacher> {

    void getTeacher(String idAddress);
}
