package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VisitorPersistDto {
    @ApiModelProperty(value = "访问者Id")
    private Long fromUserId;

    @ApiModelProperty(value = "被访问者Id")
    private Long toUserId;

}
