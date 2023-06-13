package com.heyya.model.converter;

import com.heyya.model.dto.UnLikePersistDto;
import com.heyya.model.entity.UnLike;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UnLikeConverter {
    UnLike dto2Entity(UnLikePersistDto dto);
}
