package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MomentCommentPersistDto {
    @ApiModelProperty(value = "评论用户的id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "momentId")
    @NotNull
    private Long momentId;

    @ApiModelProperty(value = "内容")
    @NotBlank
    private String content;

    @ApiModelProperty(value = "被回复的用户id")
    private Long replyUserId;
}
