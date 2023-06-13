package com.heyya.model.dto;

import lombok.Data;

@Data
public class SearchDto {
    private Integer number = 1;
    private Integer size = 10;

    public Integer getNumber() {
        if (number < 1) {
            this.number = 1;
        } else if (number > 50) {
            this.number = 50;
        }
        return this.number;
    }

    public Integer getSize() {
        if (size < 1) {
            this.size = 10;
        } else if (size > 100) {
            this.size = 100;
        }
        return this.size;
    }
}
