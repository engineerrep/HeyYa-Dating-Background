package com.heyya.exception;

import com.heyya.model.resp.Response;
import lombok.Data;

@Data
public class DaoException extends BaseException {
    private static final long serialVersionUID = 6853269508300836177L;

    public DaoException() {
        super();
    }

    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(Response<?> resp) {
        super(resp);
    }
}
