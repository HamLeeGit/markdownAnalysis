package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;


@ComponentScan("com.example.springboot")//启用组件扫描
@SpringBootApplication//开启组件扫描和自动配置
@Configuration  //表明该类使用spring基于java的配置
@CrossOrigin //主要用于跨域问题
//@EnableAutoConfiguration //开启spring boot自动配置
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);//负责启动引导应用程序
    }

}
