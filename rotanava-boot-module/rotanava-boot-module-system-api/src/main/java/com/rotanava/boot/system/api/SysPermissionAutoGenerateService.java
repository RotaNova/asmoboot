package com.rotanava.boot.system.api;

import io.swagger.models.Path;

import java.util.Map;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-11-23 17:24
 */
public interface SysPermissionAutoGenerateService {


    /**
     * 功能: 添加页面权限
     * 作者: zjt
     * 日期: 2021/3/29 15:32
     * 版本: 1.0
     */
    void addSysPagePermission(String checkTag, int sysPageId, String pathMapStr);

}
