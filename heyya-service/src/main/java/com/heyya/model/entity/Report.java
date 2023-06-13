package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.heyya.model.enums.ProcessState;
import com.heyya.model.enums.ReportType;
import lombok.Data;

@Data
@TableName("tb_report")
public class Report extends BaseEntity {

    private Long fromUserId;

    private Long toUserId;

    private String content;

    private ReportType type;

    private String media;

    private ProcessState state;
}
