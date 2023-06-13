package com.heyya.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelNumberVO {
    @ApiModelProperty(value = "喜欢我的数量")
    private Long likeMeNum;
    @ApiModelProperty(value = "match数量")
    private Long matchNum;
    @ApiModelProperty(value = "访客数量")
    private Long visitorsNum;
    @ApiModelProperty(value = "我喜欢的数量")
    private Long myLikeNum;
}
