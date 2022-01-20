package com.rotanava.dingding.sdk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiAttendanceGetsimplegroupsRequest;
import com.dingtalk.api.request.OapiAttendanceGroupMemberusersListRequest;
import com.dingtalk.api.request.OapiAttendanceGroupMinimalismListRequest;
import com.dingtalk.api.request.OapiAttendanceGroupsIdtokeyRequest;
import com.dingtalk.api.request.OapiAttendanceListRecordRequest;
import com.dingtalk.api.request.OapiAttendanceListRequest;
import com.dingtalk.api.request.OapiAttendanceRecordUploadRequest;
import com.dingtalk.api.request.OapiAttendanceShiftListRequest;
import com.dingtalk.api.request.OapiAttendanceShiftQueryRequest;
import com.dingtalk.api.response.OapiAttendanceGetsimplegroupsResponse;
import com.dingtalk.api.response.OapiAttendanceGroupMemberusersListResponse;
import com.dingtalk.api.response.OapiAttendanceGroupMinimalismListResponse;
import com.dingtalk.api.response.OapiAttendanceGroupsIdtokeyResponse;
import com.dingtalk.api.response.OapiAttendanceListRecordResponse;
import com.dingtalk.api.response.OapiAttendanceListResponse;
import com.dingtalk.api.response.OapiAttendanceRecordUploadResponse;
import com.dingtalk.api.response.OapiAttendanceShiftListResponse;
import com.dingtalk.api.response.OapiAttendanceShiftQueryResponse;
import com.rotanava.dingding.aspect.annotation.DingTalk;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.util.Date8Util;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-12-17 10:58
 */
@Component
public class AttendanceSDK {

    /**
     * 功能: 获取班次摘要信息      https://developers.dingtalk.com/document/app/enterprise-shift-query-in-batches
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceShiftListResponse getShiftList(String opUserId, Long cursor, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/shift/list");
            OapiAttendanceShiftListRequest req = new OapiAttendanceShiftListRequest();
            req.setOpUserId(opUserId);
            req.setCursor(cursor);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }

    /**
     * 功能: 获取班次详情  https://developers.dingtalk.com/document/app/shift-query
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceShiftQueryResponse getShiftInfo(String opUserId, Long shiftId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/shift/query");
            OapiAttendanceShiftQueryRequest req = new OapiAttendanceShiftQueryRequest();
            req.setOpUserId(opUserId);
            req.setShiftId(shiftId);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }


    //分组接口

    /**
     * 功能: 批量获取考勤组摘要
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceGroupMinimalismListResponse getAttendanceGroupPageList(Long cursor, String accessToken, String opUserId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/group/minimalism/list");
            OapiAttendanceGroupMinimalismListRequest req = new OapiAttendanceGroupMinimalismListRequest();
            req.setCursor(cursor);
            req.setOpUserId(opUserId);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }


    /**
     * 功能: 批量获取考勤组详情
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceGetsimplegroupsResponse getSimpleGroups(Long offset, Long size, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/getsimplegroups");
            OapiAttendanceGetsimplegroupsRequest req = new OapiAttendanceGetsimplegroupsRequest();
            req.setOffset(offset);
            req.setSize(size);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }


    /**
     * 功能: 获取考勤组员工的userid    https://developers.dingtalk.com/document/app/queries-attendance-group-list-details
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceGroupMemberusersListResponse getAttendanceGroupUsers(Long cursor, String opUserId, Long groupId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/group/memberusers/list");
            OapiAttendanceGroupMemberusersListRequest req = new OapiAttendanceGroupMemberusersListRequest();
            req.setCursor(cursor);
            req.setOpUserId(opUserId);
            req.setGroupId(groupId);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }


    /**
     * 功能: groupId转换为groupKey    https://developers.dingtalk.com/document/app/groupid-to-groupkey
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceGroupsIdtokeyResponse getGroupKeyByGroupId(String opUserId, Long groupId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/groups/idtokey");
            OapiAttendanceGroupsIdtokeyRequest req = new OapiAttendanceGroupsIdtokeyRequest();
            req.setOpUserId(opUserId);
            req.setGroupId(groupId);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }


    //打卡相关接口

    /**
     * 功能: 获取打卡详情   https://developers.dingtalk.com/document/app/attendance-clock-in-record-is-open
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceListRecordResponse getRecordInfoResult(List<String> userIds, Date checkDateFrom, Date checkDateTo, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/listRecord");
            OapiAttendanceListRecordRequest req = new OapiAttendanceListRecordRequest();
            req.setUserIds(userIds);
            req.setCheckDateFrom(Date8Util.format(checkDateFrom, Date8Util.DATE_TIME));
            req.setCheckDateTo(Date8Util.format(checkDateTo, Date8Util.DATE_TIME));
            req.setIsI18n(false);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }

    /**
     * 功能: 获取打卡结果   https://developers.dingtalk.com/document/app/open-attendance-clock-in-data
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     */
    @DingTalk
    public OapiAttendanceListResponse getRecordResult(List<String> userIds, Date checkDateFrom, Date checkDateTo, Long offset, Long limit, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/list");
            OapiAttendanceListRequest req = new OapiAttendanceListRequest();
            req.setWorkDateFrom(Date8Util.format(checkDateFrom, Date8Util.DATE_TIME));
            req.setWorkDateTo(Date8Util.format(checkDateTo, Date8Util.DATE_TIME));
            req.setUserIdList(userIds);
            req.setOffset(offset);
            req.setLimit(limit);
            req.setIsI18n(false);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }

    /**
     * 功能: 上传打卡记录   https://developers.dingtalk.com/document/app/upload-punch-records
     * 作者: zjt
     * 日期: 2021/10/25 14:47
     * 版本: 1.0
     *
     * @return
     */
    @DingTalk
    public OapiAttendanceRecordUploadResponse uploadPunchRecords(String userId, String deviceName, String deviceId, String photoUrl, Long userCheckTime, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/record/upload");
            OapiAttendanceRecordUploadRequest req = new OapiAttendanceRecordUploadRequest();
            req.setUserid(userId);
            req.setDeviceName(deviceName);
            req.setDeviceId(deviceId);
            req.setPhotoUrl(photoUrl);
            req.setUserCheckTime(userCheckTime);
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new CommonException(new ErrorCode(12001, e.getErrMsg()));
        }
    }


}