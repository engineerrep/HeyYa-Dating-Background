package com.heyya.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.heyya.model.enums.FriendRelationType;
import lombok.Data;

@Data
@TableName("tb_friend")
public class Friend extends BaseEntity {

    private Long fromUserId;

    private Long toUserId;

    private FriendRelationType relation;
}
