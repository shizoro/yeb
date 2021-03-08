package com.xxxx.yeb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动项目
 * @author arthur
 * @date 2021/3/8 12:51
 */
@SpringBootApplication
@MapperScan("com.xxxx.yeb.mapper")
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class,args);
    }
}
