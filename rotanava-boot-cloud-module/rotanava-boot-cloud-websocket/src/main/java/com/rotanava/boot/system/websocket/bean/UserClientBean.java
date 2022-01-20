package com.rotanava.boot.system.websocket.bean;

import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-01-12 11:33
 **/
@Data
public class UserClientBean {

    /**
     * token
     */
    private String token;


    /**
     * 区别用户的唯一id
     */
    private String userId;


    /**
     * socketclient对象的session
     */
    private String session;







}
