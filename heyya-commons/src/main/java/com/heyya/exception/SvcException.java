package com.heyya.exception;

import com.heyya.model.resp.Response;
import lombok.Data;

@Data
public class SvcException extends BaseException {
    private static final long serialVersionUID = 6853269508300836177L;

    public SvcException() {
        super();
    }

    public SvcException(String msg) {
        super(msg);
    }

    public SvcException(Response<?> resp) {
        super(resp);
    }
}
