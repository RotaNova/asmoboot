package com.rotanava.boot.system.websocket.service.impl;

import com.rotanava.boot.system.websocket.service.RemainPageService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @description:
 * @author: richenLi
 * @create: 2020-09-04 11:21
 **/
@Service
public class RemainPageServiceImpl implements RemainPageService {

    //用户停留记录
    private static HashMap<String,Integer> remainPageMap=new HashMap<>();

    @Override
    public synchronized void updatePageStatus(String token, Integer pageId) {
        remainPageMap.put(token,pageId);
    }

    @Override
    public Integer getRemainPageByToken(String token) {
        return remainPageMap.get(token);
    }

    @Override
    public boolean inPageByToken(Integer pageId, String token) {
        Integer remainPage = remainPageMap.get(token);

        if (remainPage!=null&& remainPage.equals(pageId)){
            return true;
        }
        return false;
    }
}
