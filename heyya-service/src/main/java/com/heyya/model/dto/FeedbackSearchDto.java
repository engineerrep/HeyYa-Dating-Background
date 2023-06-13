package com.heyya.model.dto;

import com.heyya.model.enums.ProcessState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FeedbackSearchDto extends SearchDto {

    @ApiModelProperty(value = "反馈用户ID")
    private Long userId;

    @ApiModelProperty("处理状态")
    private ProcessState state;
}
