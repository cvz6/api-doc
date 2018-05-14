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
     * 请求参数是form表单数据
     */
    FORM("form"),

    /**
     * 请求数据是纯json数据
     */
    JSON("json"),

    /**
     * 请求参数在url restful风格，例如：/{id}/{name}
     */
    URL("url"),

    /**
     * 请求参数在url,以问号拼接 例如： ?id=1&name=2
     */
    URL_PARAM("url_param"),

    /**
     * 请求参数在url，但是响应数据为blob（数据流） 比如返回图片验证码使用
     */
    URL_BLOB("url_blob");

    private final String param;

    ParamType(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return param;
    }
}
