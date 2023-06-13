package com.heyya.model.vo;

import com.heyya.model.enums.MediaPrivacy;
import com.heyya.model.enums.MediaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SparkVideoVo extends BaseVo {

    @ApiModelProperty("数据源ID")
    private String resourceId;

    @ApiModelProperty("媒体链接")
    private String url;

    @ApiModelProperty("媒体类型")
    private MediaType type;

    @ApiModelProperty("时长")
    private Integer duration;

    @ApiModelProperty("媒体封面")
    private String cover;

    @ApiModelProperty("内容描述")
    private String content;

    @ApiModelProperty("用户VO")
    private UserVo user;

    @ApiModelProperty("视频隐私状态")
    private MediaPrivacy privacy;

}
