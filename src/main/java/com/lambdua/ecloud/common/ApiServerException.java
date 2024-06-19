package com.lambdua.ecloud.common;

/**
 * 接口服务方异常
 * @author LiangTao
 * @date 2024年06月19 14:08
 **/
public class ApiServerException extends RuntimeException{
    public ApiServerException() {
    }

    public ApiServerException(String message) {
        super(message);
    }

    public ApiServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiServerException(Throwable cause) {
        super(cause);
    }

    public ApiServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
