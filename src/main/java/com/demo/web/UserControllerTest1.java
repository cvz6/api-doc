package com.demo.web;

import com.apidoc.annotation.*;
import com.apidoc.enumeration.DataType;
import com.apidoc.enumeration.Method;
import com.apidoc.enumeration.ParamType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 用户Controller
 * <p>
 * 用以展示api-doc的用法
 * @Author: peng.liu
 * @CreateDate: 2018/4/15 17:18
 */
@Api(name = "测试001", mapping = "user")
@RestController
@RequestMapping("/test1")
public class UserControllerTest1 {

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
    public Result getUserByIDParam(String id) {
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

    //演示：put请求
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

    //演示：delete请求
    @ApiAction(name = "测试delete请求", mapping = "/delete", method = Method.DELETE)
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据"),
            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @DeleteMapping("/delete")
    public Result delete() {
        return Result.success();
    }

    //演示：post请求 参数为json
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

    //演示：post请求 单上传文件
    @ApiAction(name = "上传单个文件", mapping = "/file", method = Method.POST)
    @ApiReqParams(type = ParamType.FORM,
            value = {
                    @ApiParam(name = "file", dataType = DataType.FILE, description = "文件"),
            })
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "文件名称"),
            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @PostMapping("/file")
    public Result file(@RequestParam("file") MultipartFile file) {
        return Result.success(file.getOriginalFilename());
    }

    //演示：post请求 单上传文件
    @ApiAction(name = "上传多个文件", mapping = "/files", method = Method.POST)
    @ApiReqParams(type = ParamType.FORM,
            value = {
                    @ApiParam(name = "files", dataType = DataType.FILE, description = "文件"),
            })
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "文件数量"),
            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @PostMapping("/files")
    public Result files(@RequestParam("files") MultipartFile[] files) {
        System.out.println(files.length);
        return Result.success(files.length);
    }
}
