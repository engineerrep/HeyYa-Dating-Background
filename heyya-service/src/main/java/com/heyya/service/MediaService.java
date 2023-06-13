package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.heyya.code.CodeResponse;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.exception.SvcException;
import com.heyya.mapper.MediaMapper;
import com.heyya.model.converter.MediaConverter;
import com.heyya.model.dto.*;
import com.heyya.model.entity.Friend;
import com.heyya.model.entity.Media;
import com.heyya.model.entity.User;
import com.heyya.model.enums.*;
import com.heyya.model.vo.MediaVo;
import com.heyya.model.vo.SparkVideoVo;
import liquibase.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class MediaService extends ServiceImpl<MediaMapper, Media> {

    @Resource
    private MediaConverter mediaConverter;
    @Autowired
    private UserService userService;
    @Autowired
    private BlockService blockService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private MediaMapper mediaMapper;

    @Transactional(rollbackFor = Exception.class)
    public MediaVo save(MediaPersistDto dto) {
        Media media = mediaConverter.dto2Entity(dto);
        if (save(media)) {
            User user = userService.getById(dto.getResourceId());
            if (Objects.nonNull(user)) {
                user.setVerifyVideoState(VerifyState.UNCHECKED);
                userService.updateById(user);
            }
            return mediaConverter.entity2Vo(getById(media.getId()));
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<MediaPersistDto> dtos) {
        List<Media> media = mediaConverter.dto2Entity(dtos);
        if (CollectionUtils.isNotEmpty(media)) {
            List<User> users = new ArrayList<>(dtos.size());
            media.forEach(item -> {
                User user = userService.getById(item.getResourceId());
                if (Objects.nonNull(user)) {
                    user.setVerifyVideoState(VerifyState.UNCHECKED);
                    users.add(user);
                }
            });
            userService.updateBatchById(users);
            saveBatch(media, dtos.size());
        }
    }

    public List<MediaVo> listVo(Long resourceId) {
        LambdaQueryWrapper<Media> query = new QueryWrapper<Media>().lambda();
        query.eq(Media::getResourceId, resourceId);
        return mediaConverter.entity2Vo(list(query));
    }

    public PageInfo<MediaVo> findByResourceId(MediaSearchDto searchDto) {
        LambdaQueryWrapper<Media> query = new QueryWrapper<Media>().lambda()
                .eq(Media::getResourceId, searchDto.getResourceId())
                .eq(Media::getDeleted, Deleted.NO)
                .orderByDesc(Media::getCreateTime);
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        return mediaConverter.pageEntity2PageVo(new PageInfo<>(list(query)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void audit(List<MediaUpdateDto> dto) {
        List<Media> medias = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(dto)) {
            dto.stream().forEach(audit -> {
                Media media = mediaConverter.dto2Entity(audit);
                if (audit.getVerifyState() == VerifyState.CHECKED && media.getType() == MediaType.MAIN_VIDEO) {
                    User user = userService.getById(media.getResourceId());
                    user.setMainVideo(media.getUrl());
                    userService.updateById(user);
                }
                medias.add(media);
                if (audit.getVerifyState() == VerifyState.REFUSE) {
                    removeById(media);
                    medias.remove(media);
                }
            });
            updateBatchById(medias);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public MediaVo update(MediaUpdateDto updateDto) {
        Media media = mediaConverter.dto2Entity(updateDto);
        if (updateById(media)) {
            Media mediaOne = getById(media.getId());
            User user = userService.getById(mediaOne.getResourceId());
            if (Objects.nonNull(user) && StringUtils.isNotEmpty(updateDto.getUrl())) {
                user.setVerifyVideoState(VerifyState.UNCHECKED);
                userService.updateById(user);
            }
            return mediaConverter.entity2Vo(mediaOne);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean putMainVideo(Long id) {
        Media media = getById(id);
        LambdaQueryWrapper<Media> queryWrapper = new LambdaQueryWrapper<Media>()
                .eq(Media::getResourceId, UserAuthContext.getId())
                .eq(Media::getType, MediaType.MAIN_VIDEO);
        Media mediaOne = getOne(queryWrapper);
        if (Objects.nonNull(mediaOne) && mediaOne.getId() != id) {
            mediaOne.setType(MediaType.VERIFY_VIDEO);
            updateById(mediaOne);
        }
        if (Objects.nonNull(media) && media.getVerifyState() == VerifyState.CHECKED) {
            media.setType(MediaType.MAIN_VIDEO);
            updateById(media);
            User user = userService.getById(media.getResourceId());
            user.setMainVideo(media.getUrl());
            userService.updateById(user);
            return Boolean.TRUE;
        }
        throw new SvcException(CodeResponse.SET_MAIN_VIDEO);
    }

    public PageInfo<MediaVo> page(MomentSearchDto searchDto) {
        LambdaQueryWrapper<Media> wrapper = new QueryWrapper<Media>().lambda()
                .eq(Objects.nonNull(searchDto.getVerifyState()), Media::getVerifyState, searchDto.getVerifyState())
                .eq(Media::getDeleted, Deleted.NO)
                .orderByDesc(Media::getCreateTime);
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        return mediaConverter.pageEntity2PageVo(new PageInfo<>(list(wrapper)));

    }

    public List<MediaVo> list(Long userId) {
        LambdaQueryWrapper<Media> query = new QueryWrapper<Media>().lambda();
        User user = userService.getById(userId);
        if (Objects.nonNull(user) && !user.getId().equals(UserAuthContext.getId())) {
            query.eq(Media::getVerifyState, VerifyState.CHECKED);
            query.eq(Media::getPrivacy, MediaPrivacy.PUBLIC);
        }
        query.eq(Media::getResourceId, userId);
        return mediaConverter.entity2Vo(list(query));
    }

    public PageInfo<SparkVideoVo> search(MediaSearchDto searchDto) {
        Long userId = UserAuthContext.getId();
        List<Long> ids = Lists.newArrayList(UserAuthContext.getId());
        Set<Long> blockIds = blockService.blockIds();
        if (CollectionUtils.isNotEmpty(blockIds)) {
            ids.addAll(blockIds);
        }
        searchDto.setPassIds(ids);
        searchDto.setPrivacy(MediaPrivacy.PUBLIC);
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        PageInfo<SparkVideoVo> pageInfo = mediaConverter.pageEntity2PageVideoVo(new PageInfo<>(mediaMapper.list(searchDto)));
        pageInfo.getList().forEach(item -> {
            item.setUser(userService.getUserVo(item.getResourceId()));
            LambdaQueryWrapper<Friend> myQuery = new LambdaQueryWrapper<Friend>()
                    .eq(Friend::getFromUserId, userId)
                    .eq(Friend::getToUserId, item.getResourceId())
                    .in(Friend::getRelation, FriendRelationType.IN_MY_FRIEND_LIST, FriendRelationType.BOTH_WAY);
            item.getUser().setLiked(friendService.count(myQuery) > 0);
        });
        return pageInfo;
    }

    @Transactional
    public MediaVo privacy(MediaPrivacyDto dto) {
        Media media = getById(dto.getId());
        if (Objects.nonNull(media) && media.getType() == MediaType.MAIN_VIDEO) {
            throw new SvcException(CodeResponse.VIDEO_NOT_PRIVATE);
        }
        media.setPrivacy(dto.getPrivacy());
        if (updateById(media)) {
            return mediaConverter.entity2Vo(media);
        }
        return null;
    }

}


