package com.lambdua.ecloud.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LiangTao
 * @date 2024年06月11 10:59
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RateLimit {
    /**
     * 每秒可允许的请求数
     */
    int limit() default 3;

    /**
     * 限流时间,单位秒
     */
    int limitTime() default 60;

    /**
     * 限流类型
     */
    String type() default "default";

    /**
     * 添加随机延迟,单位ms,最大值,最小值
     */
    String delay() default "0,0";

    /**
     * 请求超时时间,单位s,默认不超时
     */
    int timeout() default -1;
}
