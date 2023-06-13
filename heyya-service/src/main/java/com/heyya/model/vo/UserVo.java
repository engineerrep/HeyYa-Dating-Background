package com.heyya.model.vo;


import com.heyya.model.enums.Active;
import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserVo extends BaseVo {

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
    @ApiModelProperty("账号状态")
    private Active active;
    @ApiModelProperty("昵称审核状态")
    private VerifyState nicknameState;
    @ApiModelProperty("头像审核状态")
    private VerifyState avatarState;
    @ApiModelProperty("签名审核状态")
    private VerifyState aboutMeState;
    @ApiModelProperty("验证视频审核状态")
    private VerifyState verifyVideoState;
    @ApiModelProperty("用户主视频")
    private String mainVideo;
    @ApiModelProperty("我是否喜欢过对方")
    private Boolean liked;
    @ApiModelProperty("是否与对方形成match")
    private Boolean matchd;
    @ApiModelProperty("是否pass对方")
    private Boolean passed;
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
