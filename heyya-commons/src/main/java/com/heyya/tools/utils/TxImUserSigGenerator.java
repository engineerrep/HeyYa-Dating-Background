package com.heyya.tools.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.Deflater;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TxImUserSigGenerator {
    private static String IM_SECRET_KEY = "your key";
    private static Long IM_SDK_APP_ID = 123L;

    public static String genSig(String userId) {
        long expire = 365 * 24 * 60 * 60L;
        return genSigWithUserBuf(IM_SDK_APP_ID, IM_SECRET_KEY, userId, expire,
                null);
    }

    private static String hmacsha256(long imSdkAppId, String imSecretKey, String identifier, long currTime, long expire,
                                     String base64Userbuf) {
        String contentToBeSigned = "TLS.identifier:" + identifier + "\n" + "TLS.sdkappid:" + imSdkAppId + "\n"
                + "TLS.time:" + currTime + "\n" + "TLS.expire:" + expire + "\n";
        if (null != base64Userbuf) {
            contentToBeSigned += "TLS.userbuf:" + base64Userbuf + "\n";
        }
        try {
            byte[] byteKey = imSecretKey.getBytes("UTF-8");
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA256");
            hmac.init(keySpec);
            byte[] byteSig = hmac.doFinal(contentToBeSigned.getBytes("UTF-8"));
            return (new BASE64Encoder().encode(byteSig)).replaceAll("\\s*", "");
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (InvalidKeyException e) {
            return "";
        }
    }

    private static String genSig(long imSdkAppId, String imSecretKey, String identifier, long expire, byte[] userbuf) {

        long currTime = System.currentTimeMillis() / 1000;

        JSONObject sigDoc = new JSONObject();
        sigDoc.put("TLS.ver", "2.0");
        sigDoc.put("TLS.identifier", identifier);
        sigDoc.put("TLS.sdkappid", imSdkAppId);
        sigDoc.put("TLS.expire", expire);
        sigDoc.put("TLS.time", currTime);

        String base64UserBuf = null;
        if (null != userbuf) {
            base64UserBuf = new BASE64Encoder().encode(userbuf);
            sigDoc.put("TLS.userbuf", base64UserBuf);
        }
        String sig = hmacsha256(imSdkAppId, imSecretKey, identifier, currTime, expire, base64UserBuf);
        if (sig.length() == 0) {
            return "";
        }
        sigDoc.put("TLS.sig", sig);
        Deflater compressor = new Deflater();
        compressor.setInput(sigDoc.toString().getBytes(Charset.forName("UTF-8")));
        compressor.finish();
        byte[] compressedBytes = new byte[2048];
        int compressedBytesLength = compressor.deflate(compressedBytes);
        compressor.end();
        return (new String(Base64URL.base64EncodeUrl(Arrays.copyOfRange(compressedBytes, 0, compressedBytesLength))))
                .replaceAll("\\s*", "");
    }

    public static String genSigWithUserBuf(long imSdkAppId, String imSecretKey, String identifier, long expire,
                                           byte[] userbuf) {
        return genSig(imSdkAppId, imSecretKey, identifier, expire, userbuf);
    }

}
