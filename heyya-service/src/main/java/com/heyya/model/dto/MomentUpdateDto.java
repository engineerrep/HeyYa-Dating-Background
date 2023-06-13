package com.heyya.model.dto;

import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MomentUpdateDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户审核状态")
    private VerifyState verifyState;
}
