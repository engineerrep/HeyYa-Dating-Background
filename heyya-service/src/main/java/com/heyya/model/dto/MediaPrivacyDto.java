package com.heyya.model.dto;

import com.heyya.model.enums.MediaPrivacy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MediaPrivacyDto {
    @ApiModelProperty("id")
    @NotNull(message = "id is not null！")
    private Long id;

    @ApiModelProperty("视频隐私状态")
    private MediaPrivacy privacy;
}
