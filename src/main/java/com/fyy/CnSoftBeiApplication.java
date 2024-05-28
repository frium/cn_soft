package com.fyy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.fyy.mapper")
public class CnSoftBeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnSoftBeiApplication.class, args);
        System.out.println("IDEA启动!");
    }

}
