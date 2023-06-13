package com.heyya.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VisitorVo extends BaseVo {
    @ApiModelProperty("被访问者信息")
    private UserVo toUser;
}
