package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VisitorSearchDto extends SearchDto {
    @ApiModelProperty(value = "fromUserId")
    private Long fromUserId;

    @ApiModelProperty(value = "toUserId")
    private Long toUserId;
}
