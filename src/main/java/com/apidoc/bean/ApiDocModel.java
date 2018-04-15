package com.apidoc.bean;

import java.util.List;

/**
 * @Description: 功能模块
 * <p>
 * 模块由基本信息action列表组成
 * </p>
 * @Author: admin
 * @CreateDate: 2018/1/5 13:35
 */
public class ApiDocModel {

    private String name;//模块名称，表示该模块的功能
    private String rootMapping;// 父url路径映射 controller的RquestMapping值

    private List<ApiDocAction> list; //action列表

    public ApiDocModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootMapping() {
        return rootMapping;
    }

    public void setRootMapping(String rootMapping) {
        this.rootMapping = rootMapping;
    }

    public List<ApiDocAction> getList() {
        return list;
    }

    public void setList(List<ApiDocAction> list) {
        this.list = list;
    }
}
