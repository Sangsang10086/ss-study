package com.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.redis.Redis;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sang
 * @since 2025-06-06
 */
@Mapper
public interface RedisMapper extends BaseMapper<Redis> {

}
