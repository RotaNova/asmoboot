package com.rotanava.boot.system.websocket.config;

import com.rotanava.face.core.websocket.iface.SocketClient;
import com.rotanava.face.core.websocket.iface.SocketConnect;
import com.rotanava.face.core.websocket.model.SocketData;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: richenLi
 * @create: 2020-05-11 15:05
 **/
@Configuration
@Log4j2
public class ClientCfg {


    @Bean(name = "socketConnect")
    public SocketConnect socketConnect() {
        SocketConnect socketConnect = new SocketConnect() {
            @Override
            public boolean valid(SocketData socketData) {
                return true;
            }


            @Override
            public boolean connect(SocketClient socketClient) {
                log.info("【websocket新用户连接,session为{}】",socketClient.getSession());
                return true;
            }

            @Override
            public void disconnect(SocketClient socketClient) {
                SocketClientManage.rm(socketClient);
            }

            @Override
            public void before(SocketClient socketClient, SocketData socketData) {
            }

            @Override
            public void after(SocketClient socketClient, SocketData socketData) {
            }
        };
        return socketConnect;
    }
}
