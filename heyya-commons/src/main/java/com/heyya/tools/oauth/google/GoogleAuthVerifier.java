package com.heyya.tools.oauth.google;

import com.heyya.tools.utils.GsonUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GoogleAuthVerifier {
    private static final String GOOGLE_AUTH_URL = "https://oauth2.googleapis.com/tokeninfo";

    public static GoogleAuthResult verify(String accessToken) {
        GoogleAuthResult result = null;
        HttpUrl url = HttpUrl.parse(GOOGLE_AUTH_URL).newBuilder()
                .addQueryParameter("id_token", accessToken)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            result = GsonUtils.fromJson(response.body().string(), GoogleAuthResult.class);
        } catch (IOException e) {
            throw new GoogleAuthFailedException(e.getMessage());
        }
        return result;
    }
}
