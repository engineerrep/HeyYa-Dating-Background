package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.FriendPersistDto;
import com.heyya.model.entity.Friend;
import com.heyya.model.vo.FriendVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FriendConverter {
    Friend dto2Entity(FriendPersistDto friendPersistDTO);

    @Mappings({@Mapping(source = "toUserId", target = "toUser.id")
    })
    FriendVo entity2VO(Friend friend);

    @Mappings({@Mapping(source = "fromUserId", target = "toUserId"),
            @Mapping(source = "toUserId", target = "fromUserId")})
    Friend persist2Entity(FriendPersistDto friendPersistDTO);

    PageInfo<FriendVo> pageEntity2PageVo(PageInfo<Friend> pageInfo);
}
