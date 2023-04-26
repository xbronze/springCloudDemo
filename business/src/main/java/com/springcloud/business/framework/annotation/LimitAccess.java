package com.springcloud.business.framework.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义限流注解
 * @author xbronze
 * @date 2023-4-26 16:27:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface LimitAccess {

    /**
     * 资源的唯一key
     * 作用：不同的接口，不同的流量控制
     */
    String key() default "";

    /**
     * QPS，每秒查询数
     */
    double permitsPerSecond();

    /**
     * 获取令牌最大等待时间
     */
    long timeout();

    /**
     * 获取令牌最大等待时间，默认：毫秒
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 得不到令牌的提示语
     */
    String msg() default "系统排队人数太多，请稍后再试。";
}
