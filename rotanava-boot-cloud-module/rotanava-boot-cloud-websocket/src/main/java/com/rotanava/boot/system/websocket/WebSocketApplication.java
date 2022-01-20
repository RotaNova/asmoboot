package com.rotanava.boot.system.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-07 14:36
 **/
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.rotanava.*")
@EnableRetry
public class WebSocketApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WebSocketApplication.class, args);
    }
}
