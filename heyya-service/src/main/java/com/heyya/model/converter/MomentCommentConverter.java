package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.MomentCommentPersistDto;
import com.heyya.model.dto.MomentCommentUpdateDto;
import com.heyya.model.entity.MomentComment;
import com.heyya.model.vo.MomentCommentVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MomentCommentConverter {

    MomentComment dto2Entity(MomentCommentPersistDto persistDTO);

    @Mappings({
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "momentId", target = "moment.id")
    })
    MomentCommentVo entity2Vo(MomentComment user);

    MomentComment dto2Entity(MomentCommentUpdateDto updateDto);

    PageInfo<MomentCommentVo> pageEntity2PageVo(PageInfo<MomentComment> pageInfo);
}
