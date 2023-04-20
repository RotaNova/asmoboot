package com.rotanava.boot.system.api;

import cn.hutool.core.lang.tree.Tree;
import com.rotanava.boot.system.api.module.constant.SysPermissionType;
import com.rotanava.boot.system.api.module.system.bean.PermissionListAndId;
import com.rotanava.boot.system.api.module.system.bo.SysPagePermission;
import com.rotanava.boot.system.api.module.system.vo.SysApiChooseVO;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-10 10:47
 */
@Validated
public interface SysPageAndApiService {

    /**
     * 功能: 获取菜单资源树
     * 作者: zjt
     * 日期: 2021/3/10 10:07
     * 版本: 1.0
     */
    List<Tree<Integer>> getSysPageTree(SysPermissionType sysPermissionType, int relationId, int sysPageModuleTypeId);

    /**
     * 功能: 获取接口级权限
     * 作者: zjt
     * 日期: 2021/3/10 11:09
     * 版本: 1.0
     */
    List<SysApiChooseVO> getSysApiPermission(SysPermissionType sysPermissionType, int relationId, int sysPageId);

    /**
     * 功能: 保存系统权限配置 页面级别
     * 作者: zjt
     * 日期: 2021/3/10 14:19
     * 版本: 1.0
     */
    void saveSysPermission(SysPermissionType sysPermissionType, int relationId, int sysPageModuleTypeId, Set<Integer> sysPageIdList);

    /**
     * 功能: 保存系统权限配置 api级别
     * 作者: zjt
     * 日期: 2021/4/16 13:47
     * 版本: 1.0
     */
    void saveSysPermissionApi(SysPermissionType sysPermissionType, int sysRoleId, int sysPageModuleTypeId, int sysParentPageId, Set<Integer> sysPageIdList);

    void saveSysPermissionApi(SysPermissionType sysPermissionType, int sysRoleId, List<SysPagePermission> sysPagePermissionList, int sysParentPageId, Set<Integer> sysPageIdList, PermissionListAndId permissionListAndId);
}
