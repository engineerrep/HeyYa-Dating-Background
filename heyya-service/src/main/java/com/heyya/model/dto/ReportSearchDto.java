package com.heyya.model.dto;

import com.heyya.model.enums.ProcessState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportSearchDto extends SearchDto {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty("被举报用户id")
    private Long toUserId;

    @ApiModelProperty("处理状态")
    private ProcessState state;

}
