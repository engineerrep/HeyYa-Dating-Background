package com.heyya.model.dto;

import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MomentSearchDto extends SearchDto {

    @ApiModelProperty("userId")
    private String userId;
    @ApiModelProperty("审核状态")
    private VerifyState verifyState;
}
