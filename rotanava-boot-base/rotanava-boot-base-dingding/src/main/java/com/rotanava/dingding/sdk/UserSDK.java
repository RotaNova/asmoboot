package com.rotanava.dingding.sdk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetbyunionidRequest;
import com.dingtalk.api.request.OapiUserListidRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiUserGetbyunionidResponse;
import com.dingtalk.api.response.OapiUserListidResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.rotanava.dingding.aspect.annotation.DingTalk;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-10-27 10:42
 */
@Component
public class UserSDK {
    
    /**
     * 功能: 获取部门用户userid列表 https://developers.dingtalk.com/document/app/query-the-list-of-department-userids?spm=ding_open_doc.document.0.0.69bb7c98cz8yxb#topic-1960043
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiUserListidResponse getDeptUserList(Long deptId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listid");
            OapiUserListidRequest req = new OapiUserListidRequest();
            req.setDeptId(deptId);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }
    
    /**
     * 功能: 根据userid获取用户详情 https://developers.dingtalk.com/document/app/query-user-details
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiV2UserGetResponse getUserInfo(String userId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            req.setLanguage("zh_CN");
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }
    
    /**
     * 功能: 根据unionid获取用户userid https://open.dingtalk.com/document/orgapp-server/query-a-user-by-the-union-id
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiUserGetbyunionidResponse getUserIdByUnionId(String unionid, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/getbyunionid");
            OapiUserGetbyunionidRequest req = new OapiUserGetbyunionidRequest();
            req.setUnionid(unionid);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }
    
    /**
     * 功能: 获取部门用户详情 https://developers.dingtalk.com/document/app/queries-the-complete-information-of-a-department-user
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiV2UserListResponse getDeptUserPageList(Long deptId, Long cursor, Long size, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
            OapiV2UserListRequest req = new OapiV2UserListRequest();
            req.setDeptId(deptId);
            req.setCursor(cursor);
            req.setSize(size);
            req.setOrderField("custom");
            req.setContainAccessLimit(true);
            req.setLanguage("zh_CN");
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }
    
}