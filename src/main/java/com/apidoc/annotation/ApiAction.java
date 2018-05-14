package com.apidoc.annotation;


import com.apidoc.enumeration.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 注解controller的每一个方法（action）的信息
 * @Author: admin
 * @CreateDate: 2018/1/4 11:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiAction {

    //action名称，方法的功能描述
    String name() default "";

    //请求路径url
    String mapping() default "";

    //接口描述
    String description() default "";

    //请求方式 method
    Method method() default Method.GET;

    //排序
    int order() default Integer.MAX_VALUE;


}
