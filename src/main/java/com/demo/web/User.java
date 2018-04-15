package com.demo.web;

import com.apidoc.annotation.Table;

/**
 * @Description: 用户实体类
 * @Author: peng.liu
 * @CreateDate: 2018/4/15 17:10
 */
@Table("user")
public class User {

    private String id;//编号
    private String name;//姓名
    private Integer age;//年龄

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }


    public User(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
