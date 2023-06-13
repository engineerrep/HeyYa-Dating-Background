
package com.heyya.tencent.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class OfflinePushInfo {
    @JSONField(name = "PushFlag")
    private Integer pushFlag = 0;
    @JSONField(name = "Title")
    private String title;
    @JSONField(name = "Desc")
    private String content;
    @JSONField(name = "ApnsInfo")
    private ApnInfo apnInfo;
}
