package com.demo.web;

import com.apidoc.annotation.ApiAction;
import com.apidoc.annotation.ApiParam;
import com.apidoc.annotation.ApiReqParams;
import com.apidoc.annotation.ApiRespParams;
import com.apidoc.enumeration.DataType;
import com.apidoc.enumeration.Method;
import com.apidoc.enumeration.ParamType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 用户Controller
 * @Author: peng.liu
 * @CreateDate: 2018/4/15 17:18
 */
@RestController
@RequestMapping("/user")
public class UserController {


    /**
     * 添加用户
     * @return Result
     */
    @ApiAction(name = "获得用户信息", mapping = "/get", method = Method.GET)
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据"),
            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/get")
    public Result add(){
        return Result.success(new User("1001","张三",18));
    }

    /**
     * 添加用户
     * @return Result
     */
    @ApiAction(name = "添加活动分类", mapping = "/add", method = Method.POST)
    @ApiReqParams(type = ParamType.JSON, value = {@ApiParam(value = User.class, remove = {"userid", "id"})})
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据"),
            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        return Result.success(new User("1001","张三",18));
    }

    /**
     * 测试失败
     * @return Result
     */
    @PostMapping("/fail")
    public Result fail(){
        return Result.fail();
    }

    /**
     *
     * @return
     */
    @PostMapping("/find")
    public Result find(){
        List<User> list = new ArrayList<>(5);
        list.add(new User("1001","张三",18));
        list.add(new User("1002","李四",45));
        list.add(new User("1003","老五",34));
        list.add(new User("1041","admin",18));
        return Result.success(list);
    }
}
