package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.heyya.mapper.MomentMapper;
import com.heyya.model.converter.MomentConverter;
import com.heyya.model.dto.MomentPersistDto;
import com.heyya.model.dto.MomentSearchDto;
import com.heyya.model.dto.MomentUpdateDto;
import com.heyya.model.entity.Moment;
import com.heyya.model.entity.User;
import com.heyya.model.enums.Active;
import com.heyya.model.enums.Deleted;
import com.heyya.model.enums.VerifyState;
import com.heyya.model.vo.MomentVo;
import liquibase.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MomentService extends ServiceImpl<MomentMapper, Moment> {

    @Resource
    private MomentConverter momentConverter;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private UserService userService;
    @Autowired
    private MomentCommentService commentService;
    @Autowired
    private MomentThumbUpService thumbUpService;
    @Autowired
    private BlockService blockService;

    @Transactional(rollbackFor = Exception.class)
    public Moment save(MomentPersistDto persistDto) {
        Moment moment = momentConverter.dto2Entity(persistDto);
        if (save(moment)) {
            if (CollectionUtils.isNotEmpty(persistDto.getMedias())) {
                persistDto.getMedias().forEach(i -> {
                    i.setResourceId(moment.getId());
                });
                mediaService.saveAll(persistDto.getMedias());
            }
            return getById(moment.getId());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Moment update(MomentUpdateDto updateDto) {
        Moment moment = momentConverter.dto2Entity(updateDto);
        if (updateById(moment)) {
            return getById(moment.getId());
        }
        return null;
    }

    public Moment getOne(Long id) {
        Moment moment = getById(id);
        if (Objects.nonNull(moment)) {
            return moment;
        }
        return null;
    }

    public PageInfo<MomentVo> search(MomentSearchDto searchDto) {
        Set<Long> blockIds = blockService.blockIds();
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<User>()
                .notIn(User::getActive, Active.ACTIVE);
        Set<Long> userIds = userService.list(userWrapper).stream().map(User::getId).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(userIds)) {
            blockIds.addAll(userIds);
        }
        LambdaQueryWrapper<Moment> wrapper = new QueryWrapper<Moment>().lambda()
                .eq(StringUtils.isNotEmpty(searchDto.getUserId()), Moment::getUserId, searchDto.getUserId())
                .notIn(Moment::getVerifyState, VerifyState.REFUSE)
                .notIn(CollectionUtils.isNotEmpty(blockIds), Moment::getUserId, blockIds)
                .eq(Moment::getDeleted, Deleted.NO)
                .orderByDesc(Moment::getCreateTime);
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        PageInfo<MomentVo> pageVo = momentConverter.pageEntity2PageVo(new PageInfo<>(list(wrapper)));
        if (CollectionUtils.isNotEmpty(pageVo.getList())) {
            pageVo.getList().forEach(i -> {
                i.setMedias(mediaService.listVo(Long.valueOf(i.getId())));
                i.setUser(userService.getUserVo(i.getUserId()));
                i.setCommentCount(commentService.countByMomentId(Long.valueOf(i.getId())));
                i.setThumbCount(thumbUpService.countByMomentId(Long.valueOf(i.getId())));
                i.setIsThumb(thumbUpService.isThumb(Long.valueOf(i.getId())));
            });
        }
        return pageVo;
    }

    public PageInfo<MomentVo> page(MomentSearchDto searchDto) {
        LambdaQueryWrapper<Moment> wrapper = new QueryWrapper<Moment>().lambda()
                .eq(Objects.nonNull(searchDto.getUserId()), Moment::getUserId, searchDto.getUserId())
                .eq(Objects.nonNull(searchDto.getVerifyState()), Moment::getVerifyState, searchDto.getVerifyState())
                .eq(Moment::getDeleted, Deleted.NO)
                .orderByDesc(Moment::getCreateTime);
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        PageInfo<MomentVo> pageVo = momentConverter.pageEntity2PageVo(new PageInfo<>(list(wrapper)));
        if (CollectionUtils.isNotEmpty(pageVo.getList())) {
            pageVo.getList().forEach(i -> {
                i.setMedias(mediaService.listVo(Long.valueOf(i.getId())));
                i.setUser(userService.getUserVo(i.getUserId()));
            });
        }
        return pageVo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void audit(List<MomentUpdateDto> dto) {
        List<Moment> moments = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(dto)) {
            dto.stream().forEach(audit -> {
                Moment moment = momentConverter.dto2Entity(audit);
                moments.add(moment);
                if (audit.getVerifyState() == VerifyState.REFUSE) {
                    removeById(moment);
                    moments.remove(moment);
                }
            });
            updateBatchById(moments);
        }
    }

}
