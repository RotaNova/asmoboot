package com.rotanava.dingding.sdk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRoleListRequest;
import com.dingtalk.api.response.OapiRoleListResponse;
import com.rotanava.dingding.aspect.annotation.DingTalk;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-12-17 11:44
 */
@Component
public class RoleSDK {

    /**
     * 功能: 获取角色列表   https://open.dingtalk.com/document/orgapp-server/obtains-a-list-of-enterprise-roles
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiRoleListResponse getRoleList(Long size, Long offset, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
            OapiRoleListRequest req = new OapiRoleListRequest();
            req.setSize(20L);
            req.setOffset(0L);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }

}