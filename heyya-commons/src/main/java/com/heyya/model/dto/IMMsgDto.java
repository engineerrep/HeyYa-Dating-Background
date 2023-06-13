package com.heyya.model.dto;

import com.heyya.model.enums.ImSendMsgEnum;
import lombok.Data;

import java.util.List;

@Data
public class IMMsgDto {

    private List<String> targets;

    private String title;

    private String content;

    private String imSig;

    private String imSdkAppId;

    private String imAdminUser;

    private String beBlockId;

    private String userId;

    private String fromUserId;

    private String msg;

    private ImSendMsgEnum imSendMsgEnum;

}
