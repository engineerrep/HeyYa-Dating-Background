package com.heyya.model.vo;

import com.heyya.model.enums.FeedbackType;
import com.heyya.model.enums.ProcessState;
import io.swagger.annotations.ApiModelProperty;
import liquibase.util.StringUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class FeedbackVo extends BaseVo {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "反馈类型")
    private FeedbackType type;

    private String media;

    @ApiModelProperty(value = "媒体链接")
    private List<String> medias;

    @ApiModelProperty("处理状态")
    private ProcessState state;

    public List<String> getMedias() {
        if (StringUtils.isNotEmpty(media)) {
            return Arrays.asList(media.split(","));
        }
        return medias;
    }
}
