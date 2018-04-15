package com.apidoc.bean;

import java.util.List;

/**
 * @Description: api文档对象
 * <p>
 * 文档由基本信息和模块列表组成
 * @Author: admin
 * @CreateDate: 2018/1/5 13:33
 */
public class ApiDoc {
    private ApiDocInfo info; //基本信息
    private List<ApiDocModel> models; //文档列表

    public ApiDocInfo getInfo() {
        return info;
    }

    public void setInfo(ApiDocInfo info) {
        this.info = info;
    }

    public List<ApiDocModel> getModels() {
        return models;
    }

    public void setModels(List<ApiDocModel> models) {
        this.models = models;
    }
}
