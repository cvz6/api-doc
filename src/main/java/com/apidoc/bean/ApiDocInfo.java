package com.apidoc.bean;

/**
 * @Description: 文档的基本信息 bean
 * @Author: admin
 * @CreateDate: 2018/1/5 13:32
 */
public class ApiDocInfo {
    private String title; //标题
    private String description; //描述
    private String version; //版本

    public ApiDocInfo() {

    }

    public ApiDocInfo(String title) {
        this.title = title;
    }

    public ApiDocInfo(String title, String description, String version) {
        this.title = title;
        this.description = description;
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public ApiDocInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiDocInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ApiDocInfo setVersion(String version) {
        this.version = version;
        return this;
    }
}
