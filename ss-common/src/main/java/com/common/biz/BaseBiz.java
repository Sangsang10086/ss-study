package com.common.biz;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseBiz<M extends Mapper<T>, T> {

    protected M mapper;

}
