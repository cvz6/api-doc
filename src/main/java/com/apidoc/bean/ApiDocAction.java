package com.apidoc.bean;

/**
 * @Description: 对应一个请求
 * <p>
 * 一个action，包括基本信息，请求参数和响应参数
 * @Author: admin
 * @CreateDate: 2018/1/7 10:46
 */
public class ApiDocAction {

    //-----基本信息----
    private String name; //名称或描述
    private String mapping;//url映射
    private String method;//请求方式 get，post
    private String description;//接口描述

    private ApiDocReqParams reqParams;//请求参数
    private ApiDocRespParams respParams;//响应参数

    private String methoduuid;// 方法名的唯一标识


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ApiDocReqParams getReqParams() {
        return reqParams;
    }

    public void setReqParams(ApiDocReqParams reqParams) {
        this.reqParams = reqParams;
    }

    public ApiDocRespParams getRespParams() {
        return respParams;
    }

    public void setRespParams(ApiDocRespParams respParams) {
        this.respParams = respParams;
    }

    public String getMethoduuid() {
        return methoduuid;
    }

    public void setMethoduuid(String methoduuid) {
        this.methoduuid = methoduuid;
    }
}
