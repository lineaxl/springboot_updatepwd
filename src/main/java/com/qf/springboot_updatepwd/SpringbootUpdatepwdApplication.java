package com.qf.springboot_updatepwd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.qf.dao")
@SpringBootApplication(scanBasePackages = "com.qf")
public class SpringbootUpdatepwdApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootUpdatepwdApplication.class, args);
    }

}
