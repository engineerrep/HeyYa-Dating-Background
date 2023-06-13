package com.heyya.model.dto;

import com.heyya.model.enums.AccountType;
import com.heyya.model.enums.Platform;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SingInDto {
    @ApiModelProperty(value = "注册平台", allowableValues = "IOS,ANDROID", dataType = "java.lang.String")
    @NotNull(message = "platform can not be empty!")
    private Platform platform;

    @ApiModelProperty("第三方的accessToken")
    @NotBlank(message = "account can not be empty!")
    private String account;

    @ApiModelProperty(value = "账号类型", allowableValues = "APPLEID, GOOGLE，DEVICE", dataType = "java.lang.String")
    @NotNull(message = "type can not be empty!")
    private AccountType type;

    @ApiModelProperty("bundleId, 用AppleID登录时填写，其他不用")
    private String bundleId;
}
