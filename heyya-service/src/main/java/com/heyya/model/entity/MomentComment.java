package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_moment_comment")
public class MomentComment extends BaseEntity {

    private Long momentId;

    private Long userId;

    private Long replyUserId;

    private String content;

}
