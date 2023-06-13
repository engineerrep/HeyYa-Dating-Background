package com.heyya.tools.oauth.apple;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.heyya.tools.utils.GsonUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppleAuthVerifier {
    private static final String APPLE_AUTH_KEYS_URL = "https://appleid.apple.com/auth/keys";
    private static final String ISSUER = "https://appleid.apple.com";
    private static Map<String, AppleAuthKey> appleKeyMap;

    private static Map<String, AppleAuthKey> getAppleKeys() {
        if (MapUtils.isEmpty(appleKeyMap)) {
            Request request = new Request.Builder()
                .url(APPLE_AUTH_KEYS_URL)
                .get()
                .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                Map<String, Object> keysMap = GsonUtils.fromJson(response.body().string(), type);
                type = new TypeToken<List<AppleAuthKey>>() {}.getType();
                List<AppleAuthKey> appleKeyList = GsonUtils.fromJson(GsonUtils.toJson(keysMap.get("keys")), type);
                appleKeyMap = appleKeyList.stream().collect(Collectors.toMap(AppleAuthKey::getKid, v -> v));
            } catch (IOException e) {
                throw new AppleAuthFailedException(e.getMessage());
            }
        }
        return appleKeyMap;
    }

    private static RSAPublicKey getPublicKey(String kid) {
        try {
            AppleAuthKey appleKey = getAppleKeys().get(kid);
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(appleKey.getN()));
            BigInteger publicExponent = new BigInteger(1, Base64.getDecoder().decode(appleKey.getE()));

            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
            KeyFactory kf = KeyFactory.getInstance(appleKey.getKty());
            return (RSAPublicKey)kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Decode publicKey failed: {}", e.getMessage(), e);
        }
        return null;
    }

    private static AppleAuthTokenHeader decodeTokenHeader(String identityToken) {
        String[] arr = identityToken.split("\\.");
        String deHeader = new String(Base64.getDecoder().decode(arr[0]));
        return GsonUtils.fromJson(deHeader, AppleAuthTokenHeader.class);
    }

    public static AppleAuthResult verify(String identityToken, String bundleId) {
        AppleAuthTokenHeader header = decodeTokenHeader(identityToken);
        Algorithm algorithm = Algorithm.RSA256(getPublicKey(header.getKid()), null);
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(ISSUER)
            .withAudience(bundleId)
            .build();
        DecodedJWT decodedJWT = verifier.verify(identityToken);
        final Map<String, Object> maps = Maps.newHashMap();
        decodedJWT.getClaims().entrySet().forEach(item -> maps.put(item.getKey(), item.getValue().as(Object.class)));
        return GsonUtils.fromJson(GsonUtils.toJson(maps), AppleAuthResult.class);
    }
}
