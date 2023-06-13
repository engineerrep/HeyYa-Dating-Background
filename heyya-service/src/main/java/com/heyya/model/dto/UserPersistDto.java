package com.heyya.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heyya.model.enums.Platform;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserPersistDto {
    @ApiModelProperty(value = "注册平台", allowableValues = "IOS,ANDROID", dataType = "java.lang.String")
    @NotNull(message = "platform can not be empty!")
    private Platform platform;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
