package com.heyya.model.converter;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.ProfileAuditDto;
import com.heyya.model.dto.SingInDto;
import com.heyya.model.dto.UserPersistDto;
import com.heyya.model.dto.UserUpdateDto;
import com.heyya.model.entity.User;
import com.heyya.model.vo.MyProfileVo;
import com.heyya.model.vo.SparkVo;
import com.heyya.model.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserConverter {

    User dto2Entity(UserPersistDto persistDTO);

    UserVo entity2Vo(User user);

    MyProfileVo entity2myProfileVo(User user);

    User dto2Entity(UserUpdateDto updateDto);

    User dto2Entity(ProfileAuditDto updateDto);

    List<User> dto2Entity(List<ProfileAuditDto> updateDto);

    UserPersistDto dto2dto(SingInDto dto);

    PageInfo<UserVo> pageEntity2PageVo(PageInfo<User> pageInfo);

    PageInfo<SparkVo> pageEntity2sparkVo(PageInfo<User> pageInfo);
}
