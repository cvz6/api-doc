package com.apidoc.bean;

import java.util.List;

/**
 * @Description: 参数
 * <p>
 * 请求参数和响应参数的组成部分
 * @Author: admin
 * @CreateDate: 2018/1/7 11:39
 */
public class ApiDocParam {
    /**
     * 参数名称
     */
    private String name;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 描述
     */
    private String description;
    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否必须
     */
    private boolean required;
    /**
     * 对象名称
     */
    private transient String objectName;
    /**
     * 所属于哪个对象
     */
    private transient String belongTo;

    /**
     * 参数是数组
     */
    private List<ApiDocParam> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public List<ApiDocParam> getList() {
        return list;
    }

    public void setList(List<ApiDocParam> list) {
        this.list = list;
    }
}

