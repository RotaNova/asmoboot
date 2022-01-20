package com.rotanava.boot.system.websocket.config;

import com.rotanava.boot.system.websocket.bean.UserClientBean;
import com.rotanava.face.core.util.StringUtil;
import com.rotanava.face.core.websocket.iface.SocketClient;
import com.rotanava.face.core.websocket.server.ClientManage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: richenLi
 * @create: 2020-05-11 16:21
 **/
public class SocketClientManage {

    private static final ConcurrentHashMap<String, UserClientBean> map = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, UserClientBean> getMap() {
        return map;
    }

    public static Set<String> getUserClientById(String userId){
        Set<String> set = new HashSet<>();
        for (Map.Entry<String, UserClientBean> userClientBeanEntry : map.entrySet()) {
            if (userId.equals(userClientBeanEntry.getValue().getUserId())){
                set.add(userClientBeanEntry.getKey());
            }

        }
        return set;
    }

    public static void put(String token, UserClientBean userClientBean) {
        map.put(token, userClientBean);
    }

    public static SocketClient get(String token) {
        UserClientBean userClientBean = map.get(token);
        if (!StringUtil.isNullOrEmpty(userClientBean)) {
            return ClientManage.getClient(userClientBean.getSession());
        }
        return null;
    }

    /**
     * @description : 获取所有在线的用户
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public static List<SocketClient> getAllClient() {
        List<SocketClient> clients = new ArrayList<>();
        for (String token : map.keySet()) {
            UserClientBean userClientBean = map.get(token);
            clients.add(ClientManage.getClient(userClientBean.getSession()));
        }
        return clients;
    }


    public static List<String> getAllToken() {
        List<String> tokenList = new ArrayList<>();
        for (String token : map.keySet()) {
            tokenList.add(token);
        }
        return tokenList;
    }


    public static boolean containsToken(String token) {
        return map.containsKey(token);
    }

    public static void rm(SocketClient client) {
       String session =  client.getSession();
        Set<Map.Entry<String, UserClientBean>> setMiddle = map.entrySet();
        setMiddle.removeIf(stringStringEntry -> stringStringEntry.getValue().getSession().equals(session));

    }
}
