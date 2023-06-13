package com.heyya.config.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthContext {
    private static final ThreadLocal<UserToken> context = new ThreadLocal<>();

    public static Long getId() {
        return context.get().getId();
    }

    public static Long getTimestamp() {
        return context.get().getTimestamp();
    }

    public static void setUserTokenInfo(UserToken info) {
        context.set(info);
    }

    public static void release() {
        context.remove();
    }
}
