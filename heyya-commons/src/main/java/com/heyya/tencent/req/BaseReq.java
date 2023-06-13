
package com.heyya.tencent.req;

import lombok.Data;

@Data
public abstract class BaseReq {
    private transient String userSig;
    private transient String identifier;
    private transient String sdkappid;
}
