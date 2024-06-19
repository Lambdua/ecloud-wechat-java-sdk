package com.lambdua.ecloud.common;

/**
 * api调用后错误状态异常
 *
 * @author LiangTao
 * @date 2024年06月19 14:12
 **/
public class ApiExecuteException extends RuntimeException{
    public ApiExecuteException() {
    }

    public ApiExecuteException(String message) {
        super(message);
    }

    public ApiExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiExecuteException(Throwable cause) {
        super(cause);
    }

    public ApiExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
