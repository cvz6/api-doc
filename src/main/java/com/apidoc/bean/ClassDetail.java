package com.apidoc.bean;

/**
 * @Description: 数据库表的详细信息
 * <p>
 * 对应mysql的表信息，表的元数据
 * @Author: peng.liu
 * @CreateDate: 2018/2/1 14:50
 */
public class ClassDetail {
    private String field;//字段
    private String dataType;//数据类型

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
