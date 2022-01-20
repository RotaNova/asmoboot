package com.rotanava.boot.dbapi;

import com.rotanava.framework.config.mybatis.MybatisPlusConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@ComponentScan("com.rotanava.*")
@MapperScan(basePackages = {"com.rotanava.framework.module.dao", "com.rotanava.boot.dbapi.dao"})
@EnableRabbit
@EnableDubbo(scanBasePackages = "com.rotanava.*")
@DubboComponentScan(basePackages = {"com.rotanava.*"})
public class DBApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DBApiApplication.class, args);
    }

}
