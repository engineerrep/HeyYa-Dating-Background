package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountUpdateDto {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("用户名")
    private String account;
}
