package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_moment_thumb_up")
public class MomentThumbUp extends BaseEntity {

    private Long momentId;

    private Long userId;

    private Long toUserId;

}
