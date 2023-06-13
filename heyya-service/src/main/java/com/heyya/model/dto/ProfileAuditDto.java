package com.heyya.model.dto;

import com.heyya.model.enums.VerifyState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProfileAuditDto {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("昵称审核状态")
    private VerifyState nicknameState;

    @ApiModelProperty("头像审核状态")
    private VerifyState avatarState;

    @ApiModelProperty("签名审核状态")
    private VerifyState aboutMeState;

    @ApiModelProperty("验证视频审核状态")
    private VerifyState verifyVideoState;

    @ApiModelProperty("媒体审核")
    private List<MediaUpdateDto> auditMedias;
}
