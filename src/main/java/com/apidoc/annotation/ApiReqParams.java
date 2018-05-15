package com.apidoc.annotation;


import com.apidoc.enumeration.ParamType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: request请求中的参数集
 * <p>
 * 参数集@ApiReqParams 是由多个 参数@ApiParam 构成的
 * <p>
 * @Author: admin
 * @CreateDate: 2018/1/5 17:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiReqParams {

    /**
     * 参数类型
     */

    ParamType type() default ParamType.URL;

    /**
     * 参数项
     */
    ApiParam[] value() default {};

    /**
     * 描述
     */
    String description() default  "";
}
