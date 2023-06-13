package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.heyya.model.enums.AccountType;
import lombok.Data;

@Data
@TableName("tb_account")
public class Account extends BaseEntity {

    private String account;

    private Long userId;

    private AccountType type;
}
