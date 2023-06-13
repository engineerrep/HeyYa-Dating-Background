package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.ReportPersistDto;
import com.heyya.model.dto.ReportUpdateDto;
import com.heyya.model.entity.Report;
import com.heyya.model.vo.ReportVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportConverter {
    Report dto2Entity(ReportPersistDto persistDTO);

    Report dto2Entity(ReportUpdateDto persistDTO);

    List<Report> dto2Entity(List<ReportUpdateDto> persistDTO);

    ReportVo entity2Vo(Report user);

    PageInfo<ReportVo> pageEntity2PageVo(PageInfo<Report> pageInfo);
}
