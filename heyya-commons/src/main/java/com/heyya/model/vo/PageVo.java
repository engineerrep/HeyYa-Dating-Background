package com.heyya.model.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {
    @ApiModelProperty("页码")
    private Integer number = 0;
    @ApiModelProperty("页大小")
    private Integer size = 10;
    @ApiModelProperty("总页数")
    private Integer totalPages = 0;
    @ApiModelProperty("总记录数")
    private Long totalElements = 0L;
    @ApiModelProperty("数据列表")
    private List<T> content = Lists.newArrayList();
    @ApiModelProperty("是否有下一页")
    private boolean hasNextPage = false;

    public boolean getHasNextPage() {
        return this.getNumber() < (this.getTotalPages() - 1);
    }

}
