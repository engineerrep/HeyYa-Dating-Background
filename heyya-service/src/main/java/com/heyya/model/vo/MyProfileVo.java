package com.heyya.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MyProfileVo extends BaseVo {

    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("签名")
    private String aboutMe;
    @ApiModelProperty("媒体资源")
    private List<MediaVo> medias;
    @ApiModelProperty("email")
    private String email;
    @ApiModelProperty("Instagram")
    private String instagram;
    @ApiModelProperty("SnapChat")
    private String snapchat;
    @ApiModelProperty("tikTok")
    private String tiktok;
    @ApiModelProperty("电话")
    private String phone;
}
