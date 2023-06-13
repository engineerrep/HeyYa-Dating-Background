package com.heyya.model.converter;

import com.heyya.model.dto.MomentThumbUpPersistDto;
import com.heyya.model.entity.MomentThumbUp;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MomentThumbUpConverter {

    MomentThumbUp dto2Entity(MomentThumbUpPersistDto dto);
}
