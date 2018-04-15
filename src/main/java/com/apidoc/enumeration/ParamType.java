package com.apidoc.enumeration;

/**
 * @Description: 请求参数的类型
 * @Author: admin
 * @CreateDate: 2018/1/6 13:22
 */
public enum ParamType {
    /**
     * 请求参数在请求头
     */
    HEADER("header"),

    /**
     * 请求参数在url
     */
    URL("url"),

    /**
     * 请求参数是form表单数据
     */
    FORM("form"),

    /**
     * 请求数据是纯json数据
     */
    JSON("json");

    private final String param;

    ParamType(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return param;
    }
}
