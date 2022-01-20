package com.rotanava.boot.system.websocket.service;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: richenLi
 * @create: 2020-09-04 10:51
 **/
public interface RemainPageService {


    /**
     * @description : 更新用户停留页面状态
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    void updatePageStatus(String token, Integer pageId);

    /**
     * @description : 获取用户当前所在的页面
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    Integer getRemainPageByToken(String token);

    /**
     * @description : 验证用户是否在某页面
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    boolean inPageByToken(Integer pageId, String token);

    public static void main(String[] args) {
        for (int i = 0; i <10000 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <100 ; j++) {
                        new Thread(new Runnable() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                System.out.println("sleep");
                                TimeUnit.SECONDS.sleep(1);
                            }
                        }).start();
                    }
                }
            }).start();
        }
    }



}
