package com.heyya.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heyya.model.dto.MediaSearchDto;
import com.heyya.model.entity.Media;

import java.util.List;

public interface MediaMapper extends BaseMapper<Media> {

    List<Media> list(MediaSearchDto dto);

}
