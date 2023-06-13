package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.mapper.FriendMapper;
import com.heyya.model.converter.FriendConverter;
import com.heyya.model.dto.FriendPersistDto;
import com.heyya.model.dto.FriendSearchDto;
import com.heyya.model.entity.Friend;
import com.heyya.model.enums.FriendRelationType;
import com.heyya.model.vo.FriendVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FriendService extends ServiceImpl<FriendMapper, Friend> {

    @Resource
    private FriendConverter friendConverter;
    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public FriendVo save(FriendPersistDto dto) {
        LambdaQueryWrapper<Friend> queryFrom = new QueryWrapper<Friend>().lambda()
                .eq(Friend::getFromUserId, dto.getFromUserId())
                .eq(Friend::getToUserId, dto.getToUserId());
        Friend myLike = getOne(queryFrom);
        LambdaQueryWrapper<Friend> queryTo = new QueryWrapper<Friend>().lambda()
                .eq(Friend::getFromUserId, dto.getToUserId())
                .eq(Friend::getToUserId, dto.getFromUserId());
        Friend likeMe = getOne(queryTo);
        if (Objects.nonNull(myLike) && Objects.nonNull(likeMe)) {
            boolean isLike = FriendRelationType.IN_MY_FRIEND_LIST == myLike.getRelation();
            boolean isMatch = FriendRelationType.BOTH_WAY == myLike.getRelation();
            if (isLike || isMatch) {
                return getFriendVo(myLike.getId());
            }
            if (FriendRelationType.IN_MY_FRIEND_LIST == likeMe.getRelation()) {
                myLike.setRelation(FriendRelationType.BOTH_WAY);
                likeMe.setRelation(FriendRelationType.BOTH_WAY);
            } else {
                myLike.setRelation(FriendRelationType.IN_MY_FRIEND_LIST);
                likeMe.setRelation(FriendRelationType.IN_OTHER_FRIEND_LIST);
            }
            updateBatchById(Lists.newArrayList(myLike, likeMe));
            return getFriendVo(myLike.getId());
        }
        Friend myLikeSave = friendConverter.dto2Entity(dto);
        myLikeSave.setRelation(FriendRelationType.IN_MY_FRIEND_LIST);
        Friend likeMeSave = friendConverter.persist2Entity(dto);
        likeMeSave.setRelation(FriendRelationType.IN_OTHER_FRIEND_LIST);
        saveBatch(Lists.newArrayList(myLikeSave, likeMeSave));
        return getFriendVo(myLikeSave.getId());
    }

    public PageInfo<FriendVo> search(FriendSearchDto searchDto) {
        LambdaQueryWrapper<Friend> queryWrapper = new QueryWrapper<Friend>().lambda()
                .eq(Objects.nonNull(searchDto.getFromUserId()), Friend::getFromUserId, searchDto.getFromUserId())
                .eq(Objects.nonNull(searchDto.getToUserId()), Friend::getToUserId, searchDto.getToUserId())
                .in(CollectionUtils.isNotEmpty(searchDto.getRelationOR()), Friend::getRelation, searchDto.getRelationOR())
                .notIn(CollectionUtils.isNotEmpty(searchDto.getBlockIds()), Friend::getToUserId, searchDto.getBlockIds());
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        PageInfo<FriendVo> pageInfo = friendConverter.pageEntity2PageVo(new PageInfo<>(list(queryWrapper)));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            pageInfo.getList().forEach(item -> {
                item.setToUser(userService.getUserVo(item.getToUser().getId()));
            });
        }
        return pageInfo;
    }

    public FriendVo getFriendVo(Long id) {
        FriendVo friend = friendConverter.entity2VO(getById(id));
        if (Objects.nonNull(friend)) {
            friend.setToUser(userService.getUserVo(friend.getToUser().getId()));
        }
        return friend;
    }

    public Set<Long> friends() {
        LambdaQueryWrapper<Friend> queryWrapper = new LambdaQueryWrapper<Friend>()
                .eq(Friend::getFromUserId, UserAuthContext.getId())
                .in(Friend::getRelation, Lists.newArrayList(FriendRelationType.IN_MY_FRIEND_LIST, FriendRelationType.BOTH_WAY));
        return list(queryWrapper).stream().map(Friend::getToUserId).collect(Collectors.toSet());
    }
}


