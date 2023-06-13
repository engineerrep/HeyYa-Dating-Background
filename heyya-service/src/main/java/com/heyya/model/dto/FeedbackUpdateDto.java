package com.heyya.model.dto;

import com.heyya.model.enums.ProcessState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FeedbackUpdateDto {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("状态")
    private ProcessState state;
}
