package com.apidoc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: response 响应参数集
 * 一个响应参数集由多个参数组成
 * @Author: admin
 * @CreateDate: 2018/1/5 17:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiRespParams {
    /**
     * 参数项
     */
    ApiParam[] value();
}
