package com.heyya.model.dto;

import com.heyya.model.im.BaseMsg;
import lombok.Data;

import java.util.List;

@Data
public class ImSendMsgDto extends BaseMsg {

    private String fromUserId;

    private List<String> targets;

    private String title;

    private String content;


}
