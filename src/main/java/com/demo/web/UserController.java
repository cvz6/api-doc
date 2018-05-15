package com.demo.web;

import com.apidoc.annotation.*;
import com.apidoc.enumeration.DataType;
import com.apidoc.enumeration.Method;
import com.apidoc.enumeration.ParamType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: 用户Controller
 * <p>
 * 用以展示api-doc的用法
 * @Author: peng.liu
 * @CreateDate: 2018/4/15 17:18
 */
@Api(name = "用户管理(文档使用demo看这里)", mapping = "user", order = 1)
@RestController
@RequestMapping("/user")
public class UserController {

    //--------------演示get请求--------------------
    //演示： get请求 无参数
    @ApiAction(name = "获取用户信息", order = 1, mapping = "/get", method = Method.GET)
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


    //演示：get请求 url带多个参数（标准RESTful风格参数）
    @ApiAction(name = "通过id和姓名获得用户信息", mapping = "/get/rest", method = Method.GET)
    @ApiReqParams(type = ParamType.URL, value = {
            @ApiParam(name = "id", dataType = DataType.STRING, defaultValue = "", description = "用户id"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "用户名"),
    })
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/get/rest/{id}/{name}")
    public Result getUserByIDAndName(@PathVariable("id") String id, @PathVariable("name") String name) {
        return Result.success(new User("1001", "毁灭你们，与你何干！", 18));
    }

    //演示：get请求 url带多个参数（参数拼接在url后，用问号拼接的参数）
    @ApiAction(name = "问号拼接参数方式", mapping = "/get/param", method = Method.GET)
    @ApiReqParams(type = ParamType.URL_PARAM, value = {
            @ApiParam(name = "id", dataType = DataType.STRING, defaultValue = "", description = "用户id"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "用户名"),
    })
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/get/param")
    public Result getUserByIDParam(String id, String name) {
        return Result.success(new User("1001", "毁灭你们，与你何干！", 18));
    }

    //演示：get请求 url带参数（参数拼接在url后，用问号拼接的参数）
    @ApiAction(name = "问号拼接参数方式，多个参数", mapping = "/get/params", method = Method.GET)
    @ApiReqParams(type = ParamType.URL_PARAM, value = {
            @ApiParam(name = "id", dataType = DataType.STRING, defaultValue = "", description = "用户id")
    })
    @ApiRespParams({
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码"),
            @ApiParam(name = "data", dataType = DataType.OBJECT, defaultValue = "null", description = "响应数据", object = "user"),
            @ApiParam(name = "name", dataType = DataType.STRING, defaultValue = "", description = "姓名", belongTo = "user"),
            @ApiParam(name = "age", dataType = DataType.NUMBER, defaultValue = "", description = "年龄", belongTo = "user"),

            @ApiParam(name = "message", dataType = DataType.STRING, defaultValue = "", description = "提示信息")
    })
    @GetMapping("/get/params")
    public Result getUserByIDParams(String id) {
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

    /**
     * 测试响应参数或请求参数  参数中包含对象的无限嵌套关系
     * <p>
     * 常见的参数是一个对象嵌套对象的类型
     */
    @ApiAction(name = "测试三层对象嵌套", mapping = "/nest", method = Method.POST)
    @ApiReqParams(type = ParamType.JSON,
            description = "测试数据\n参数的描述\n\n测试换行，这个应空两行以后显示",
            value = {
                    @ApiParam(name = "typeId", dataType = DataType.STRING, description = "归档类型ID"),
                    @ApiParam(name = "typeName", dataType = DataType.STRING, description = "归档类型名称"),
                    @ApiParam(name = "creators", dataType = DataType.ARRAY, description = "创建人列表", object = "creators"),

                    @ApiParam(name = "creator", dataType = DataType.OBJECT, description = "创建人", belongTo = "creators", object = "creator"),
                    @ApiParam(name = "name", dataType = DataType.STRING, description = "创建人姓名", belongTo = "creator"),
                    @ApiParam(name = "age", dataType = DataType.NUMBER, description = "创建人年龄", belongTo = "creator"),
            })
    @ApiRespParams(description = "测试数据\n 参数的描述 \n\n测试换行，这个应空两行以后显示",
            value = {
                    @ApiParam(name = "code", dataType = DataType.NUMBER, description = "状态编码"),
                    @ApiParam(name = "message", dataType = DataType.STRING, description = "信息提示"),
                    @ApiParam(name = "data", dataType = DataType.OBJECT, description = "响应数据", object = "PageResult"),
                    @ApiParam(name = "pageSize", dataType = DataType.NUMBER, description = "分页条数", belongTo = "PageResult"),
                    @ApiParam(name = "pageNum", dataType = DataType.NUMBER, description = "当前页码", belongTo = "PageResult"),
                    @ApiParam(name = "result", dataType = DataType.ARRAY, description = "结果集", belongTo = "PageResult", object = "ArchiveType"),
                    @ApiParam(name = "typeId", dataType = DataType.STRING, description = "归档类型ID", belongTo = "ArchiveType"),
                    @ApiParam(name = "ids", dataType = DataType.ARRAY, description = "归档类型ID数组", belongTo = "ArchiveType"),
                    @ApiParam(name = "typeName", dataType = DataType.NUMBER, description = "归档类型名称", belongTo = "ArchiveType")
            })
    @PostMapping("/nest")
    public Result nest() {
        return Result.success("测试三层对象嵌套成功，前后端都已经改为递归实现，支持对象间无限层级嵌套");
    }


    /**
     * 测试返回值为 字符串数组
     */
    @ApiAction(name = "测试返回值为字符串数组", mapping = "/strings", method = Method.POST)
    @ApiReqParams(type = ParamType.JSON)
    @ApiRespParams({
            @ApiParam(name = "idList", dataType = DataType.ARRAY, description = "ID列表集合")

    })
    @PostMapping("/strings")
    public Result strings() {
        return Result.success("测试返回值为字符串数组");
    }

    /**
     * 获取 图片随机验证码
     */
    @ApiAction(name = "获取随机验证码", description = "", mapping = "/getPictureCode", method = Method.GET)
    @ApiReqParams(type = ParamType.URL_BLOB)
    @ApiRespParams(value = {
            @ApiParam(name = "code", dataType = DataType.NUMBER, defaultValue = "0", description = "状态编码(0.失败 1.成功)"),
            @ApiParam(name = "data", dataType = DataType.STRING, defaultValue = "null", description = "响应数据"),
            @ApiParam(name = "total", dataType = DataType.NUMBER, defaultValue = "0", description = "数据总条数"),
            @ApiParam(name = "msg", dataType = DataType.STRING, defaultValue = "", description = "提示信息"),
    })
    @GetMapping("/getPictureCode")
    public Result getPictureCode(HttpServletRequest request, HttpServletResponse resp) {
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndImage();
        String code = codeMap.get("code").toString();
        System.err.println("验证码： " + code);

        //缓存验证码
        HttpSession session = request.getSession();
        session.setAttribute("pictureCode", code);


        // 禁止图像缓存
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -1);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("image"), "jpeg", sos);
            sos.close();
            return Result.success("获取成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("获取失败");
        }
    }

    //演示： get请求 无参数 无响应值
    @ApiAction(name = "测试无请求数据，无响应数据", mapping = "/noValue", method = Method.GET)
    @GetMapping("/noValue")
    public void noValue() {
    }


    //演示： 参数中增加描述信息
    @ApiAction(name = "测试参数中增加描述信息", mapping = "/description", method = Method.GET)
    @ApiReqParams(type = ParamType.URL, description = "测试数据\n 请求参数的描述")
    @ApiRespParams(description = "测试数据\n 参数的描述 \n\n测试换行，这个应空两行以后显示")
    @GetMapping("/description")
    public Result noValue1() {
        return Result.success("参数中增加描述信息");
    }

}
