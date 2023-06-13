package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MomentPersistDto {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("Moment标题")
    private String title;

    @ApiModelProperty("Moment内容")
    private String content;

    @ApiModelProperty("Moment图片集合")
    private List<MediaPersistDto> medias;
}
