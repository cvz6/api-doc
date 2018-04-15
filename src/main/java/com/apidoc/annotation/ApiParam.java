package com.apidoc.annotation;


import com.apidoc.enumeration.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 请求参数 注解
 * <p>
 * 高度抽象出“参数@ApiParam”这个概念
 * 由“参数@ApiParam” 组成请求参数和响应参数
 * <p>
 * 原因：
 * 考虑到每个参数可能是由多个对象组成的，其中每一个对象又可能是由多个对象组成的
 * 即：一个参数在java中对应一个类，一个类可能由多个属性组成，一个属性又可以是一个类
 * <p>
 * 又由于java注解的局限性，不好表示对象中嵌套对象
 * 这里借鉴数据库的表关联思想，用二维的空间表达多维的空间
 * <p>
 * 也可以理解为对象中嵌套对象就是树结构tree，
 * 把tree转换成了list结构
 * 给每一个对象增加id和pid属性
 * <p>
 * <p>
 * 给参数一个object属性和belongTo属性
 * object ：对象名称
 * belongTo:从属于哪个对象
 * 如果参数是对象，指明对象名称，如果不是对象，指明该参数属于哪个对象
 * @Author: admin
 * @CreateDate: 2018/1/5 15:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParam {

    /**
     * 参数类型<br>
     * 使用该属性后，其他属性可以不加，该属性表示参数的类型，会自动读取数据库表中字段的类型，是否可以为null，注释
     */
    Class value() default void.class;

    String[] remove() default {};

    /**
     * 参数名称
     */
    String name() default "";

    /**
     * 数据类型
     */
    DataType dataType() default DataType.STRING;

    /**
     * 描述
     */
    String description() default "";

    /**
     * 默认值
     */
    String defaultValue() default "";


    /**
     * 是否必须
     */
    boolean required() default false;

    /**
     * 对象名称
     */
    String object() default "";

    /**
     * 从属于哪个对象
     *
     * @return
     */
    String belongTo() default "0";

}
