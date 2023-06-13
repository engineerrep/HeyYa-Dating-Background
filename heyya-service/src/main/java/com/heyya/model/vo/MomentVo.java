package com.heyya.model.vo;


import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MomentVo extends BaseVo {
    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("Moment标题")
    private String title;

    @ApiModelProperty("Moment内容")
    private String content;

    @ApiModelProperty("Moment图片集合")
    private List<MediaVo> medias;

    @ApiModelProperty("用户对象")
    private UserVo user;

    @ApiModelProperty("评论数")
    private Long commentCount;

    @ApiModelProperty("点赞数")
    private Long thumbCount;

    @ApiModelProperty("是否点赞")
    private Boolean isThumb;

    @ApiModelProperty("是否审核")
    private VerifyState verifyState;
}