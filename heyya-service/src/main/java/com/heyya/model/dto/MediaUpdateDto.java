package com.heyya.model.dto;

import com.heyya.model.enums.MediaType;
import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MediaUpdateDto {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("数据源id")
    private Long resourceId;

    @ApiModelProperty("审核状态")
    private VerifyState verifyState;

    @ApiModelProperty("内容描述")
    private String content;

    @ApiModelProperty("url链接")
    private String url;

    @ApiModelProperty("时长")
    private Integer duration;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("视频类型")
    private MediaType type;
}
