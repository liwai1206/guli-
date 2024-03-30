package com.atguigu.cmsservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan(basePackages = {"com.atguigu.cmsservice.mapper"})
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run( CmsApplication.class, args);
    }
}