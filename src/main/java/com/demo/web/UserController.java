package com.demo.web;

import com.apidoc.annotation.*;
import com.apidoc.enumeration.DataType;
import com.apidoc.enumeration.Method;
import com.apidoc.enumeration.ParamType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 用户Controller
 * <p>
 * 用以展示api-doc的用法
 * @Author: peng.liu
 * @CreateDate: 2018/4/15 17:18
 */
@Api(name = "用户管理", mapping = "user")
@RestController
@RequestMapping("/user")
public class UserController {

    //--------------演示get请求--------------------
    //演示： get请求 无参数
    @ApiAction(name = "获取用户信息", mapping = "/get", method = Method.GET)
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/get")
    public Result getUser() {
        return Result.success(new User("1001", "一切问题，都只是时间问题。", 18));
    }

    //演示：get请求 url带参数（标准RESTful风格参数）
    @ApiAction(name = "通过id获得用户信息", mapping = "/get/rest", method = Method.GET)
    @ApiReqParams(type = ParamType.URL, value = {
            @ApiParam(name = "id", dataType = DataType.STRING, defaultValue = "", description = "用户id")
    })
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/get/rest/{id}")
    public Result getUserByID(@PathVariable("id") String id) {
        return Result.success(new User("1001", "毁灭你们，与你何干！", 18));
    }

    //演示：get请求 url带参数（参数拼接在url后，用问号拼接的参数）
    @ApiAction(name = "通过id查询用户信息", mapping = "/get/param", method = Method.GET)
    @ApiReqParams(type = ParamType.URL, value = {
            @ApiParam(name = "id", dataType = DataType.STRING, defaultValue = "", description = "用户id")
    })
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/get/param")
    public Result getUserByIDParam( String id) {
        return Result.success(new User("1001", "毁灭你们，与你何干！", 18));
    }

    //演示： get请求 返回数据列表
    @ApiAction(name = "获取用户列表", mapping = "/getlist", method = Method.GET)
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.ARRAY, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/getlist")
    public Result getList() {
        User[] list = new User[]{
                new User("10301", "三体", 108),
                new User("10201", "一切问题，都只是时间问题。", 18),
                new User("1003", "哈哈啊哈", 888),
                new User("10301", "哈哈啊哈", 888),
        };
        return Result.success(list);
    }

//todo 其他测试，这几天补上








    @ApiAction(name = "添加用户", mapping = "/add", method = Method.POST)
    @ApiReqParams(type = ParamType.JSON, value = {@ApiParam(value = User.class, remove = {"id"})})
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据"),
            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        return Result.success();
    }


    /**
     * 测试失败
     *
     * @return Result
     */
    @ApiAction(name = "测试失败信息", mapping = "/fail", method = Method.PUT)
    @ApiReqParams(type = ParamType.JSON, value = {@ApiParam(value = User.class, remove = {"id"})})
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据"),
            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @PutMapping("/fail")
    public Result fail() {
        return Result.fail();
    }

    /**
     * 查询
     *
     * @return
     */
    @ApiAction(name = "查询用户", mapping = "/find", method = Method.POST)
    @ApiReqParams(type = ParamType.JSON, value = {@ApiParam(value = User.class, remove = {"id"})})
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @PostMapping("/find")
    public Result find(User user) {
        List<User> list = new ArrayList<>(5);
        list.add(new User("1001", "张三", 18));
        list.add(new User("1002", "李四", 45));
        list.add(new User("1003", "老五", 34));
        list.add(new User("1041", "admin", 18));
        return Result.success(list);
    }
}
