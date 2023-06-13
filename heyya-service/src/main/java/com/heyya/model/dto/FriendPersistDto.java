package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendPersistDto {
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long fromUserId;

    @ApiModelProperty("好友id")
    @NotNull
    private Long toUserId;
}
