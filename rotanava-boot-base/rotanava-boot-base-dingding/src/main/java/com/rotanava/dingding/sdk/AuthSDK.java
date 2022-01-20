package com.rotanava.dingding.sdk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserListadminRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserListadminResponse;
import com.rotanava.dingding.aspect.annotation.DingTalk;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

/**
 * @description: 授权相关
 * @author: jintengzhou
 * @date: 2021-10-25 15:55
 */
@Component
public class AuthSDK {
    
    /**
     * 功能: 根据sns临时授权码获取用户信息
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiSnsGetuserinfoBycodeResponse getUserInfo(String code, String appkey, String appsecret) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
            OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
            req.setTmpAuthCode(code);
            return client.execute(req, appkey, appsecret);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }
    
    
    /**
     * 功能: 获取token
     * 作者: zjt
     * 日期: 2021/10/26 17:14
     * 版本: 1.0
     */
    @DingTalk
    public OapiGettokenResponse getAccessToken(String appkey, String appsecret) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(appkey);
            req.setAppsecret(appsecret);
            req.setHttpMethod("GET");
            return client.execute(req);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }
    
    
    /**
     * 功能: 获取管理员列表
     * 作者: zjt
     * 日期: 2021/10/26 17:52
     * 版本: 1.0
     */
    @DingTalk
    public OapiUserListadminResponse getAdminList(String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listadmin");
            OapiUserListadminRequest req = new OapiUserListadminRequest();
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }
    
    
}