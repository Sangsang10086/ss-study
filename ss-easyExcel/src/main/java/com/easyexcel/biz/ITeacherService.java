package com.easyexcel.biz;

import com.easyexcel.entity.StopPoint;
import com.easyexcel.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sang
 * @since 2025-06-18
 */
@Service
public interface ITeacherService extends IService<Teacher> {

    void insertBeach(StopPoint stopPoint);
}
