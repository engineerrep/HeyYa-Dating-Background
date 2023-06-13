package com.heyya.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlockVo extends BaseVo {
    @ApiModelProperty("被拉黑用户")
    private UserVo toUser;

}
