package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_subscribe_email")
public class SubscribeEmail extends BaseEntity {

    private String email;
}
