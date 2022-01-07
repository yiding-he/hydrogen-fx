package com.hyd.fx;

/**
 * (description)
 * created at 2018/2/6
 *
 * @author yidin
 */
public class FxException extends RuntimeException {

    public static FxException wrap(Throwable t) {
        return t instanceof FxException ? (FxException) t : new FxException(t);
    }

    public FxException() {
    }

    public FxException(String message) {
        super(message);
    }

    public FxException(String message, Throwable cause) {
        super(message, cause);
    }

    public FxException(Throwable cause) {
        super(cause);
    }
}
