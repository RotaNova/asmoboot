package com.rotanava.boot.system.websocket.test;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-08 15:15
 **/
@SpringBootTest
public class WebSocketTest {
        @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Test
    public void testRedis(){
//        //扫描符合条件的token scan 来扫 要去重
//
//        String pattern = String.format("%s:%s*", "loginUserIds", 1);
//
//        Set<String> tokens =  redisTemplate.keys(pattern);


        //扫描符合条件的token scan 来扫 要去重
        RKeys keys = redissonClient.getKeys();
        String pattern = String.format("%s:%s*", "loginUserIds", 1);
        Set<String> tokens = Sets.newHashSet();

        Set<String> loginTokens = keys.getKeysStreamByPattern(pattern, Integer.MAX_VALUE).collect(Collectors.toSet());
        loginTokens.forEach(token -> {
            tokens.add(token.substring(token.lastIndexOf(":") + 1));
        });
               System.out.println(tokens);
    }
}
