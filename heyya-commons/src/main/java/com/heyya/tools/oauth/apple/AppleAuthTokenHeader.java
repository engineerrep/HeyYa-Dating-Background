package com.heyya.tools.oauth.apple;

import lombok.Data;

@Data
public class AppleAuthTokenHeader {
    private String alg;
    private String kid;
}
