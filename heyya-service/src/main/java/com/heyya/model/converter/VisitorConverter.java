package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.VisitorPersistDto;
import com.heyya.model.entity.Visitor;
import com.heyya.model.vo.VisitorVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VisitorConverter {
    Visitor dto2Entity(VisitorPersistDto persistDTO);

    @Mappings({
            @Mapping(source = "fromUserId", target = "toUser.id")
    })
    VisitorVo entity2Vo(Visitor user);


    PageInfo<VisitorVo> pageEntity2PageVo(PageInfo<Visitor> pageInfo);
}
