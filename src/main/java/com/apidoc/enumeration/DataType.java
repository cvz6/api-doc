package com.apidoc.enumeration;

/**
 * @Description: 参数的 数据类型
 * @Author: admin
 * @CreateDate: 2018/1/6 12:36
 */
public enum DataType {
    /**
     * 字符串
     */
    STRING("string"),

    /**
     * 数字类型
     */
    NUMBER("number"),

    /**
     * 文件
     */
    FILE("file"),

    /**
     * 日期
     */
    DATE("date"),

    /**
     * 对象
     */
    OBJECT("object"),


    /**
     * 数组
     */
    ARRAY("array"),

    /**
     * 布尔类型
     */
    BOOLEAN("boolean"),

    /**
     * 集合类型
     */
    MAP("map");

    private String type;

    DataType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
