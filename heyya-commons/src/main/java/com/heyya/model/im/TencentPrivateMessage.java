package com.heyya.model.im;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TencentPrivateMessage {

    @JSONField(name = "To_Account")
    private List<String> toAccountList;
    @JSONField(name = "MsgRandom")
    private Integer msgRandom;
    @JSONField(name = "MsgBody")
    private List<BaseMsg> msgBody;
    @JSONField(name = "OfflinePushInfo")
    private Map<String, Object> offlinePushInfo;
    @JSONField(name = "From_Account")
    private String fromAccount;


}
