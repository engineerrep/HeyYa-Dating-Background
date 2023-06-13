package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.BlockPersistDto;
import com.heyya.model.entity.Block;
import com.heyya.model.vo.BlockVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlockConverter {

    Block dto2Entity(BlockPersistDto persistDTO);

    @Mappings({@Mapping(source = "toUserId", target = "toUser.id"),
    })
    BlockVo entity2Vo(Block user);

    PageInfo<BlockVo> pageEntity2PageVo(PageInfo<Block> pageInfo);

}
