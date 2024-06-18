package com.lambdua.ecloud.common;

import lombok.Data;

/**
 * @author LiangTao
 * @date 2024年06月11 10:27
 **/

@Data
public class ApiResult<T> {
    /**
     * {
     * 	"code": "1000",
     * 	"message": "初始化通讯录成功",
     * 	"data": null
     * }
     */

    private String code;

    private String message;

    private T data;

    public boolean isSuccess() {
        return "1000".equals(code);
    }
}
