package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.mapper.MomentThumbUpMapper;
import com.heyya.model.converter.MomentThumbUpConverter;
import com.heyya.model.dto.MomentThumbUpPersistDto;
import com.heyya.model.entity.MomentThumbUp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class MomentThumbUpService extends ServiceImpl<MomentThumbUpMapper, MomentThumbUp> {

    @Resource
    private MomentThumbUpConverter thumbUpConverter;

    @Transactional(rollbackFor = Exception.class)
    public MomentThumbUp save(MomentThumbUpPersistDto dto) {
        LambdaQueryWrapper<MomentThumbUp> queryWrapper = new LambdaQueryWrapper<MomentThumbUp>()
                .eq(MomentThumbUp::getUserId, dto.getUserId())
                .eq(MomentThumbUp::getMomentId, dto.getMomentId());
        MomentThumbUp thumbUp = getOne(queryWrapper);
        MomentThumbUp momentThumbUp = thumbUpConverter.dto2Entity(dto);
        if (Objects.isNull(thumbUp) && save(momentThumbUp)) {
            return getById(momentThumbUp.getId());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void del(Long momentId) {
        LambdaQueryWrapper<MomentThumbUp> queryWrapper = new QueryWrapper<MomentThumbUp>().lambda()
                .eq(MomentThumbUp::getUserId, UserAuthContext.getId())
                .eq(MomentThumbUp::getMomentId, momentId);
        MomentThumbUp momentThumbUp = getOne(queryWrapper);
        if (Objects.nonNull(momentThumbUp)) {
            removeById(momentThumbUp);
        }
    }

    public Long countByMomentId(Long momentId) {
        LambdaQueryWrapper<MomentThumbUp> queryWrapper = new QueryWrapper<MomentThumbUp>().lambda()
                .eq(MomentThumbUp::getMomentId, momentId);
        return count(queryWrapper);
    }

    public Boolean isThumb(Long momentId) {
        LambdaQueryWrapper<MomentThumbUp> queryWrapper = new LambdaQueryWrapper<MomentThumbUp>()
                .eq(MomentThumbUp::getUserId, UserAuthContext.getId())
                .eq(MomentThumbUp::getMomentId, momentId);
        if (count(queryWrapper) > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
