package com.rotanava.dingding.sdk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentGetRequest;
import com.dingtalk.api.request.OapiV2DepartmentListparentbydeptRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.dingtalk.api.response.OapiV2DepartmentListparentbydeptResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.rotanava.dingding.aspect.annotation.DingTalk;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-10-27 10:46
 */
@Component
public class DepartmentSDK {

    /**
     * 功能: 获取指定部门的所有父部门列表    https://developers.dingtalk.com/document/app/query-the-list-of-all-parent-departments-of-a-department
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiV2DepartmentListparentbydeptResponse getDeptListParent(Long deptId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listparentbydept");
            OapiV2DepartmentListparentbydeptRequest req = new OapiV2DepartmentListparentbydeptRequest();
            req.setDeptId(deptId);
            OapiV2DepartmentListparentbydeptResponse rsp = client.execute(req, accessToken);
            return rsp;
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }

    /**
     * 功能: 获取部门详情    https://developers.dingtalk.com/document/app/query-department-details0-v2
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiV2DepartmentGetResponse getDeptInfo(Long deptId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
            OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
            req.setDeptId(deptId);
            req.setLanguage("zh_CN");
            OapiV2DepartmentGetResponse rsp = client.execute(req, accessToken);
            return rsp;
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }

    /**
     * 功能: 获取部门列表    https://developers.dingtalk.com/document/app/obtain-the-department-list-v2
     * 作者: zjt
     * 日期: 2021/10/25 15:55
     * 版本: 1.0
     */
    @DingTalk
    public OapiV2DepartmentListsubResponse getDeptList(Long deptId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
            OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
            req.setDeptId(deptId);
            req.setLanguage("zh_CN");
            OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
            return rsp;
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }

}