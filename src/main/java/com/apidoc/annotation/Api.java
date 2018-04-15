package com.apidoc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 功能模块信息
 * <p>
 * 该注解作用于controller类上，表明该类代表的功能模块的信息
 * </p>
 * 示例：@Api(name = "测试功能模块",mapping = "/apiTest")
 *
 * @Author: admin
 * @CreateDate: 2018/1/6 10:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Api {
    /*
        注解参数的可支持数据类型：
    　　　　1.所有基本数据类型（int,float,boolean,byte,double,char,long,short)
    　　　　2.String类型
    　　　　3.Class类型
    　　　　4.enum类型
    　　　　5.Annotation类型
    　　　　6.以上所有类型的数组
     */


    //模块名称，表示该模块的功能
    String name() default "";

    //url映射
    String mapping() default "";


}
