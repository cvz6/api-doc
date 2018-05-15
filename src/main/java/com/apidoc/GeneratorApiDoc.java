package com.apidoc;


import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.apidoc.annotation.*;
import com.apidoc.bean.*;
import com.apidoc.exception.ApiDocException;
import com.apidoc.utis.ClassScanUtil;
import com.apidoc.utis.DatabaseMetaDataUtil;
import com.apidoc.utis.utils.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @Description: 生成API文档工具类
 * @Author: admin
 * @CreateDate: 2018/1/4 15:04
 */
public class GeneratorApiDoc {

    //数据库信息
    private String driver;
    private String url;
    private String username;
    private String password;
    private String dataBaseName;
    //保存文档信息
    private ApiDoc apiDoc = new ApiDoc();
    //java数据类型的一个映射
    private static Map<String, String> dataTypeMap;

    //缓存 所有的方法
    private static Map<String, Method> methodMap = new HashMap<>();

    /**
     * 设置文档基本信息
     *
     * @param apiDocInfo
     */
    public GeneratorApiDoc setInfo(ApiDocInfo apiDocInfo) {
        this.apiDoc.setInfo(apiDocInfo);
        return this;
    }

    /**
     * 设置数据库连接驱动
     *
     * @param driver
     * @return
     */
    public GeneratorApiDoc setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    /**
     * 设置数据库连接url
     *
     * @param url
     * @return
     */
    public GeneratorApiDoc setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置数据库名称
     *
     * @param dataBaseName
     * @return
     */
    public GeneratorApiDoc setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
        return this;
    }

    /**
     * 设置数据库连接用户名
     *
     * @param username
     * @return
     */
    public GeneratorApiDoc setUserName(String username) {
        this.username = username;
        return this;
    }

    /**
     * 设置数据库连接密码
     *
     * @param password
     * @return
     */
    public GeneratorApiDoc setPassword(String password) {
        this.password = password;
        return this;
    }


    /**
     * 得到某个包下所有的注解为@Api的controller类
     * 把controller类信息转换成文档信息数据
     * <p>
     * 对应一个controller：模块信息，action信息
     *
     * @param packageName 包名称
     */
    public ApiDoc generator(String packageName) {
        //得到所有标准了文档的controller类
        //扫描指定包路径下所有包含指定注解的类
        Set<Class> classes = ClassScanUtil.getClass4Annotation(packageName, Api.class);
//        System.err.println(JsonUtil.toString(classes));
        if (classes != null && classes.size() > 0) {
            //设置模块列表信息
            this.apiDoc.setModels(getApiDocModelList(classes));
        } else {
            System.err.println("你要生成文档的package下没有一个类存在文档生成的注解标记");
        }
        return apiDoc;
    }

    /**
     * 获得功能模块列表信息
     *
     * @param classes
     * @return
     */
    public List<ApiDocModel> getApiDocModelList(Set<Class> classes) {
        List<ApiDocModel> apiDocModelList = new ArrayList<>(classes.size());//保存每一个功能模块的信息
        //循环获取信息
        for (Class controllerClass : classes) {
            ApiDocModel apiDocModel = new ApiDocModel();//保存一个功能模块的信息
            Api api = (Api) controllerClass.getAnnotation(Api.class);
            //设置基本信息
            apiDocModel.setName(api.name());
            apiDocModel.setRootMapping(api.mapping());
            apiDocModel.setOrder(api.order());
            //设置action列表信息
            apiDocModel.setList(getApiDocActionList(controllerClass));
            apiDocModelList.add(apiDocModel);
        }
        //排序： 根据模块的名称
        apiDocModelList.sort(Comparator.comparing(ApiDocModel::getName));
        apiDocModelList.sort(Comparator.comparing(ApiDocModel::getOrder));
        return apiDocModelList;
    }

    /**
     * 获得action列表信息
     *
     * @param controllerClass
     * @return
     */
    private List<ApiDocAction> getApiDocActionList(Class controllerClass) {
        //得到所有包含注解@ApiAction的方法
        Method[] methods = ClassUtil.getPublicMethods(controllerClass);
        List<ApiDocAction> apiDocActionList = null; //保存每一个action信息
        if (null != methods && methods.length > 0) {
            apiDocActionList = new ArrayList<>(methods.length);
            for (Method actionMethod : methods) {
                ApiAction apiAction = actionMethod.getAnnotation(ApiAction.class);
                if (null != apiAction) {
                    String methodUUID = actionMethod.getDeclaringClass() + "_" + actionMethod.getName();
                    methodMap.put(methodUUID, actionMethod);//加入缓存

                    ApiDocAction apiDocAction = new ApiDocAction();
                    apiDocAction.setName(apiAction.name());//设置名称
                    apiDocAction.setOrder(apiAction.order());//设置排序
                    apiDocAction.setMethoduuid(methodUUID);//设置方法名的唯一标识

                    apiDocActionList.add(apiDocAction);
                }
            }
        }
        //排序
        apiDocActionList.sort(Comparator.comparing(ApiDocAction::getName));
        apiDocActionList.sort(Comparator.comparing(ApiDocAction::getOrder));
        return apiDocActionList;
    }

    /**
     * 根据方法名唯一标识 获取 该方法的文档信息
     */
    public ApiDocAction getApiOfMethod(String methodUUID) {
        Method method = methodMap.get(methodUUID);
        if (null == method) {
            throw new RuntimeException("找不到相应方法： " + methodMap.get(methodUUID));
        }
        ApiAction apiAction = method.getAnnotation(ApiAction.class);
        if (null != apiAction) {
            ApiDocAction apiDocAction = new ApiDocAction();
            //设置基本信息
            apiDocAction.setName(apiAction.name());
            apiDocAction.setMapping(apiAction.mapping());
            apiDocAction.setDescription(apiAction.description());
            apiDocAction.setMethod(apiAction.method().toString());
            //设置请求参数
            apiDocAction.setReqParams(getApiDocReqParams(method));
            //设置响应参数
            apiDocAction.setRespParams(getApiDocRespParams(method));
            return apiDocAction;
        }
        return null;
    }

    /**
     * 获得action的请求参数
     *
     * @param actionMethod
     * @return
     */
    private ApiDocReqParams getApiDocReqParams(Method actionMethod) {
        ApiReqParams apiReqParams = actionMethod.getAnnotation(ApiReqParams.class);
        ApiDocReqParams apiDocReqParams = null;
        if (null != apiReqParams) {
            apiDocReqParams = new ApiDocReqParams();
            //设置请求参数的类型
            apiDocReqParams.setType(apiReqParams.type().toString());
            //设置请求参数列表
            apiDocReqParams.setParams(getApiParam(apiReqParams.value()));
            //设置描述
            apiDocReqParams.setDescription(apiReqParams.description());
        }
        return apiDocReqParams;
    }

    /**
     * 获得action的响应参数
     *
     * @param actionMethod
     * @return
     */
    private ApiDocRespParams getApiDocRespParams(Method actionMethod) {
        ApiRespParams apiRespParams = actionMethod.getAnnotation(ApiRespParams.class);
        ApiDocRespParams apiDocRespParams = null;
        if (null != apiRespParams) {
            apiDocRespParams = new ApiDocRespParams();
            List<ApiDocParam> apiDocRespParamList = getApiParam(apiRespParams.value());
            //设置描述
            apiDocRespParams.setDescription(apiRespParams.description());
            apiDocRespParams.setParams(apiDocRespParamList);
        }
        return apiDocRespParams;
    }

    //获取api参数
    private List<ApiDocParam> getApiParam(ApiParam[] apiParamsArray) {
        List<ApiDocParam> apiDocParams = null;
        if (null != apiParamsArray && apiParamsArray.length > 0) {
            apiDocParams = new ArrayList<>();
            for (ApiParam apiParam : apiParamsArray) {
                if (null != apiParam) {
                    Class paramClass = apiParam.value();
                    //如果有class类型则反射类，获取参数的信息
                    if (paramClass != void.class) {
                        //参数含有value，object，belongTo时，保存该参数信息为belongTo的子参数，并将其value属性解析为子参数
                        //参数含有value和object属性时，保存该条参数信息，并将其value属性解析为子参数
                        //参数含有value和belongTo属性时，不保存该条参数信息，将其value属性解析为belongTo的子参数
                        //参数只有value属性时，将value属性解析为参数
                        if (StringUtil.isNotBlank(apiParam.object()) && !"0".equals(apiParam.belongTo())) {
                            keepParam4Sub(apiDocParams, apiParam, paramClass);
                            resolveValue2ObjectParam(apiDocParams, apiParam, paramClass);
                        } else if (StringUtil.isNotBlank(apiParam.object())) {
                            keepParam(apiDocParams, apiParam);
                            resolveValue2ObjectParam(apiDocParams, apiParam, paramClass);
                        } else if (!"0".equals(apiParam.belongTo())) {
                            resolveValue2Param(apiDocParams, apiParam, paramClass);
                        } else {
                            resolveValue2Param(apiDocParams, apiParam, paramClass);
                        }
                    } else {
                        keepParam(apiDocParams, apiParam);
                    }
                }
            }
        }
        return list2Json(apiDocParams);

    }

    private void keepParam4Sub(List<ApiDocParam> apiDocParams, ApiParam apiParam, Class paramClass) {
        //解析类的属性，每一个属性组成一个参数对象
        List<ClassDetail> classDetailList = getApiDocModelList(apiParam, paramClass);
        //解析表，获取表的元数据
        Map<String, TableDetail> tableDetailMap = getTableDetailMap(paramClass);
        for (ClassDetail classDetail : classDetailList) {
            ApiDocParam apiDocParam1 = new ApiDocParam();
            apiDocParam1.setBelongTo(apiParam.belongTo());
            apiDocParam1.setObjectName(apiParam.object());
            String fieldName = classDetail.getField();
            apiDocParam1.setName(fieldName);
            apiDocParam1.setDataType(classDetail.getDataType());
            TableDetail tableDetail = tableDetailMap.get(fieldName);
            if (tableDetail != null) {
                apiDocParam1.setRequired("NO".equals(tableDetail.getRequire()));
                apiDocParam1.setDefaultValue(tableDetail.getDefaultValue());
                apiDocParam1.setDescription(tableDetail.getComment());
            }
            apiDocParams.add(apiDocParam1);
        }
    }

    /**
     * 将其value属性解析为子参数
     *
     * @param apiDocParams
     * @param apiParam
     * @param paramClass
     */
    private void resolveValue2Param(List<ApiDocParam> apiDocParams, ApiParam apiParam, Class paramClass) {
        //解析类的属性，每一个属性组成一个参数对象
        List<ClassDetail> classDetailList = getApiDocModelList(apiParam, paramClass);
        //解析表，获取表的元数据
        Map<String, TableDetail> tableDetailMap = getTableDetailMap(paramClass);
        for (ClassDetail classDetail : classDetailList) {
            ApiDocParam apiDocParam1 = new ApiDocParam();
            apiDocParam1.setBelongTo(apiParam.belongTo());
            String fieldName = classDetail.getField();
            apiDocParam1.setName(fieldName);
            apiDocParam1.setDataType(classDetail.getDataType());
            TableDetail tableDetail = tableDetailMap.get(fieldName);
            if (tableDetail != null) {
                apiDocParam1.setRequired("NO".equals(tableDetail.getRequire()));
                apiDocParam1.setDefaultValue(tableDetail.getDefaultValue());
                apiDocParam1.setDescription(tableDetail.getComment());
            }
            apiDocParams.add(apiDocParam1);
        }
    }

    /**
     * 将其value属性解析为子参数，并设置父参数为object属性
     *
     * @param apiDocParams
     * @param apiParam
     * @param paramClass
     */
    private void resolveValue2ObjectParam(List<ApiDocParam> apiDocParams, ApiParam apiParam, Class paramClass) {
        //解析类的属性，每一个属性组成一个参数对象
        List<ClassDetail> classDetailList = getApiDocModelList(apiParam, paramClass);
        //解析表，获取表的元数据
        Map<String, TableDetail> tableDetailMap = getTableDetailMap(paramClass);
        for (ClassDetail classDetail : classDetailList) {
            ApiDocParam apiDocParam1 = new ApiDocParam();
            apiDocParam1.setBelongTo(apiParam.object());
            String fieldName = classDetail.getField();
            apiDocParam1.setName(fieldName);
            apiDocParam1.setDataType(classDetail.getDataType());
            TableDetail tableDetail = tableDetailMap.get(fieldName);
            if (tableDetail != null) {
                apiDocParam1.setRequired("NO".equals(tableDetail.getRequire()));
                apiDocParam1.setDefaultValue(tableDetail.getDefaultValue());
                apiDocParam1.setDescription(tableDetail.getComment());
            }
            apiDocParams.add(apiDocParam1);
        }
    }

    /**
     * 保存该参数
     *
     * @param apiDocParams
     * @param apiParam
     */
    private void keepParam(List<ApiDocParam> apiDocParams, ApiParam apiParam) {
        ApiDocParam apiDocParam = new ApiDocParam();

        apiDocParam.setName(apiParam.name());
        apiDocParam.setDataType(apiParam.dataType().toString());
        apiDocParam.setDescription(apiParam.description());
        apiDocParam.setDefaultValue(apiParam.defaultValue());
        apiDocParam.setRequired(apiParam.required());
        apiDocParam.setObjectName(apiParam.object());
        apiDocParam.setBelongTo(apiParam.belongTo());

        apiDocParams.add(apiDocParam);
    }

    /**
     * 解析表结构，获取元数据
     *
     * @param paramClass
     * @return
     */
    private Map<String, TableDetail> getTableDetailMap(Class paramClass) {
        Map<String, TableDetail> map = null;
        //从类上的TableName注解中获取与之关联的数据库表名
        Table tableName = (Table) paramClass.getAnnotation(Table.class);
        if (tableName == null) {
            throw new ApiDocException("请检查， " + paramClass.getName() + " 类上没有标注关联数据库表的注解 @TableName");
        }
        String table = tableName.value();
        //根据表名得到数据库中该表的元数据
        try {
            DatabaseMetaDataUtil.init(driver, url, username, password, dataBaseName);
            map = DatabaseMetaDataUtil.getTablesDetails(dataBaseName, table);
        } catch (Exception e) {
            System.err.println("获取表 " + table + " 的元数据失败！");
            e.printStackTrace();
        }
        if (map == null) {
            throw new ApiDocException("请检查， " + table + " 表没有任字段");
        }
        return map;
    }

    /**
     * 解析类的属性
     *
     * @param paramClass
     * @return
     */
    private List<ClassDetail> getApiDocModelList(ApiParam apiParam, Class paramClass) {
        Field[] fields = ReflectUtil.getFields(paramClass);
        final List<ClassDetail> list = new ArrayList<>(fields.length);
        Arrays.stream(fields)
                //去掉序列化字段serialVersionUID
                .filter(m -> !"serialVersionUID".equals(m.getName()))
                //遍历
                .forEach(field -> {
                            ClassDetail classDetail = new ClassDetail();
                            classDetail.setField(field.getName());
                            classDetail.setDataType(getDataType(field.getGenericType().toString()));
                            list.add(classDetail);
                        }
                );

        if (list == null) {
            throw new ApiDocException("请检查， " + paramClass.getName() + " 类没有任何字段");
        }
        return remove(apiParam, list);
    }

    /**
     * 去除类中不用生成文档的属性
     *
     * @param list
     * @return
     */
    private List<ClassDetail> remove(ApiParam apiParam, List<ClassDetail> list) {
        List<ClassDetail> removeList = new ArrayList<>();
        String[] remove = apiParam.remove();
        for (ClassDetail c : list) {
            for (String fileNmae : remove) {
                if (c.getField().equals(fileNmae)) {
                    removeList.add(c);
                }
            }
        }
        list.removeAll(removeList);
        return list;
    }

    /**
     * 获取参数的类型，把java的数据类型转换为前端要的数据类型 具体类型参考枚举类com.qdzklt.apidoc.enumeration.DateType
     *
     * @param s
     * @return
     */
    private String getDataType(String s) {
        if (dataTypeMap == null) {
            dataTypeMap = new HashMap<>();
            dataTypeMap.put(byte.class.getName(), "number");
            dataTypeMap.put(short.class.getName(), "number");
            dataTypeMap.put(int.class.getName(), "number");
            dataTypeMap.put(long.class.getName(), "number");
            dataTypeMap.put(float.class.getName(), "number");
            dataTypeMap.put(double.class.getName(), "number");
            dataTypeMap.put(Byte.class.getName(), "number");
            dataTypeMap.put(Short.class.getName(), "number");
            dataTypeMap.put(Integer.class.getName(), "number");
            dataTypeMap.put(Long.class.getName(), "number");
            dataTypeMap.put(Float.class.getName(), "number");
            dataTypeMap.put(Double.class.getName(), "number");
            dataTypeMap.put(BigDecimal.class.getName(), "number");
            dataTypeMap.put(BigInteger.class.getName(), "number");

            dataTypeMap.put(char.class.getName(), "string");
            dataTypeMap.put(Character.class.getName(), "string");
            dataTypeMap.put(String.class.getName(), "string");

            dataTypeMap.put(boolean.class.getName(), "boolean");
            dataTypeMap.put(Boolean.class.getName(), "boolean");

            dataTypeMap.put(Date.class.getName(), "date");
        }
        s = s.replace("class", "").trim();
        return StringUtil.isNotBlank(dataTypeMap.get(s)) ? dataTypeMap.get(s) : "object";
    }

    /**
     * 将list数据转换为tree结构数据
     *
     * @param apiDocParams
     * @return
     */
    private List<ApiDocParam> list2Json(List<ApiDocParam> apiDocParams) {
        if (null == apiDocParams || apiDocParams.size() == 0) {
            return null;
        }
        List<ApiDocParam> trees = new ArrayList<>();

        for (ApiDocParam treeNode : apiDocParams) {

            if ("0".equals(treeNode.getBelongTo())) {
                trees.add(treeNode);
            }

            for (ApiDocParam it : apiDocParams) {
                if (it.getBelongTo().equals(treeNode.getObjectName())) {
                    if (treeNode.getList() == null) {
                        treeNode.setList(new ArrayList<>());
                    }
                    treeNode.getList().add(it);
                }
            }
        }
//        System.out.println(JSON.toJSONString(trees));
        return trees;
    }
}
