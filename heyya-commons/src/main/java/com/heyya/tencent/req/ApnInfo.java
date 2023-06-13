
package com.heyya.tencent.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApnInfo {
    @JSONField(name = "Title")
    private String title;
}
