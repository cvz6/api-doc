package com.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApidocApplication {

    private static String port;
    private static String host;

    @Value("${server.port}")
    public void setPort(String port) {
        ApidocApplication.port = port;
    }

    @Value("${cloud.host}")
    public void setHost(String host) {
        ApidocApplication.host = host;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApidocApplication.class, args);
        //输出服务地址url，方便开发调试
        printServerUrl();
    }

    private static void printServerUrl() {

        String url = String.format("http://%s%s%s", host + ":", port, "/index.html");

        System.err.println("-----------------------------------------------------------------");
        System.err.println();
        System.err.println("##### 程序启动成功！");
        System.err.println();
        System.err.println("##### 点击该服务地址打开浏览器 : " + url);
        System.err.println();
        System.err.println("-----------------------------------------------------------------");

    }
}
