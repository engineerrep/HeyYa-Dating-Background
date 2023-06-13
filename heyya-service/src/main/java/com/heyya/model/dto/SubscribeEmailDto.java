package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SubscribeEmailDto {
    @ApiModelProperty(value = "email")
    @NotNull(message = "email is not null!")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
            message = "Incorrect mailbox format!")
    private String email;
}
