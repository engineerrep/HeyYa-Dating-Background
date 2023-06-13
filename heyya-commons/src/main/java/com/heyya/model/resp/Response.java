package com.heyya.model.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class Response<T> {
    public static final Response<Object> SUCCESS = new Response<>(8200, "Success");
    public static final Response<Object> INVALID_TOKEN = new Response<>(8402, "Invalid token");
    private Integer code;
    private String msg;
    private Long timestamp = System.currentTimeMillis();
    private T data;

    public Response() {

    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Response<T> success() {
        return success(SUCCESS.getCode(), SUCCESS.getMsg(), null);
    }
    public static <T> Response<T> success(T data) {
        return success(SUCCESS.getCode(), SUCCESS.getMsg(), data);
    }

    public static <T> Response<T> success(int code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> failed(int code, String msg) {
        return failed(code, msg, null);
    }

    public static <T> Response<T> failed(int code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> internalError(T data) {
        return failed(8500, "Internal Error!", data);
    }

    public static <T> Response<T> paramsInvalid(T data) {
        return failed(8425, "Parameters invalid!", data);
    }
}
