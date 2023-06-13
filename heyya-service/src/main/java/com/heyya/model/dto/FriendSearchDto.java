package com.heyya.model.dto;

import com.heyya.model.enums.FriendRelationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FriendSearchDto extends SearchDto {
    @ApiModelProperty("用户Id")
    private Long fromUserId;

    @ApiModelProperty(value = "好友id", hidden = true)
    private Long toUserId;

    @ApiModelProperty(value = "多个关系类型 or关系", hidden = true)
    private List<FriendRelationType> relationOR;

    @ApiModelProperty(value = "过滤黑名单")
    private Set<Long> blockIds;
}
