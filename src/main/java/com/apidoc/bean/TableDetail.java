package com.apidoc.bean;

/**
 * @Description: 数据库表的详细信息
 * <p>
 * 对应mysql的表信息，表的元数据
 * @Author: peng.liu
 * @CreateDate: 2018/2/1 14:50
 */
public class TableDetail {
    private String field;//字段
    private String require;//是否可以为空，是否为必须字段
    private String defaultValue;//默认值
    private String comment;//备注

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
