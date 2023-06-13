package com.heyya.model.dto;

import com.heyya.model.enums.AccountType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountPersistDto {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("用户Id")
    private Long userId;
    @ApiModelProperty("账号类型")
    private AccountType type;
}
