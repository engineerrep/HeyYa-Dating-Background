package com.heyya.model.dto;

import com.heyya.model.enums.FeedbackType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackPersistDto {

    @ApiModelProperty(value = "反馈用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty("类容")
    private String content;

    @ApiModelProperty("反馈类型")
    private FeedbackType type;

    @ApiModelProperty("反馈图片")
    private List<String> medias;
}
