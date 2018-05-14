# api-doc  文档生成工具
java开发，根据代码自动生成api接口文档工具，支持RESTful风格

预览版 [http://lovepeng.gitee.io/apidoc](http://lovepeng.gitee.io/apidoc)

# 该工具的优势

- 文档自动生成，减少文档维护的时间和成本

- 支持复杂的接口请求参数或响应参数，比如对象的多层、互相嵌套

- 支持多种参数类型（数组，List，字符串，数字，文件等等）

- 支持RESTful风格的接口，同样支持普通的接口

- 支持与spring/spring-boot无缝对接，支持jar包方式运行，支持war包方式运行

- 支持mock功能，可直接测试接口，方便调试和检查接口写的是否正确

- 方便规范整个团队的接口风格和代码，风格不一致的报错提示


##### Github开源地址： https://github.com/liupeng328/api-doc

##### 开源中国地址： https://gitee.com/lovepeng/api-doc

# 预览
![基本信息](https://upload-images.jianshu.io/upload_images/2833665-a2ce576b7438c8ce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![演示](https://upload-images.jianshu.io/upload_images/2833665-cba03dffe34b6793.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![数据模拟mock](https://upload-images.jianshu.io/upload_images/2833665-d9a928816a328096.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 在线预览地址 
[http://lovepeng.gitee.io/apidoc](http://lovepeng.gitee.io/apidoc)
# 开发原理
这个工具是一个典型的前后端分离开发的项目，想了解前后端分离开发的同学也可以下载本项目学习。

项目后端使用java代码，前端使用angular开发。Java开发时，使用注解把文档相关信息标注在类的方法上，通过工具自动扫描代码的注解，生成json数据，发给前端，前端angular解析生成页面

本项目自带一个spring-boot框架为基础的demo（这里使用spring-boot做演示的demo仅仅是为了方便，本质上只要是java写的项目都可以用该工具），前端用angular做了一个比较漂亮的界面（最终前端界面都编译成了html，如果你前端不熟悉，可以跳过，不用管他），这里使用angular开发仅仅是我比较喜欢，你可以用任何你喜欢的的前端框架或者仅仅使用html写一个漂亮的界面就可以。

后端项目开源地址：https://github.com/liepeng328/api-doc

前端开源地址：https://github.com/liepeng328/api-doc-angular

后端数据说明在这里 [src/main/resources/doc/数据说明.json](src/main/resources/doc/数据说明.json)

![功能目录对应关系](https://upload-images.jianshu.io/upload_images/2833665-d9627161b59b7673.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![请求参数和响应参数对应](https://upload-images.jianshu.io/upload_images/2833665-53359d84c7531029.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 快速启动
当成一个工具类用就可以了，下载本项目，拷贝包com.apidoc下的代码到你的系统，
然后拷贝前端html页面，在static.apidoc文件下，到你的资源文件下。即可使用
使用时，后台提供两个接口，目录文档接口和某个功能的详细接口
```
//生成目录接口
ApiDoc apiDoc = new GeneratorApiDoc()
                        .setInfo(//设置文档基本信息
                                new ApiDocInfo()
                                        .setTitle("某莫系统后台管理文档")
                                        .setVersion("1.0")
                                        .setDescription("")

                        )
                        .generator(packageName);//指定生成哪个包下controller的文档
 System.err.println(JsonUtil.toString(apiDoc));

//详细功能接口
ApiDocAction detail = new GeneratorApiDoc()
                //设置数据库连接信息，可忽略
                .setDriver(driver)
                .setUrl(url)
                .setUserName(userName)
                .setPassword(password)
                .setDataBaseName(dataBaseName)
                .getApiOfMethod(methodUUID);
        System.err.println(JsonUtil.toString(detail));
```

# 一个详细的例子
一个详细例子如下代码，这里是springboot/springmvc的controller示例（展示两个文档，前端接口和后台接口）参考代码这个类 UserController.java

# 注解详细介绍
共有6个注解，标注出整个文档信息（我为什么讲那么详细，那么啰嗦，而且我没有把这个项目打成jar包直接给别人使用，就是因为文档生成最大可能是需要特殊定制，确保你拿到该代码可以个性化定制功能，随意修改）。
- Api 标注文档的功能模块
- ApiAction 标注一个功能
- ApiReqAparams 请求参数
- ApiResqAparams 响应参数
- ApiParam 参数，用以组成请求参数和响应参数
- Table 用以标注实体类（比如bean）和数据库表的关系，自动从数据库读取相关信息，不用写大量的 ApiReqAparams和ApiResqAparams
## 详细介绍如下
Api：写在类上，表明一个功能模块。
属性：
- name 模块名称
- mapping url映射
- order 排序顺序，int类型，数值小的排在前边，默认值为int最大值
![](https://upload-images.jianshu.io/upload_images/2833665-0ac022ed836cebfe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

ApiAction： 写在方法上，表明一个功能点
属性：
- name 方法的功能名称
- mapping url映射
- description 描述
- order 排序顺序，int类型，数值小的排在前边，默认值为int最大值
- method 请求方式（get，post，put，delete）
![](https://upload-images.jianshu.io/upload_images/2833665-4af8001b0c02387f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

ApiReqParams： 请求参数
属性：
- type：参数类型
>  - header  在请求头 
>- url  在url后拼接,restful风格的参数，示例： /{id}/{name}
>- url_param  在url后拼接，以问号分隔拼接，示例： ?id=11&name=2
>- url_blob  请求方式为url，响应数据为二进制流，比如图片流，随机验证码
>- form  表单数据
>- json  json格式
- ApiParam :参数列表
>-  value : class类，增加该类可自动读取数据库信息，避免写多个属性
>-  remove： 配合value使用，去除class类中无用的属性，比如id
>-  dataType: 数据类型（字符串string,数字number,文件file,日期date,对象object,数组array,布尔类型boolean）
>-  descrption:描述
>-  defaultValue： 默认值
>- required：是否必须
>-  object：从属于哪个对象（因为请求参数或者响应参数可能是对象中嵌套对象的，这里为了更好的表示这种层级关系，增加两个属性，object和belongTo，构建一个树结构，表示对象之间无限、互相嵌套）
>-  belognTo ： 对应object 默认值为"0"，字符串0
![](https://upload-images.jianshu.io/upload_images/2833665-ad96b77ad5326e3e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

ApiRespParams： 响应参数
属性：
- ApiParam： 该参数等同于请求参数中的ApiParam，参考如上描述
![](https://upload-images.jianshu.io/upload_images/2833665-0373d707dd37b9f4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 下载本项目并运行
配置jdk8以上版本，下载代码，在mysql数据库创建一个user表，
sql在这里 [src/main/resources/db/sql.sql](src/main/resources/db/sql.sql)
运行[ApidocApplication](src/main/java/com/demo/ApidocApplication.java)类main方法即可
然后访问地址 [http://localhost:8080/index.html](http://localhost:8080/index.html)

![](https://upload-images.jianshu.io/upload_images/2833665-ebdf7ed6bdefb210.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/2833665-6ac946d6c51e320b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 欢迎加入开发
提交bug，讨论问题,提出建议 https://github.com/liepeng328/api-doc/issues/new
参与代码修改或者文档修改，请fork本项目，然后提交pull request，这是0.0.1版，争取做到比swagger好用，尤其对国内开发者友好
欢迎各种建议，请提交issues

# 感谢列表
该项目为maven项目，引用工具请查看 pom.xml

感谢 spring-boot

感谢@路晓磊 的工具类hutool

感谢阿里fastjson

感谢阿里angular版前端开源框架ng-zorro


# 欢迎加入群交流
点击链接加入群聊【web开发交流群 317896269】：https://jq.qq.com/?_wv=1027&k=55PiekD
