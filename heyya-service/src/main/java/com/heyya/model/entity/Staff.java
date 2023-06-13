package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.heyya.model.enums.Active;
import lombok.Data;

@Data
@TableName("tb_staff")
public class Staff extends BaseEntity {

    private String account;

    private String password;

    private Active state;

}
