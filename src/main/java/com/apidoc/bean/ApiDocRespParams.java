package com.apidoc.bean;

import java.util.List;

/**
 * @Description: 响应参数
 * @Author: admin
 * @CreateDate: 2018/1/7 11:38
 */
public class ApiDocRespParams {
    List<ApiDocParam> params;

    public List<ApiDocParam> getParams() {
        return params;
    }

    public void setParams(List<ApiDocParam> params) {
        this.params = params;
    }
}
