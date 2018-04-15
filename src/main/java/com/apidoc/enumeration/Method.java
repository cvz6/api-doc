package com.apidoc.enumeration;

/**
 * @Description: 请求方式
 * 对应http请求的方式，目前只使用到get和post
 * @Author: admin
 * @CreateDate: 2018/1/4 11:48
 */
public enum Method {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private final String method;

    Method(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }
}
