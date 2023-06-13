package com.heyya.tools.utils;

import java.util.UUID;

public final class UUIDUtils {
    private UUIDUtils() {

    }

    public static String lowerCaseUUID() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    public static String upperCaseUUID() {
        return lowerCaseUUID().toUpperCase();
    }

    public static String lowerCaseNoSeparatorUUID() {
        return lowerCaseUUID().replace("-", "");
    }

    public static String upperCaseNoSeparatorUUID() {
        return lowerCaseNoSeparatorUUID().toUpperCase();
    }
}
