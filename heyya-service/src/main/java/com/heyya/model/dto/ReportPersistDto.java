package com.heyya.model.dto;

import com.heyya.model.enums.ReportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReportPersistDto {

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "被举报人")
    private Long toUserId;

    @ApiModelProperty(value = "举报类型")

    private ReportType type;

    @ApiModelProperty(value = "媒体连接")
    private List<String> medias;
}
