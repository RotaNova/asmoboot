package com.rotanava.boot.system.websocket.handler;


import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rotanava.boot.system.websocket.bean.UserClientBean;
import com.rotanava.boot.system.websocket.config.SocketClientManage;

import com.rotanava.face.core.websocket.annotation.SocketHandler;
import com.rotanava.face.core.websocket.iface.HandlerAction;
import com.rotanava.face.core.websocket.iface.SocketClient;
import com.rotanava.face.core.websocket.model.ActionMsg;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

/**
 * @description: socket连接
 * @author: richenLi
 * @create: 2020-05-11 16:41
 **/
@Log4j2
@SocketHandler(handlerName = "connect")
public class ConnectHandler implements HandlerAction {


    @Override
    public void action(ActionMsg msg) {
        //连接
        String token = msg.getData().getString("token");

        SocketClient client = msg.getSocketClient();
        //告诉客户端端登录成功
        SocketClient tokenClient = SocketClientManage.get(token);

        if (tokenClient!=null){
            tokenClient.disconnect();
        }else {
            UserClientBean userClientBean = new UserClientBean();
            userClientBean.setToken(token);
            userClientBean.setSession(client.getSession());
            userClientBean.setUserId(getUserId(token).toString());

            SocketClientManage.put(token, userClientBean);
            HashMap<String,Object> result = new HashMap<>();
            result.put("code",200);
            result.put("msg","连接成功");
            client.send("connect", result);
        }


    }

    public static Integer getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asInt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
