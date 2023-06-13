package com.heyya.model.dto;

import com.heyya.model.enums.Active;
import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSearchDto extends SearchDto {
    @ApiModelProperty("用户名")
    private String nickname;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("账号状态")
    private Active active;
    @ApiModelProperty("审核状态")
    private VerifyState state;
}
