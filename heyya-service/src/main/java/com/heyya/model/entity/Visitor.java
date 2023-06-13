package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_visitor")
public class Visitor extends BaseEntity {

    private Long fromUserId;

    private Long toUserId;

    private LocalDateTime visitTime;
}
