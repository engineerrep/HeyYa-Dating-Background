package com.heyya.model.dto;

import com.heyya.model.enums.Active;
import com.heyya.model.enums.ShowVideosType;
import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserUpdateDto {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("用户名")
    private String nickname;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("签名")
    private String aboutMe;
    @ApiModelProperty("视频显示状态")
    private ShowVideosType showVideosState;
    @ApiModelProperty("国家")
    private String country;
    @ApiModelProperty("省份、州")
    private String province;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("经度")
    private BigDecimal lon;
    @ApiModelProperty("纬度")
    private BigDecimal lat;
    @ApiModelProperty("账号状态")
    private Active active;
    @ApiModelProperty("email")
    private String email;
    @ApiModelProperty("cInstagram")
    private String instagram;
    @ApiModelProperty("SnapChat")
    private String snapchat;
    @ApiModelProperty("tikTok")
    private String tiktok;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty(hidden = true)
    private String mainVideo;
    @ApiModelProperty(hidden = true)
    private VerifyState nicknameState;
    @ApiModelProperty(hidden = true)
    private VerifyState avatarState;
    @ApiModelProperty(hidden = true)
    private VerifyState aboutMeState;
    @ApiModelProperty(hidden = true)
    private VerifyState verifyVideoState;
}
