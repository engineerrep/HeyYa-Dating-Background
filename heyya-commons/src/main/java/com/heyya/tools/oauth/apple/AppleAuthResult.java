package com.heyya.tools.oauth.apple;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AppleAuthResult {
    private String iss;
    private String sub;
    private String aud;
    private Long iat;
    private Long exp;
    private String nonce;
    @SerializedName("nonce_supported")
    private Boolean nonceSupported;
    private String email;
    @SerializedName("email_verified")
    private Boolean emailVerified;
    @SerializedName("is_private_email")
    private Boolean isPrivateEmail;
    @SerializedName("real_user_status")
    private Integer realUserStatus;
}
