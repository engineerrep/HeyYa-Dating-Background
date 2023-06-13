package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.MediaPersistDto;
import com.heyya.model.dto.MediaPrivacyDto;
import com.heyya.model.dto.MediaUpdateDto;
import com.heyya.model.entity.Media;
import com.heyya.model.vo.MediaVo;
import com.heyya.model.vo.SparkVideoVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MediaConverter {

    Media dto2Entity(MediaPersistDto dto);

    Media dto2Entity(MediaPrivacyDto dto);

    Media dto2Entity(MediaUpdateDto dto);

    List<Media> dto2Entity(List<MediaPersistDto> dto);

    MediaVo entity2Vo(Media media);

    List<MediaVo> entity2Vo(List<Media> media);

    PageInfo<MediaVo> pageEntity2PageVo(PageInfo<Media> pageInfo);

    PageInfo<SparkVideoVo> pageEntity2PageVideoVo(PageInfo<Media> pageInfo);
}
