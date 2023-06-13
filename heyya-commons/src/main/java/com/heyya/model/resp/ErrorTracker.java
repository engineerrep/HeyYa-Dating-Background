package com.heyya.model.resp;

import com.heyya.tools.utils.UUIDUtils;

import lombok.Data;

@Data
public class ErrorTracker {
    private Integer errorNo = Math.abs(UUIDUtils.lowerCaseNoSeparatorUUID().hashCode());
}
