package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MomentThumbUpPersistDto {
    @ApiModelProperty(value = "momentId")
    @NotNull(message = "momentId is not null！")
    private Long momentId;
    @ApiModelProperty(value = "点赞用户 ID'", hidden = true)
    private Long userId;
    @ApiModelProperty(value = "被点赞用户ID")
    private Long toUserId;
}
