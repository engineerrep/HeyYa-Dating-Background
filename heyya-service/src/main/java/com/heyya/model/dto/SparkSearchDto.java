package com.heyya.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class SparkSearchDto extends SearchDto {

    @ApiModelProperty(value = "被pass的用户ID")
    private Set<Long> passUserIds;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省、州")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "性别，默认女性")
    private String sex;


}
