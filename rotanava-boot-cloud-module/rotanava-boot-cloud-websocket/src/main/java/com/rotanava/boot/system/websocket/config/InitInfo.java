package com.rotanava.boot.system.websocket.config;

import com.rotanava.boot.system.websocket.thread.ThreadPoolUtil;

import com.rotanava.face.core.websocket.server.ServerBootStrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class InitInfo implements ApplicationRunner {

    @Autowired
    ServerBootStrap serverBootStrap;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadPoolUtil.execute(() -> serverBootStrap.run());
        log.info("websocket项目启动成功");
    }



}
