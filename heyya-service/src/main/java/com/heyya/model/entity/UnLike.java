package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_unlike")
public class UnLike extends BaseEntity {

    private Long fromUserId;

    private Long toUserId;
}
