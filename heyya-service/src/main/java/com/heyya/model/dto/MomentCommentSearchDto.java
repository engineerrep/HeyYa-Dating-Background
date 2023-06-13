package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MomentCommentSearchDto extends SearchDto {
    @ApiModelProperty(value = "momentId")
    private Long momentId;

    @ApiModelProperty(value = "userId")
    private Long userId;
}
