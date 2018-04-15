package com.apidoc.exception;

/**
 * @Description: 文档生成时的异常
 * @Author: peng.liu
 * @CreateDate: 2018/2/2 11:21
 */
public class ApiDocException extends RuntimeException {
    public ApiDocException(String message) {
        super(message);
    }
}
