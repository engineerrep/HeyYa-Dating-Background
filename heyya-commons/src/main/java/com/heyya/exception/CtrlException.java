package com.heyya.exception;

import com.heyya.model.resp.Response;

public class CtrlException extends BaseException {
    private static final long serialVersionUID = 6853269508300836177L;

    public CtrlException() {
        super();
    }

    public CtrlException(String msg) {
        super(msg);
    }

    public CtrlException(Response<?> resp) {
        super(resp);
    }
}
