package com.example.covid_19_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// 开启对定时任务的支持
@EnableScheduling
@SpringBootApplication
@MapperScan("com.example.covid_19_backend.mapper")
public class Covid19BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Covid19BackendApplication.class, args);
    }

}
