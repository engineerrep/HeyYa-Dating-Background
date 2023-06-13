package com.heyya.model.vo;


import com.heyya.model.enums.Active;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffVo extends BaseVo {

    @ApiModelProperty("登录账户")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("员工状态")
    private Active state;
}
