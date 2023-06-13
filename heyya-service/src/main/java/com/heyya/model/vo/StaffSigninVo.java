package com.heyya.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StaffSigninVo {

    @ApiModelProperty("接口访问令牌")
    private String token;

}
