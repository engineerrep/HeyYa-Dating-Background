package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.FeedbackPersistDto;
import com.heyya.model.dto.FeedbackUpdateDto;
import com.heyya.model.entity.Feedback;
import com.heyya.model.vo.FeedbackVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FeedbackConverter {

    Feedback dto2Entity(FeedbackPersistDto feedbackPersistDTO);

    List<Feedback> dto2Entity(List<FeedbackUpdateDto> feedbackPersistDTO);

    FeedbackVo entity2Vo(Feedback feedback);

    PageInfo<FeedbackVo> pageEntity2PageVo(PageInfo<Feedback> pageInfo);
}
