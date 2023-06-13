package com.heyya.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountVo extends BaseVo {

    @ApiModelProperty("用户账号")
    private String account;

}
