package com.heyya.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MomentCommentVo extends BaseVo {

    @ApiModelProperty(value = "UserVo")
    private UserVo user;

    @ApiModelProperty(value = "MomentVO")
    private MomentVo moment;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "被评论的用户id")
    private String replyUserId;

    @ApiModelProperty(value = "U")
    private String userId;
}
