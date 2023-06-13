package com.heyya.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class EmailVerifyDto {
    @ApiModelProperty("手机号码/email")
    @NotBlank
    private String email;

    @ApiModelProperty("短信验证码")
    @NotBlank
    private String code;
}
