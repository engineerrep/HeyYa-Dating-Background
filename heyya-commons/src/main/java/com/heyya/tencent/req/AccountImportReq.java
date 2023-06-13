package com.heyya.tencent.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.heyya.tencent.anatations.ReqMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ReqMethod(url = "/v4/im_open_login_svc/account_import")
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountImportReq extends BaseReq {
    @JSONField(name = "Identifier")
    private String userId;

    @JSONField(name = "Nick")
    private String nickName;

    @JSONField(name = "FaceUrl")
    private String avatar;
}
