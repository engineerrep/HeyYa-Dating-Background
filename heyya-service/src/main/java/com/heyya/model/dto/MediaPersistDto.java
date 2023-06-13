package com.heyya.model.dto;

import com.heyya.model.enums.MediaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MediaPersistDto {

    @ApiModelProperty("数据源ID")
    private Long resourceId;

    @ApiModelProperty("媒体链接")
    private String url;

    @ApiModelProperty("媒体类型")
    private MediaType type;

    @ApiModelProperty("时长")
    private Integer duration;

    @ApiModelProperty("媒体封面")
    private String cover;

    @ApiModelProperty("内容描述")
    private String content;

}
