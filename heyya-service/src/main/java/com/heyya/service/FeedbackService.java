package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heyya.mapper.FeedbackMapper;
import com.heyya.model.converter.FeedbackConverter;
import com.heyya.model.dto.FeedbackPersistDto;
import com.heyya.model.dto.FeedbackSearchDto;
import com.heyya.model.dto.FeedbackUpdateDto;
import com.heyya.model.entity.Feedback;
import com.heyya.model.vo.FeedbackVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {

    @Resource
    private FeedbackConverter feedbackConverter;

    @Transactional(rollbackFor = Exception.class)
    public Feedback save(FeedbackPersistDto dto) {
        Feedback feedback = feedbackConverter.dto2Entity(dto);
        if (CollectionUtils.isNotEmpty(dto.getMedias())) {
            feedback.setMedia(String.join(",", dto.getMedias()));
        }
        if (save(feedback)) {
            return getById(feedback.getId());
        }
        return null;
    }

    public Feedback getOne(Long id) {
        Feedback feedback = getById(id);
        if (Objects.nonNull(feedback)) {
            return feedback;
        }
        return null;
    }

    public PageInfo<FeedbackVo> page(FeedbackSearchDto search) {
        LambdaQueryWrapper<Feedback> wrapper = new QueryWrapper<Feedback>().lambda()
                .eq(Objects.nonNull(search.getUserId()), Feedback::getUserId, search.getUserId())
                .eq(Objects.nonNull(search.getState()), Feedback::getState, search.getState())
                .orderByDesc(Feedback::getCreateTime);
        PageHelper.startPage(search.getNumber(), search.getSize());
        return feedbackConverter.pageEntity2PageVo(new PageInfo<>(list(wrapper)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(List<FeedbackUpdateDto> dto) {
        updateBatchById(feedbackConverter.dto2Entity(dto));
    }

}
