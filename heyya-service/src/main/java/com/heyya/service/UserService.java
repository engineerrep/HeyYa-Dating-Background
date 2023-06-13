package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.constans.ImKeyConts;
import com.heyya.mapper.UserMapper;
import com.heyya.model.converter.MediaConverter;
import com.heyya.model.converter.UserConverter;
import com.heyya.model.dto.*;
import com.heyya.model.entity.Media;
import com.heyya.model.entity.User;
import com.heyya.model.enums.Active;
import com.heyya.model.enums.ImSendMsgEnum;
import com.heyya.model.enums.VerifyState;
import com.heyya.model.vo.SparkVo;
import com.heyya.model.vo.UserVo;
import com.heyya.tencent.service.ImService;
import liquibase.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService extends ServiceImpl<UserMapper, User> {
    private final String DEF_AVATAR = "default avatar";

    @Resource
    private UserConverter userConverter;

    @Autowired
    private BlockService blockService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private UnLikeService unLikeService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ImService imService;

    @Autowired
    private MediaConverter mediaConverter;

    @Transactional(rollbackFor = Exception.class)
    public User save(UserPersistDto persistDto) {
        User user = userConverter.dto2Entity(persistDto);
        if (save(user)) {
            return getById(user.getId());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(UserUpdateDto updateDto) {
        User user = userConverter.dto2Entity(updateDto);
        if (updateById(user)) {
            BaseIMSendMsgDto imSendMsgDTO = new BaseIMSendMsgDto();
            if (StringUtils.isNotEmpty(updateDto.getNickname()) || StringUtils.isNotEmpty(updateDto.getAvatar())) {
                imSendMsgDTO.setContent(new IMAccountImportDto(
                        ImKeyConts.IM_KEY + user.getId(),
                        StringUtils.isNotEmpty(updateDto.getNickname()) ? updateDto.getNickname() : null,
                        StringUtils.isNotEmpty(updateDto.getAvatar()) ? updateDto.getAvatar() : null));
                imSendMsgDTO.setImSendMsgEnum(ImSendMsgEnum.ACCOUNT_IMPORT);
                imService.imSendMsg(imSendMsgDTO);
            }
            return getById(user.getId());
        }
        return null;
    }

    public User getOne(Long id) {
        User user = getById(id);
        if (Objects.nonNull(user)) {
            return user;
        }
        return null;
    }

    public UserVo getUserVo(String id) {
        UserVo userVo = userConverter.entity2Vo(getById(id));
        if (Objects.nonNull(userVo)) {
            return userVo;
        }
        return null;
    }

    public PageInfo<SparkVo> spark(SparkSearchDto search) {
        HashSet<Long> filterIds = Sets.newHashSet(UserAuthContext.getId());
        Set<Long> blocks = blockService.blockIds();
        if (CollectionUtils.isNotEmpty(blocks)) {
            filterIds.addAll(blocks);
        }
        Set<Long> unLikes = unLikeService.unLikes();
        if (CollectionUtils.isNotEmpty(unLikes)) {
            filterIds.addAll(unLikes);
        }
        Set<Long> friends = friendService.friends();
        if (CollectionUtils.isNotEmpty(friends)) {
            filterIds.addAll(friends);
        }
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda()
                .notIn(CollectionUtils.isNotEmpty(filterIds), User::getId, filterIds)
                .eq(StringUtils.isNotEmpty(search.getCity()), User::getCity, search.getCity())
                .eq(StringUtils.isNotEmpty(search.getCountry()), User::getCountry, search.getCountry())
                .eq(StringUtils.isNotEmpty(search.getProvince()), User::getProvince, search.getProvince())
                .eq(StringUtils.isNotEmpty(search.getSex()), User::getSex, search.getSex())
                .eq(User::getAvatarState, VerifyState.CHECKED)
                .eq(User::getNicknameState, VerifyState.CHECKED)
                .eq(User::getAboutMeState, VerifyState.CHECKED)
                .eq(User::getActive, Active.ACTIVE)
                .orderByDesc(User::getCreateTime);
        PageHelper.startPage(search.getNumber(), search.getSize());
        PageInfo<SparkVo> pageVo = userConverter.pageEntity2sparkVo(new PageInfo<>(list(wrapper)));
        if (CollectionUtils.isNotEmpty(pageVo.getList())) {
            pageVo.getList().forEach(i -> {
                i.setMedias(mediaService.list(Long.valueOf(i.getId())));
            });
        }
        return pageVo;
    }

    public PageInfo<UserVo> page(UserSearchDto search) {
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda();
        if (Objects.nonNull(search.getState())) {
            wrapper.or((nickNameWrapper) -> {
                nickNameWrapper.isNotNull(User::getNickname);
                nickNameWrapper.ne(User::getNickname, "");
                nickNameWrapper.eq(User::getNicknameState, VerifyState.UNCHECKED);
            });
            wrapper.or((avatarWrapper) -> {
                avatarWrapper.isNotNull(User::getAvatar);
                avatarWrapper.ne(User::getAvatar, "");
                avatarWrapper.eq(User::getAvatarState, VerifyState.UNCHECKED);
            });
            wrapper.or((aboutMeWrapper) -> {
                aboutMeWrapper.isNotNull(User::getAboutMe);
                aboutMeWrapper.ne(User::getAboutMe, "");
                aboutMeWrapper.eq(User::getAboutMeState, VerifyState.UNCHECKED);
            });
            wrapper.or((videoWrapper) -> {
                videoWrapper.eq(User::getVerifyVideoState, VerifyState.UNCHECKED);
            });
        }
        wrapper.eq(Objects.nonNull(search.getUserId()), User::getId, search.getUserId());
        wrapper.eq(StringUtils.isNotEmpty(search.getNickname()), User::getNickname, search.getNickname());
        wrapper.eq(StringUtils.isNotEmpty(search.getSex()), User::getSex, search.getSex());
        wrapper.eq(Objects.nonNull(search.getActive()), User::getActive, search.getActive());
        wrapper.orderByDesc(User::getCreateTime);
        PageHelper.startPage(search.getNumber(), search.getSize());
        PageInfo<UserVo> pageVo = userConverter.pageEntity2PageVo(new PageInfo<>(list(wrapper)));
        if (CollectionUtils.isNotEmpty(pageVo.getList())) {
            pageVo.getList().forEach(i -> {
                LambdaQueryWrapper<Media> queryWrapper = new LambdaQueryWrapper<Media>()
                        .eq(Media::getResourceId, Long.valueOf(i.getId()))
                        .eq(Objects.nonNull(search.getState()), Media::getVerifyState, search.getState());
                i.setMedias(mediaConverter.entity2Vo(mediaService.list(queryWrapper)));
            });
        }
        return pageVo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void audit(List<ProfileAuditDto> auditDto) {
        List<User> users = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(auditDto)) {
            auditDto.stream().forEach(audit -> {
                User user = userConverter.dto2Entity(audit);
                if (audit.getAboutMeState() == VerifyState.REFUSE) {
                    user.setAboutMe("");
                }
                if (audit.getAvatarState() == VerifyState.REFUSE) {
                    BaseIMSendMsgDto imSendMsgDTO = new BaseIMSendMsgDto();
                    imSendMsgDTO.setContent(new IMAccountImportDto(
                            ImKeyConts.IM_KEY + user.getId(), null, DEF_AVATAR));
                    imSendMsgDTO.setImSendMsgEnum(ImSendMsgEnum.ACCOUNT_IMPORT);
                    imService.imSendMsg(imSendMsgDTO);
                    user.setAvatar("");
                }
                if (audit.getNicknameState() == VerifyState.REFUSE) {
                    BaseIMSendMsgDto imSendMsgDTO = new BaseIMSendMsgDto();
                    imSendMsgDTO.setContent(new IMAccountImportDto(
                            ImKeyConts.IM_KEY + user.getId(), "", null));
                    imSendMsgDTO.setImSendMsgEnum(ImSendMsgEnum.ACCOUNT_IMPORT);
                    imService.imSendMsg(imSendMsgDTO);
                    user.setNickname("");
                }
                mediaService.audit(audit.getAuditMedias());
                users.add(user);
            });
            updateBatchById(users);
        }
    }

}
