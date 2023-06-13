package com.heyya.tools.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

public final class GsonUtils {
    private static final Gson gson = new Gson();

    private GsonUtils() {

    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static JsonObject toJsonObject(Object obj) {
        return toJsonObject(toJson(obj));
    }

    public static JsonObject toJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public static JsonArray toJsonArray(Object obj) {
        return toJsonArray(toJson(obj));
    }

    public static JsonArray toJsonArray(String json) {
        return JsonParser.parseString(json).getAsJsonArray();
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T fromObj(Object obj, Class<T> clazz) {
        return fromJson(gson.toJson(obj), clazz);
    }

    public static <T> T fromObj(Object obj, Type type) {
        return fromJson(gson.toJson(obj), type);
    }
}