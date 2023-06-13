
package com.heyya.tencent.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.heyya.tencent.anatations.ReqMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ReqMethod(url = "/v4/sns/black_list_add")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddBlackListReq extends BaseReq {
    @JSONField(name = "From_Account")
    private String uid;
    @JSONField(name = "To_Account")
    private List<String> blackIds;
}
