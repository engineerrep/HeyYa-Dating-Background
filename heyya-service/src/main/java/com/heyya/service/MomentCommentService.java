package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heyya.mapper.MomentCommentMapper;
import com.heyya.model.converter.MomentCommentConverter;
import com.heyya.model.dto.MomentCommentPersistDto;
import com.heyya.model.dto.MomentCommentSearchDto;
import com.heyya.model.dto.MomentCommentUpdateDto;
import com.heyya.model.entity.MomentComment;
import com.heyya.model.enums.Deleted;
import com.heyya.model.vo.MomentCommentVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class MomentCommentService extends ServiceImpl<MomentCommentMapper, MomentComment> {

    @Resource
    private MomentCommentConverter commentConverter;
    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public MomentComment save(MomentCommentPersistDto persistDto) {
        MomentComment comment = commentConverter.dto2Entity(persistDto);
        if (save(comment)) {
            return getById(comment.getId());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public MomentComment update(MomentCommentUpdateDto updateDto) {
        MomentComment comment = commentConverter.dto2Entity(updateDto);
        if (updateById(comment)) {
            return getById(comment.getId());
        }
        return null;
    }

    public MomentComment getOne(Long id) {
        MomentComment comment = getById(id);
        if (Objects.nonNull(comment)) {
            return comment;
        }
        return null;
    }

    public PageInfo<MomentCommentVo> search(MomentCommentSearchDto searchDto) {
        LambdaQueryWrapper<MomentComment> queryWrapper = new LambdaQueryWrapper<MomentComment>()
                .eq(Objects.nonNull(searchDto.getMomentId()), MomentComment::getMomentId, searchDto.getMomentId())
                .eq(MomentComment::getDeleted, Deleted.NO)
                .orderByDesc(MomentComment::getCreateTime);
        PageHelper.startPage(searchDto.getNumber(), searchDto.getSize());
        PageInfo<MomentCommentVo> pageVo = commentConverter.pageEntity2PageVo(new PageInfo<>(list(queryWrapper)));
        if (CollectionUtils.isNotEmpty(pageVo.getList())) {
            pageVo.getList().forEach(i -> {
                i.setUser(userService.getUserVo(i.getUserId()));
            });
        }
        return pageVo;
    }

    public Long countByMomentId(Long momentId) {
        LambdaQueryWrapper<MomentComment> queryWrapper = new QueryWrapper<MomentComment>().lambda()
                .eq(Objects.nonNull(momentId), MomentComment::getMomentId, momentId);
        return count(queryWrapper);
    }

}
