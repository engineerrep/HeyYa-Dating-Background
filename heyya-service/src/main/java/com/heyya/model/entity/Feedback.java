package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.heyya.model.enums.FeedbackType;
import com.heyya.model.enums.ProcessState;
import lombok.Data;

@Data
@TableName("tb_feedback")
public class Feedback extends BaseEntity {

    private Long userId;

    private String content;

    private FeedbackType type;

    private String media;

    private ProcessState state;
}
