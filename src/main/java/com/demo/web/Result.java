package com.demo.web;

/**
 * @Description: 封装请求响应数据工具类
 * @Author: peng.liu
 * @CreateDate: 2018/4/15 17:25
 */
public class Result<T> {

    private static String MESSAGE_SUCCESS = "操作成功";
    private static String MESSAGE_FAIL = "操作失败";

    private Integer code;//状态码 1.成功 0.失败
    private String message;//响应信息
    private T data;//响应数据

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result<Object> success(Object data) {
        return new Result<>(1, MESSAGE_SUCCESS, data);
    }

    public static Result<Object> success() {
        return new Result<>(1, MESSAGE_SUCCESS, null);
    }


    public static Result<Object> fail() {
        return new Result<>(0, MESSAGE_FAIL, null);
    }

    public static Result<Object> fail(Object data) {
        return new Result<>(1, MESSAGE_SUCCESS, data);
    }
}
