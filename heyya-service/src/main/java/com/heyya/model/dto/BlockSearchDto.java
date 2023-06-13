package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlockSearchDto extends SearchDto {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("被拉黑用户id")
    private Long toUserId;

}
