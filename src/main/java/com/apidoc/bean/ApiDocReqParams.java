package com.apidoc.bean;

import java.util.List;

/**
 * @Description: 请求参数
 * @Author: admin
 * @CreateDate: 2018/1/7 10:54
 */
public class ApiDocReqParams {
    private String type;//请求参数的类型：header，url，form，json
    private List<ApiDocParam> params; //请求参数集合

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ApiDocParam> getParams() {
        return params;
    }

    public void setParams(List<ApiDocParam> params) {
        this.params = params;
    }
}
