package com.atguigu.serviceedu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 导入以下包，程序报找不到此注解的错误，所以干脆不写
//@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.atguigu"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run( EduApplication.class, args);
    }
}
