package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.mapper.VisitorMapper;
import com.heyya.model.converter.VisitorConverter;
import com.heyya.model.dto.VisitorPersistDto;
import com.heyya.model.dto.VisitorSearchDto;
import com.heyya.model.entity.Friend;
import com.heyya.model.entity.Visitor;
import com.heyya.model.enums.Deleted;
import com.heyya.model.enums.FriendRelationType;
import com.heyya.model.vo.RelNumberVO;
import com.heyya.model.vo.VisitorVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class VisitorService extends ServiceImpl<VisitorMapper, Visitor> {

    @Resource
    private VisitorConverter visitorConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Transactional(rollbackFor = Exception.class)
    public void save(VisitorPersistDto dto) {
        LambdaQueryWrapper<Visitor> queryWrapper = new LambdaQueryWrapper<Visitor>()
                .eq(Visitor::getFromUserId, dto.getFromUserId())
                .eq(Visitor::getToUserId, dto.getToUserId());
        Visitor visitor = getOne(queryWrapper);
        if (Objects.nonNull(visitor)) {
            visitor.setVisitTime(LocalDateTime.now());
            updateById(visitor);
        } else {
            save(visitorConverter.dto2Entity(dto));
        }
    }

    public PageInfo<VisitorVo> search(VisitorSearchDto searchDto) {
        LambdaQueryWrapper<Visitor> queryWrapper = new LambdaQueryWrapper<Visitor>()
                .eq(Objects.nonNull(searchDto.getFromUserId()), Visitor::getFromUserId, searchDto.getFromUserId())
                .eq(Objects.nonNull(searchDto.getToUserId()), Visitor::getToUserId, searchDto.getToUserId())
                .eq(Visitor::getDeleted, Deleted.NO)
                .orderByDesc(Visitor::getCreateTime);
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        PageInfo<VisitorVo> pageVo = visitorConverter.pageEntity2PageVo(new PageInfo<>(list(queryWrapper)));
        if (CollectionUtils.isNotEmpty(pageVo.getList())) {
            pageVo.getList().forEach(i -> {
                i.setToUser(userService.getUserVo(i.getToUser().getId()));
            });
        }
        return pageVo;
    }

    public RelNumberVO rel() {
        Long userId = UserAuthContext.getId();
        RelNumberVO relNumber = new RelNumberVO();
        LambdaQueryWrapper<Visitor> visitorQuery = new LambdaQueryWrapper<Visitor>()
                .eq(Visitor::getToUserId, userId);
        relNumber.setVisitorsNum(count(visitorQuery));
        LambdaQueryWrapper<Friend> otherQuery = new LambdaQueryWrapper<Friend>()
                .eq(Friend::getFromUserId, userId)
                .eq(Friend::getRelation, FriendRelationType.IN_OTHER_FRIEND_LIST);
        relNumber.setLikeMeNum(friendService.count(otherQuery));
        LambdaQueryWrapper<Friend> myQuery = new LambdaQueryWrapper<Friend>()
                .eq(Friend::getFromUserId, userId)
                .eq(Friend::getRelation, FriendRelationType.IN_MY_FRIEND_LIST);
        relNumber.setMyLikeNum(friendService.count(myQuery));
        LambdaQueryWrapper<Friend> matchQuery = new LambdaQueryWrapper<Friend>()
                .eq(Friend::getFromUserId, userId)
                .eq(Friend::getRelation, FriendRelationType.BOTH_WAY);
        relNumber.setMatchNum(friendService.count(matchQuery));
        return relNumber;
    }


}
