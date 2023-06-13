package com.heyya.model.dto;

import com.heyya.model.enums.MediaPrivacy;
import com.heyya.model.enums.MediaType;
import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MediaSearchDto extends SearchDto {

    @ApiModelProperty("数据源ID")
    private Long resourceId;
    @ApiModelProperty(value = "审核状态", hidden = true)
    private VerifyState verifyState;
    @ApiModelProperty(value = "媒体类型", hidden = true)
    private List<MediaType> mediaTypes;
    @ApiModelProperty(value = "过滤掉的id", hidden = true)
    private List<Long> passIds;
    @ApiModelProperty(value = "视频隐私状态", hidden = true)
    private MediaPrivacy privacy;
}
