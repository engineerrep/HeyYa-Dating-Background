package com.heyya.model.dto;

import com.heyya.model.enums.ImSendMsgEnum;
import lombok.Data;

@Data
public class BaseIMSendMsgDto<T> {

    private String fromUserId;
    private ImSendMsgEnum imSendMsgEnum;
    private T content;

}
