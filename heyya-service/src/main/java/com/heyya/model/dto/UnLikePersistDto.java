package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UnLikePersistDto {
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long fromUserId;

    @ApiModelProperty("不喜欢用户id")
    @NotNull
    private Long toUserId;
}
