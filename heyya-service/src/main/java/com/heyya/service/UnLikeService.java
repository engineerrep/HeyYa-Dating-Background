package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.mapper.UnlikeMapper;
import com.heyya.model.converter.UnLikeConverter;
import com.heyya.model.dto.UnLikePersistDto;
import com.heyya.model.entity.UnLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UnLikeService extends ServiceImpl<UnlikeMapper, UnLike> {

    @Autowired
    private UnLikeConverter unLikeConverter;

    @Transactional(rollbackFor = Exception.class)
    public void save(UnLikePersistDto dto) {
        UnLike unLike = unLikeConverter.dto2Entity(dto);
        unLike.setFromUserId(UserAuthContext.getId());
        save(unLike);
    }

    public Set<Long> unLikes() {
        LambdaQueryWrapper<UnLike> queryWrapper = new LambdaQueryWrapper<UnLike>()
                .eq(UnLike::getFromUserId, UserAuthContext.getId());
        return list(queryWrapper).stream().map(UnLike::getToUserId).collect(Collectors.toSet());
    }
}
