package com.heyya.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SparkVo extends BaseVo {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("签名")
    private String aboutMe;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份/州")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("经度")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @ApiModelProperty("视频对象")
    private List<MediaVo> medias;

    @ApiModelProperty("主视频")
    private String mainVideo;
}
