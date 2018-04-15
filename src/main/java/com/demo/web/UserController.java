package com.demo.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户Controller
 * @Author: peng.liu
 * @CreateDate: 2018/4/15 17:18
 */
@RestController
@RequestMapping("/")
public class UserController {

    @PostMapping("/add")
    public User add(){
        return new User("1001","张三",18);
    }
}
