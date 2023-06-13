package com.heyya.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.heyya.config.spring.SpringContextHolder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenUtils {

    private static final String ID = "id";
    private static final String ISSUER = "issuer";
    private static final String TIMESTAMP = "timestamp";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("12345");

    private static Long getExpireTime() {
        long expireTime = 7 * 24 * 60 * 60 * 1000L;
        UserAuthProperties properties = SpringContextHolder.getBean(UserAuthProperties.class);
        if (Objects.nonNull(properties)) {
            expireTime = properties.getTokenExpire() * 1000L;
        }
        return expireTime;
    }

    public static String create(UserToken token) {
        Date expiresAt = new Date(System.currentTimeMillis() + getExpireTime());
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(expiresAt)
                .withClaim(ID, token.getId())
                .withClaim(TIMESTAMP, token.getTimestamp())
                .sign(ALGORITHM);
    }

    public static UserToken verify(String token) {
        UserToken info = new UserToken();
        JWTVerifier verifier = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT decode = verifier.verify(token);
        info.setId(decode.getClaim(ID).asLong());
        info.setTimestamp(decode.getClaim(TIMESTAMP).asLong());
        return info;
    }

}
