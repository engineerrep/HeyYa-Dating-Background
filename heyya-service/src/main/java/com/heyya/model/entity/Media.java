package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.heyya.model.enums.MediaPrivacy;
import com.heyya.model.enums.MediaType;
import com.heyya.model.enums.VerifyState;
import lombok.Data;

@Data
@TableName("tb_media")
public class Media extends BaseEntity {

    private Long resourceId;

    private String url;

    private MediaType type;

    private Integer duration;

    private String cover;

    private String content;

    private VerifyState verifyState;

    private MediaPrivacy privacy;
}
