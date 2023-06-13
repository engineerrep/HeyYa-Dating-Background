package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.MomentPersistDto;
import com.heyya.model.dto.MomentUpdateDto;
import com.heyya.model.entity.Moment;
import com.heyya.model.vo.MomentVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MomentConverter {

    Moment dto2Entity(MomentPersistDto persistDTO);

    MomentVo entity2Vo(Moment user);

    Moment dto2Entity(MomentUpdateDto updateDto);

    PageInfo<MomentVo> pageEntity2PageVo(PageInfo<Moment> pageInfo);
}
