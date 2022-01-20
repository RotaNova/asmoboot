package com.rotanava.boot.system.websocket.config;

import com.rotanava.face.core.config.SpringStartListener;
import com.rotanava.face.core.global.ThreadPoolUtil;
import com.rotanava.face.core.websocket.server.ServerBootStrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class InitInfo extends SpringStartListener {

    @Autowired
    ServerBootStrap serverBootStrap;


    @Override
    public void start(ConfigurableApplicationContext applicationContext) {
        ThreadPoolUtil.execute(() -> serverBootStrap.run());
        log.info("项目启动成功");
    }

}
