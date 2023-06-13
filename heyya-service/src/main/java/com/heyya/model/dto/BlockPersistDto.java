package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BlockPersistDto {
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long fromUserId;

    @ApiModelProperty("拉黑用户id")
    @NotNull(message = "toUserId not null")
    private Long toUserId;
}
