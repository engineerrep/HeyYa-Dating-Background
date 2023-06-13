package com.heyya.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heyya.model.entity.User;
import io.lettuce.core.dynamic.annotation.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<Long> selectByGeo(@Param("lon") BigDecimal lon, @Param("lat") BigDecimal lat, @Param("distance") Long distance);

}
