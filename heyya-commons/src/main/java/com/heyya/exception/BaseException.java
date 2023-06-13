package com.heyya.exception;

import com.heyya.model.resp.Response;
import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 6853269508300836177L;
    private Integer code = 8500;
    private String msg = "Internal Error!";

    public BaseException() {
        super();
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(Response<?> resp) {
        super(resp.getMsg());
        this.code = resp.getCode();
        this.msg = resp.getMsg();
    }
}
