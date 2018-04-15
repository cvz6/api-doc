package com.apidoc.utis;

import cn.hutool.core.util.StrUtil;
import com.apidoc.bean.TableDetail;
import com.apidoc.utis.utils.JsonUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc获取数据库的元数据信息
 */
public class DatabaseMetaDataUtil {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static String dataBaseName;
    private static String tableName;

//    static {
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 初始化
     * @param driver
     * @param url1
     * @param username1
     * @param password1
     * @param dataBaseName1
     */
    public static void init(String driver, String url1, String username1, String password1,
                            String dataBaseName1) {
        //初始化数据库连接信息
        url = url1;
        username = username1;
        password = password1;
        dataBaseName = dataBaseName1;

        //加载数据连接驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("装载数据库连接驱动失败，url:  " + driver);
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库中的表名称与视图名称
     *
     * @return
     */
    public static List getTablesAndViews(String dataBaseName) throws Exception {
        Connection conn = DriverManager.getConnection(url, username, password);
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            rs = metaData.getTables(null, dataBaseName, null, new String[]{"TABLE", "VIEW"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                list.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    /**
     * 利用表名和数据库用户名查询出该表对应的字段类型
     */
    public static Map<String, TableDetail> getTablesDetails(String dataBaseName, String tableName) throws Exception {
        Connection conn = DriverManager.getConnection(url, username, password);
        ResultSet rs = null;
        Map<String, TableDetail> map = null;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            rs = metaData.getColumns(null, dataBaseName, tableName, null);
            map = new HashMap<>();
            ResultSetMetaData metaData1 = rs.getMetaData();
            while (rs.next()) {
                //    "TABLE_CAT": "所属数据库名",
                //    "BUFFER_LENGTH": "缓存长度",
                //    "IS_NULLABLE": "是否可以为空YES/NO",
                //    "TABLE_NAME": "所属表名",
                //    "COLUMN_DEF": "默认值",
                //    "COLUMN_NAME": "字段名",
                //    "NULLABLE": "是否为空 0/1",
                //    "REMARKS": "注释",
                //    "DECIMAL_DIGITS": "0",
                //    "NUM_PREC_RADIX": "10",
                //    "SQL_DATETIME_SUB": "0",
                //    "IS_GENERATEDCOLUMN": "NO",
                //    "IS_AUTOINCREMENT": "NO",
                //    "SQL_DATA_TYPE": "0",
                //    "ORDINAL_POSITION": "10",
                //    "DATA_TYPE": "5",
                //    "TYPE_NAME": "SMALLINT",
                //    "COLUMN_SIZE": "5"
                TableDetail tableMetaData = new TableDetail();
//                tableMetaData.setField(rs.getString("COLUMN_NAME"));
                tableMetaData.setField(StrUtil.toCamelCase(rs.getString("COLUMN_NAME")));//字段名下划线转驼峰形式
                tableMetaData.setDefaultValue(rs.getString("COLUMN_DEF"));
                tableMetaData.setRequire(rs.getString("IS_NULLABLE"));
//                tableMetaData.setRequire(rs.getString("NULLABLE"));
                tableMetaData.setComment(rs.getString("REMARKS"));
                map.put(tableMetaData.getField(), tableMetaData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return map;
    }


    //测试
    public static void main(String[] args) {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://123.56.45.9:3306/cloud?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8";
        username = "zklt";
        password = "zklt";
        dataBaseName = "cloud";
        tableName = "acc_blog";
        try {
            init(driver,url,username,password,dataBaseName);
            //获取某数据库的所有表和试图信息
            List tablesAndViews = getTablesAndViews(dataBaseName);
            System.err.println(JsonUtil.toString(tablesAndViews));

            //获取某表的元数据
            Map<String, TableDetail> tablesDetails = getTablesDetails(null, tableName);
            System.err.println(JsonUtil.toString(tablesDetails));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
