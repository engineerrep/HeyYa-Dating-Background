package com.heyya.model.im;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;
import com.heyya.model.dto.IMMsgDto;
import lombok.Data;

import java.util.concurrent.ConcurrentMap;

@Data
public class MsgBodyBaen extends BaseMsg {

    @JSONField(name = "MsgType")
    private String msgType = "TIMTextElem";
    @JSONField(name = "MsgContent")
    private Object msgContent;

    public MsgBodyBaen(BaseMsg baseMsg) {
        ConcurrentMap<Object, Object> dataMap = Maps.newConcurrentMap();
        dataMap.put("Text", JSONObject.toJSONString(baseMsg));
        this.msgContent = dataMap;
    }

    public MsgBodyBaen(IMMsgDto im) {
        ConcurrentMap<Object, Object> dataMap = Maps.newConcurrentMap();
        dataMap.put("Text", im.getContent());
        this.msgContent = dataMap;
    }

    public MsgBodyBaen(String msg) {
        ConcurrentMap<Object, Object> dataMap = Maps.newConcurrentMap();
        dataMap.put("Text", msg);
        this.msgContent = dataMap;
    }


}
