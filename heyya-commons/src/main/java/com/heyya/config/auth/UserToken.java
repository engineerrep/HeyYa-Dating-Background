package com.heyya.config.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserToken {

    private Long id;

    private Long timestamp = System.currentTimeMillis();

    public UserToken(Long userId) {
        this.id = userId;
    }
}
