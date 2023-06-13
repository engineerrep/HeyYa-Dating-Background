package com.heyya.model.vo;

import com.heyya.model.enums.FriendRelationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FriendVo extends BaseVo {
    @ApiModelProperty("关系")
    private FriendRelationType relation;
    @ApiModelProperty("好友")
    private UserVo toUser;
}
