package com.auth.mapper;

import com.auth.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper extends BaseMapper<User> {
}
