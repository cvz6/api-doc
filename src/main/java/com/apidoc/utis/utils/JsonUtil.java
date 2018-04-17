package com.apidoc.utis.utils;

import com.alibaba.fastjson.JSON;

/**
 * @Description: json工具类
 * @Author: peng.liu
 * @CreateDate: 2018/1/19 11:40
 */
public class JsonUtil {

    /**
     * 把对象转换成json字符串
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        return JSON.toJSONString(object);
    }
}
