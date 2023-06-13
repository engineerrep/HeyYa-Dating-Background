package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.heyya.model.enums.Deleted;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    @TableId
    private Long id;
    @Version
    private Long version;
    @TableField(updateStrategy = FieldStrategy.NEVER,insertStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;
    @TableField(updateStrategy = FieldStrategy.NEVER,insertStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;
    private Deleted deleted;
}
