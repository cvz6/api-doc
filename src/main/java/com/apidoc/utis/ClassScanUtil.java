package com.apidoc.utis;

import com.apidoc.annotation.Api;
import com.apidoc.utis.utils.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Description:
 * @Author: peng.liu
 * @CreateDate: 2018/4/27 15:20
 */
public class ClassScanUtil {

    public static void main(String[] args) {
//        String packageName = "com.demo.web";
        String packageName = "com.demo.web.test.scanjar.com.demo.web";
        Set<Class> set = getClass4Annotation(packageName, Api.class);
        System.out.println(JsonUtil.toString(set));
    }

    /**
     * 扫描指定包路径下所有包含指定注解的类
     *
     * @param packageName 包名
     * @param apiClass    指定的注解
     * @return Set
     */
    public static Set<Class> getClass4Annotation(String packageName, Class<?> apiClass) {
        Set<Class> classSet = new HashSet<>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    File dir = new File(filePath);
                    List<File> fileList = new ArrayList<File>();
                    fetchFileList(dir, fileList);
                    for (File f : fileList) {
                        String fileName = f.getAbsolutePath();
                        if (fileName.endsWith(".class")) {
                            String noSuffixFileName = fileName.substring(8 + fileName.lastIndexOf("classes"), fileName.indexOf(".class"));
                            String filePackage = noSuffixFileName.replaceAll("\\\\", ".");
                            Class clazz = Class.forName(filePackage);
                            if (null != clazz.getAnnotation(apiClass)) {
                                classSet.add(clazz);
                            }
                        }
                    }
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        System.out.println(url);
                        //jar:file:/D:/MyStudy/apidoc/target/apidoc-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/com/demo/web
                        JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                        jar = urlConnection.getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                // 如果是一个.class文件 而且不是目录
                                if (name.endsWith(".class") && !entry.isDirectory()) {
                                    // 去掉后面的".class" 获取真正的类名
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                                    try {
                                        Class aClass = Class.forName(packageName + '.' + className);
                                        if (null != aClass.getAnnotation(apiClass)) {
                                            classSet.add(aClass);
                                        }
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.err.println(JsonUtil.toString(classSet));
        return classSet;
    }


    /**
     * 查找所有的文件
     *
     * @param dir      路径
     * @param fileList 文件集合
     */
    private static void fetchFileList(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : Objects.requireNonNull(dir.listFiles())) {
                fetchFileList(f, fileList);
            }
        } else {
            fileList.add(dir);
        }
    }

}
