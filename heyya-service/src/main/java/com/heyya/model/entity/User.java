package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.heyya.model.enums.Active;
import com.heyya.model.enums.Platform;
import com.heyya.model.enums.ShowVideosType;
import com.heyya.model.enums.VerifyState;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@TableName("tb_user")
public class User extends BaseEntity {

    private String nickname;

    private String avatar;

    private String sex;

    private String aboutMe;

    private VerifyState nicknameState;

    private VerifyState avatarState;

    private VerifyState aboutMeState;

    private LocalDate birthday;

    private Platform platform;

    private ShowVideosType showVideosState;

    private String country;

    private String province;

    private String city;

    private BigDecimal lon;

    private BigDecimal lat;

    private Active active;

    private VerifyState verifyVideoState;

    private String mainVideo;

    private String email;

    private String instagram;

    private String snapchat;

    private String tiktok;

    private String phone;
}
