package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountSearchDto extends SearchDto {
    @ApiModelProperty("用户账号")
    private String account;
}
