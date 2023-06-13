package com.heyya.tools.oauth.google;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GoogleAuthResult {
    private String iss;
    private String sub;
    private String azp;
    private String aud;
    private String iat;
    private String exp;
    private String email;
    @SerializedName("email_verified")
    private String emailVerified;
    private String name;
    private String picture;
    @SerializedName("given_name")
    private String givenName;
    @SerializedName("family_name")
    private String familyName;
    private String local;
}
