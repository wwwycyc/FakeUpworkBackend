package com.wyc.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.wyc")
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@MapperScan("com.wyc.server.mapper")
public class FakeupworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(FakeupworkApplication.class, args);
        log.info("server started");
    }
}
