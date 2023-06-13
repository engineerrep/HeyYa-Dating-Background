package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.StaffPersistDto;
import com.heyya.model.entity.Staff;
import com.heyya.model.vo.StaffVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StaffConverter {

    Staff dto2Entity(StaffPersistDto dto);

    List<Staff> dto2Entity(List<StaffPersistDto> dto);

    StaffVo entity2Vo(Staff media);

    PageInfo<StaffVo> pageEntity2PageVo(PageInfo<Staff> pageInfo);
}
