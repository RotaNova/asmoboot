package com.rotanava.dingding.service;

import com.dingtalk.api.response.OapiGettokenResponse;
import com.rotanava.dingding.sdk.AuthSDK;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-10-26 17:12
 */
@Component
public class DingTalkService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private AuthSDK authSDK;


    /**
     * 功能: 获取accessToken
     * 作者: zjt
     * 日期: 2021/10/26 17:12
     * 版本: 1.0
     */
    public String getAccessToken(String appkey, String appsecret) {
        final RBucket<String> ddAccessToken = redissonClient.getBucket(String.format("ddAccessToken:%s:%s", appkey, appsecret));
        String accessToken = ddAccessToken.get();

        if (StringUtils.isBlank(accessToken)) {
            final OapiGettokenResponse oapiGettokenResponse = authSDK.getAccessToken(appkey, appsecret);
            final Long expiresIn = oapiGettokenResponse.getExpiresIn();
            ddAccessToken.set(oapiGettokenResponse.getAccessToken(), expiresIn - 60 * 16, TimeUnit.SECONDS);
            return oapiGettokenResponse.getAccessToken();
        }

        return accessToken;
    }

}