package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SmsCodeDto {

    @ApiModelProperty(value = "接收人手机号", required = true)
    @NotBlank(message = "recipient can not be null.")
    private String mobileNumber;


}
