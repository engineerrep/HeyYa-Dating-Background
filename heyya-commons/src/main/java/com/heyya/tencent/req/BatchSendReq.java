
package com.heyya.tencent.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.heyya.model.im.BaseMsg;
import com.heyya.tencent.anatations.ReqMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ReqMethod(url = "/v4/openim/batchsendmsg")
@Data
@EqualsAndHashCode(callSuper = true)
public class BatchSendReq extends BaseReq {
    @JSONField(name = "From_Account")
    private String sender;
    @JSONField(name = "To_Account")
    private List<String> receivers;
    @JSONField(name = "MsgRandom")
    private Integer random = ThreadLocalRandom.current().nextInt(100000000);
    @JSONField(name = "MsgBody")
    private List<? extends BaseMsg> body;
    @JSONField(name = "OfflinePushInfo")
    private OfflinePushInfo offlinePushInfo;
}
