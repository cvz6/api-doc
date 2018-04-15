package com.apidoc.utis.utils;

import cn.hutool.core.util.StrUtil;

import java.util.UUID;

/**
 * @Description: 字符串工具类
 * <p>
 * 字符串相关的工具类都要使用这个，方便项目统一管理和维护<br>
 * <p>
 * 即便是调用spring的方法也要在这个类封装一次
 * </p>
 * @Author: admin
 * @CreateDate: 2018/1/11 11:58
 */
public class StringUtil {

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * 字符串含空白
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return StrUtil.hasBlank(str);
    }

    /**
     * 字符串不含空白
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !StrUtil.hasBlank(str);
    }

    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
