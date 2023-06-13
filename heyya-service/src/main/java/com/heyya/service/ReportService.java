package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.mapper.ReportMapper;
import com.heyya.model.converter.ReportConverter;
import com.heyya.model.dto.ReportPersistDto;
import com.heyya.model.dto.ReportSearchDto;
import com.heyya.model.dto.ReportUpdateDto;
import com.heyya.model.entity.Report;
import com.heyya.model.vo.ReportVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class ReportService extends ServiceImpl<ReportMapper, Report> {

    @Resource
    private ReportConverter reportConverter;

    @Transactional(rollbackFor = Exception.class)
    public void save(ReportPersistDto persistDTO) {
        Report report = reportConverter.dto2Entity(persistDTO);
        if (CollectionUtils.isNotEmpty(persistDTO.getMedias())) {
            report.setMedia(String.join(",", persistDTO.getMedias()));
        }
        report.setFromUserId(UserAuthContext.getId());
        save(report);
    }

    public PageInfo<ReportVo> page(ReportSearchDto search) {
        LambdaQueryWrapper<Report> wrapper = new QueryWrapper<Report>().lambda()
                .eq(Objects.nonNull(search.getUserId()), Report::getFromUserId, search.getUserId())
                .eq(Objects.nonNull(search.getToUserId()), Report::getToUserId, search.getToUserId())
                .eq(Objects.nonNull(search.getState()), Report::getState, search.getState())
                .orderByDesc(Report::getCreateTime);
        PageHelper.startPage(search.getNumber(), search.getSize());
        return reportConverter.pageEntity2PageVo(new PageInfo<>(list(wrapper)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(List<ReportUpdateDto> dto) {
        updateBatchById(reportConverter.dto2Entity(dto));
    }

}
