package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_block")
public class Block extends BaseEntity {

    private Long fromUserId;

    private Long toUserId;
}
