package com.common.biz.around;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public interface IBaseBiz<T> extends BaseMapper<T> {

}
