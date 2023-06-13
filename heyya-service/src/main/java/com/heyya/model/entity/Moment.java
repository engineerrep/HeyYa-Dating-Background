package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.heyya.model.enums.VerifyState;
import lombok.Data;

@Data
@TableName("tb_moment")
public class Moment extends BaseEntity {

    private Long userId;

    private String title;

    private String content;

    private VerifyState verifyState;
}
