package com.rotanava.boot.system;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: 基础系统启动类
 * @author: richenLi
 * @create: 2021-03-01 16:00
 **/
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.rotanava.*")
@MapperScan(basePackages = {"com.rotanava.framework.module.dao", "com.rotanava.boot.system.module.dao"})
@ServletComponentScan(basePackages = "com.rotanava.boot.system.module.system.filter")
@EnableRabbit
@EnableDubbo(scanBasePackages = "com.rotanava.*")
@DubboComponentScan(basePackages = {"com.rotanava.*"})
@EnableAsync
public class SystemApplication {



    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(SystemApplication.class, args);
    }

}
