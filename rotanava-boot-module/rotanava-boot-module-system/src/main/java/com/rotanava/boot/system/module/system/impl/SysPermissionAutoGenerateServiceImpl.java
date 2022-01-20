package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rotanava.boot.system.api.ManagePermissionService;
import com.rotanava.boot.system.api.SysPermissionAutoGenerateService;
import com.rotanava.boot.system.api.module.constant.PageType;
import com.rotanava.boot.system.api.module.constant.VisibleToAllType;
import com.rotanava.boot.system.api.module.system.dto.AddSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.AddSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.vo.SysPagePermissionVO;
import com.rotanava.boot.system.module.dao.SysPagePermissionMapper;
import com.rotanava.framework.model.BaseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-11-23 17:25
 */
@Slf4j
@DubboService
@Service
public class SysPermissionAutoGenerateServiceImpl implements SysPermissionAutoGenerateService {

    @Autowired
    private ManagePermissionService managePermissionService;

    @Autowired
    private SysPagePermissionMapper sysPagePermissionMapper;


    @Override
    public void addSysPagePermission(String checkTag, int sysPageId, String pathMapStr) {

        final AtomicInteger orderCal = new AtomicInteger();

        final Map<String, JSONObject> pathMap = JSON.parseObject(pathMapStr, Map.class);

        pathMap.forEach((k, v) -> {

            JSONObject operation = null;
            int apiMethod = 0;

            final JSONObject delete = v.getJSONObject("delete");
            final JSONObject get = v.getJSONObject("get");
            final JSONObject post = v.getJSONObject("post");
            final JSONObject put = v.getJSONObject("put");

            if (delete != null) {
                operation = delete;
                apiMethod = 4;
            } else if (get != null) {
                operation = get;
                apiMethod = 1;
            } else if (post != null) {
                operation = post;
                apiMethod = 2;
            } else if (put != null) {
                operation = put;
                apiMethod = 3;
            }
            // 91 72
            if (operation != null) {
                final List<String> tags = operation.getJSONArray("tags").toJavaList(String.class);
                final String tag = tags.get(0);
                if (checkTag.equals(tag)) {
                    final String summary = operation.getString("summary");
                    try {
                        final BaseVO<SysPagePermissionVO> sysPagePermission = managePermissionService.getSysPagePermission(sysPageId);
                        Integer parentSysPageId = sysPagePermissionMapper.findIdByPageUrl(k);


                        if (parentSysPageId == null) {
                            final AddSysPagePermissionDTO addSysPagePermissionDTO = new AddSysPagePermissionDTO();
                            addSysPagePermissionDTO.setParentPageId(sysPageId);
                            addSysPagePermissionDTO.setPageTitle(summary);
                            addSysPagePermissionDTO.setSysPageModuleTypeId(sysPagePermission.getData().getSysPageModuleTypeId());
                            addSysPagePermissionDTO.setPageDescription(summary);
                            addSysPagePermissionDTO.setPageShow(1);
                            addSysPagePermissionDTO.setPageStatus(1);
                            addSysPagePermissionDTO.setPageType(PageType.INTERFACE_PERMISSIONS.getType());
                            addSysPagePermissionDTO.setPageCode(k);
                            addSysPagePermissionDTO.setPageUrl(k);
                            addSysPagePermissionDTO.setIsAuto(0);
                            addSysPagePermissionDTO.setPageRedirect("/");
                            addSysPagePermissionDTO.setAbilityType(Convert.toInt(operation.getJSONObject("vendorExtensions").get("operateType")));
                            addSysPagePermissionDTO.setSort(orderCal.incrementAndGet());
                            addSysPagePermissionDTO.setVisibleToAll(VisibleToAllType.NOT_VISIBLE.getType());
                            parentSysPageId = managePermissionService.saveSysPagePermission(addSysPagePermissionDTO, 1);
                        }

                        if (managePermissionService.countByApiUrl(k) <= 0) {
                            final AddSysApiPermissionDTO sysApiPermissionDTO = new AddSysApiPermissionDTO();
                            sysApiPermissionDTO.setSysPageId(parentSysPageId);
                            sysApiPermissionDTO.setApiUrl(k);
                            sysApiPermissionDTO.setApiCode(k);
                            sysApiPermissionDTO.setApiAuthType(1);
                            sysApiPermissionDTO.setApiName(summary);
                            sysApiPermissionDTO.setApiMethod(apiMethod);
                            managePermissionService.saveSysApiPermission(sysApiPermissionDTO, 1);
                        }
                        log.info("插入{}成功 url={}", summary, k);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("接口 {} 添加失败，原因是{} \n", summary, e.getMessage());
                    }
                }
            }
        });

    }
}