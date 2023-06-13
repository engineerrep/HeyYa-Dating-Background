package com.heyya.model.vo;

import com.heyya.model.enums.ProcessState;
import com.heyya.model.enums.ReportType;
import io.swagger.annotations.ApiModelProperty;
import liquibase.util.StringUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ReportVo extends BaseVo {

    @ApiModelProperty("举报用户ID")
    private String fromUserId;

    @ApiModelProperty("被举报用户ID")
    private String toUserId;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("举报类型")
    private ReportType type;

    @ApiModelProperty("举报媒体")
    private List<String> medias;

    @ApiModelProperty("处理状态")
    private ProcessState state;

    private String media;

    public List<String> getMedias() {
        if (StringUtils.isNotEmpty(media)) {
            return Arrays.asList(media.split(","));
        }
        return medias;
    }
}
