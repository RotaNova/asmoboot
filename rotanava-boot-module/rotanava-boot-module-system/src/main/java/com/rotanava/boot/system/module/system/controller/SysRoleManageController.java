package com.rotanava.boot.system.module.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.DeptRoleManagementService;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.SysPageAndApiService;
import com.rotanava.boot.system.api.SysRoleManagementService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.SysPermissionType;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddSysRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.DeptRoleAuthorizedUserDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.SysRoleAuthorizedUserDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateSysRoleDTO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleInfoVO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleItemVO;
import com.rotanava.boot.system.api.module.system.vo.SysApiChooseVO;
import com.rotanava.boot.system.api.module.system.vo.SysRoleInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysRoleItemVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.boot.system.module.dao.SysDepartRoleMapper;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @description: 角色管理
 * @author: jintengzhou
 * @date: 2021-03-06 16:12
 */
@Api(tags = "角色管理")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/sysRoleManage")
public class SysRoleManageController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private SysRoleManagementService sysRoleManagementService;

    @Autowired
    private DeptRoleManagementService deptRoleManagementService;

    @Autowired
    private SysPageAndApiService sysPageAndApiService;

    @Autowired
    private SysDepartRoleMapper sysDepartRoleMapper;


    //
    // 系统用户
    //

    /**
     * 功能: 分页获取系统授权用户列表
     * 作者: zjt
     * 日期: 2021/3/10 17:12
     * 版本: 1.0
     */
    @PostMapping("getListSysUser")
    @AutoLog(value = "分页获取系统授权用户列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListSysUser(@RequestBody GetListSysUserRoleDTO sysUserDTO) {
        final IPage<SysUserItemVO> listSysUser = sysRoleManagementService.getListSysUser(sysUserDTO);
        return RetData.ok(listSysUser);
    }



    /**
     * 功能: 分页获取系统未授权用户列表
     * 作者: zjt
     * 日期: 2021/3/10 17:12
     * 版本: 1.0
     */
    @PostMapping("getListSysUserNotAuth")
    @AutoLog(value = "分页获取系统授权用户列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListSysUserNotAuth(@RequestBody GetListSysUserDTO sysUserDTO) {
        sysUserDTO.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
        final IPage<SysUserItemVO> listSysUser = sysUserService.getListSysUser(sysUserDTO);
        return RetData.ok(listSysUser);
    }

    /**
     * 功能: 获取系统角色菜单资源
     * 作者: zjt
     * 日期: 2021/3/10 14:41
     * 版本: 1.0
     */
    @GetMapping("getSysRoleSysPageTree")
    @AutoLog(value = "获取系统角色菜单资源", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getSysRoleSysPageTree(@NotNull Integer sysRoleId, @NotNull Integer sysPageModuleTypeId) {
        final List<Tree<Integer>> sysPageTree = sysPageAndApiService.getSysPageTree(SysPermissionType.SYSTEM_ROLE, sysRoleId, sysPageModuleTypeId);
        return RetData.ok(sysPageTree);
    }

    /**
     * 功能: 获取系统角色接口集权限
     * 作者: zjt
     * 日期: 2021/3/10 14:44
     * 版本: 1.0
     */
    @GetMapping("getSysRoleSysApiPermission")
    @AutoLog(value = "获取系统角色接口集权限", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<SysApiChooseVO>> getSysRoleSysApiPermission(@NotNull Integer sysRoleId, @NotNull Integer sysPageId) {
        final List<SysApiChooseVO> sysApiPermission = sysPageAndApiService.getSysApiPermission(SysPermissionType.SYSTEM_ROLE, sysRoleId, sysPageId);
        return RetData.ok(sysApiPermission);
    }

    /**
     * 功能: 保存系统角色系统权限配置 页面级别
     * 作者: zjt
     * 日期: 2021/3/10 14:47
     * 版本: 1.0
     */
    @PostMapping("saveSysPermission")
    @AutoLog(value = "保存系统角色系统权限配置", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData saveSysPermission(@RequestBody SaveSysPermissionDTO saveSysPermissionDTO) {
        sysPageAndApiService.saveSysPermission(SysPermissionType.SYSTEM_ROLE, saveSysPermissionDTO.getSysRoleId(), saveSysPermissionDTO.getSysPageModuleTypeId(), saveSysPermissionDTO.getSysPageIdList());
        return RetData.ok();
    }

    /**
     * 功能: 保存系统角色系统权限配置 api级别
     * 作者: zjt
     * 日期: 2021/3/10 14:47
     * 版本: 1.0
     */
    @PostMapping("saveSysPermissionApi")
    @AutoLog(value = "保存系统角色系统权限配置 api级别", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData saveSysPermissionApi(@RequestBody SaveSysPermissionDTO saveSysPermissionDTO) {
        sysPageAndApiService.saveSysPermissionApi(SysPermissionType.SYSTEM_ROLE, saveSysPermissionDTO.getSysRoleId(), saveSysPermissionDTO.getSysPageModuleTypeId(), saveSysPermissionDTO.getSysParentPageId(), saveSysPermissionDTO.getSysPageIdList());
        return RetData.ok();
    }

    /**
     * 功能: 查询角色列表
     * 作者: zjt
     * 日期: 2021/3/3 17:57
     * 版本: 1.0
     */
    @PostMapping("listSysRoleItem")
    @AutoLog(value = "查询角色列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysRoleItemVO>> listSysRoleItem(@RequestBody BaseDTO baseDTO) {
        final IPage<SysRoleItemVO> sysRoleItemVOIPage = sysRoleManagementService.listSysRoleItem(baseDTO);
        return RetData.ok(sysRoleItemVOIPage);
    }

    /**
     * 功能: 新增系统角色
     * 作者: zjt
     * 日期: 2021/3/3 18:12
     * 版本: 1.0
     */
    @PostMapping("addSysRole")
    @AutoLog(value = "新增系统角色", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData addSysRole(@RequestBody AddSysRoleDTO addRoleDTO) {
        sysRoleManagementService.addSysRole(addRoleDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 编辑系统角色
     * 作者: zjt
     * 日期: 2021/3/3 18:12
     * 版本: 1.0
     */
    @PutMapping("updateSysRole")
    @AutoLog(value = "编辑系统角色", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateSysRole(@RequestBody UpdateSysRoleDTO updateSysRoleDTO) {
        sysRoleManagementService.updateSysRole(updateSysRoleDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 获取系统角色详情
     * 作者: zjt
     * 日期: 2021/3/5 15:45
     * 版本: 1.0
     */
    @GetMapping("getSysRoleInfo")
    @AutoLog(value = "获取系统角色详情", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<SysRoleInfoVO> getSysRoleInfo(@NotNull Integer sysRoleId) {
        final SysRoleInfoVO sysRoleInfo = sysRoleManagementService.getSysRoleInfo(sysRoleId);
        return RetData.ok(sysRoleInfo);
    }

    /**
     * 功能: 系统角色批量删除
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    @PostMapping("deleteSysRole")
    @AutoLog(value = "系统角色批量删除", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteSysRole(@RequestBody Collection<Integer> sysRoleIdList) {
        sysRoleManagementService.deleteSysRole(sysRoleIdList);
        return RetData.ok();
    }

    /**
     * 功能: 系统角色授权用户
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    @PostMapping("sysRoleAuthorizedUser")
    @AutoLog(value = "系统角色授权用户", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData sysRoleAuthorizedUser(@RequestBody SysRoleAuthorizedUserDTO authorizedUserDTO) {
        sysRoleManagementService.sysRoleAuthorizedUser(authorizedUserDTO.getSysRoleId(), authorizedUserDTO.getSysUserIdList());
        return RetData.ok();
    }

    /**
     * 功能: 删除系统角色授权的用户
     * 作者: zjt
     * 日期: 2021/3/10 17:54
     * 版本: 1.0
     */
    @PostMapping("deleteSysRoleAuthorizedUser")
    @AutoLog(value = "删除系统角色授权的用户", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteSysRoleAuthorizedUser(@RequestBody SysRoleAuthorizedUserDTO authorizedUserDTO) {
        sysRoleManagementService.deleteSysRoleAuthorizedUser(authorizedUserDTO.getSysRoleId(), authorizedUserDTO.getSysUserIdList());
        return RetData.ok();
    }

    /**
     * 功能: 系统角色获取部门列表
     * 作者: zjt
     * 日期: 2021/3/11 16:31
     * 版本: 1.0
     */
    @GetMapping("getSysRoleDepartmentList")
    @AutoLog(value = "系统角色获取部门列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getSysRoleDepartmentList(Integer deptId) {
        final List<Tree<Integer>> departmentList = sysDepartmentService.getDepartmentList(deptId);
        return RetData.ok(departmentList);
    }


    //
    //  部门用户
    //

    /**
     * 功能: 部门角色获取部门列表
     * 作者: zjt
     * 日期: 2021/3/11 16:31
     * 版本: 1.0
     */
    @GetMapping("getDeptRoleDepartmentList")
    @AutoLog(value = "部门角色获取部门列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getDeptRoleDepartmentList(Integer deptId) {
        final List<Tree<Integer>> departmentList = sysDepartmentService.getDepartmentList(deptId);
        return RetData.ok(departmentList);
    }

    /**
     * 功能: 删除部门角色授权的用户
     * 作者: zjt
     * 日期: 2021/3/10 17:54
     * 版本: 1.0
     */
    @PostMapping("deleteDeptRoleAuthorizedUser")
    @AutoLog(value = "删除部门角色授权的用户", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteDeptRoleAuthorizedUser(@RequestBody SysRoleAuthorizedUserDTO authorizedUserDTO) {
        deptRoleManagementService.deleteDeptRoleAuthorizedUser(authorizedUserDTO.getSysRoleId(), authorizedUserDTO.getSysUserIdList());
        return RetData.ok();
    }

    /**
     * 功能: 获取部门角色菜单资源
     * 作者: zjt
     * 日期: 2021/3/10 14:41
     * 版本: 1.0
     */
    @GetMapping("getDeptRoleSysPageTree")
    @AutoLog(value = "获取部门角色菜单资源", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getDeptRoleSysPageTree(@NotNull Integer deptRoleId, @NotNull Integer sysPageModuleTypeId) {
        final List<Tree<Integer>> sysPageTree = sysPageAndApiService.getSysPageTree(SysPermissionType.DEPT_ROLE, deptRoleId, sysPageModuleTypeId);
        return RetData.ok(sysPageTree);
    }

    /**
     * 功能: 获取部门角色接口集权限
     * 作者: zjt
     * 日期: 2021/3/10 14:44
     * 版本: 1.0
     */
    @GetMapping("getDeptRoleSysApiPermission")
    @AutoLog(value = "获取部门角色接口集权限", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<SysApiChooseVO>> getDeptRoleSysApiPermission(@NotNull Integer deptRoleId, @NotNull Integer sysPageId) {
        final List<SysApiChooseVO> sysApiPermission = sysPageAndApiService.getSysApiPermission(SysPermissionType.DEPT_ROLE, deptRoleId, sysPageId);
        return RetData.ok(sysApiPermission);
    }

    /**
     * 功能: 保存部门角色系统权限配置
     * 作者: zjt
     * 日期: 2021/3/10 14:47
     * 版本: 1.0
     */
    @PostMapping("saveDeptPermission")
    @AutoLog(value = "保存部门角色系统权限配置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData saveDeptPermission(@RequestBody SaveDeptRolePermissionDTO saveDeptPermissionDTO) {
        sysPageAndApiService.saveSysPermission(SysPermissionType.DEPT_ROLE, saveDeptPermissionDTO.getDeptRoleId(), saveDeptPermissionDTO.getSysPageModuleTypeId(), saveDeptPermissionDTO.getSysPageIdList());
        return RetData.ok();
    }

    /**
     * 功能: 保存部门角色系统权限配置 api级别
     * 作者: zjt
     * 日期: 2021/3/10 14:47
     * 版本: 1.0
     */
    @PostMapping("saveDeptPermissionApi")
    @AutoLog(value = "保存部门角色系统权限配置 api级别", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData saveDeptPermissionApi(@RequestBody SaveDeptRolePermissionDTO saveDeptRolePermission) {
        sysPageAndApiService.saveSysPermissionApi(SysPermissionType.DEPT_ROLE, saveDeptRolePermission.getDeptRoleId(), saveDeptRolePermission.getSysPageModuleTypeId(), saveDeptRolePermission.getSysParentPageId(), saveDeptRolePermission.getSysPageIdList());
        return RetData.ok();
    }

    /**
     * 功能: 获取部门角色列表
     * 作者: zjt
     * 日期: 2021/3/5 15:29
     * 版本: 1.0
     */
    @PostMapping("listDeptRoleItem")
    @AutoLog(value = "获取部门角色列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<DeptRoleItemVO>> listDeptRoleItem(@RequestBody ListDeptRoleItemDTO baseDTO) {
        final IPage<DeptRoleItemVO> deptRoleItemVOIPage = deptRoleManagementService.listDeptRoleItem(baseDTO);
        return RetData.ok(deptRoleItemVOIPage);
    }

    /**
     * 功能: 添加部门角色
     * 作者: zjt
     * 日期: 2021/3/5 11:47
     * 版本: 1.0
     */
    @PostMapping("addDeptRole")
    @AutoLog(value = "添加部门角色", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData addDeptRole(@RequestBody AddDeptRoleDTO addDeptRoleDTO) {
        deptRoleManagementService.addDeptRole(addDeptRoleDTO);
        return RetData.ok();
    }

    /**
     * 功能: 编辑部门角色
     * 作者: zjt
     * 日期: 2021/3/5 13:47
     * 版本: 1.0
     */
    @PutMapping("updateDeptRole")
    @AutoLog(value = "编辑部门角色", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateDeptRole(@RequestBody UpdateDeptRoleDTO updateDeptRoleDTO) {
        deptRoleManagementService.updateDeptRole(updateDeptRoleDTO);
        return RetData.ok();
    }

    /**
     * 功能: 获取部门角色详情
     * 作者: zjt
     * 日期: 2021/3/5 13:48
     * 版本: 1.0
     */
    @GetMapping("getDeptRoleInfo")
    @AutoLog(value = "获取部门角色详情", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<DeptRoleInfoVO> getDeptRoleInfo(@NotNull Integer deptRoleId) {
        final DeptRoleInfoVO deptRoleInfo = deptRoleManagementService.getDeptRoleInfo(deptRoleId);
        return RetData.ok(deptRoleInfo);
    }

    /**
     * 功能: 部门角色批量删除
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    @PostMapping("deleteDeptRole")
    @AutoLog(value = "部门角色批量删除", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteDeptRole(@RequestBody Collection<Integer> departRoleIdList) {
        deptRoleManagementService.deleteDeptRole(departRoleIdList);
        return RetData.ok();
    }

    /**
     * 功能: 部门角色授权用户
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    @PostMapping("deptRoleAuthorizedUser")
    @AutoLog(value = "部门角色授权用户", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData deptRoleAuthorizedUser(@RequestBody DeptRoleAuthorizedUserDTO authorizedUserDTO) {
        deptRoleManagementService.deptRoleAuthorizedUser(authorizedUserDTO.getDeptRoleId(), authorizedUserDTO.getSysUserIdList());
        return RetData.ok();
    }


    /**
     * 功能: 分页获取部门用户授权的分页列表
     * 作者: zjt
     * 日期: 2021/3/4 18:14
     * 版本: 1.0
     */
    @PostMapping("getListDeptUser")
    @AutoLog(value = "分页获取部门用户授权的分页列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListDeptUser(@RequestBody GetListSysUserDeptRoleDTO getListSysUserDeptRoleDTO) {
        final IPage<SysUserItemVO> listSysUser = sysDepartmentService.getListSysUser(getListSysUserDeptRoleDTO);
        return RetData.ok(listSysUser);
    }

    /**
     * 功能: 分页获取部门用户未授权分页列表
     * 作者: zjt
     * 日期: 2021/3/4 18:14
     * 版本: 1.0
     */
    @PostMapping("getListDeptUserNoAuth")
    @AutoLog(value = "分页获取部门用户未授权分页列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListDeptUserNoAuth(@RequestBody GetListDeptUserNoAuthDTO getListDeptUserNoAuthDTO) {
        final Integer deptRoleId = getListDeptUserNoAuthDTO.getDeptRoleId();
        final GetListSysUserDTO sysUserDTO = new GetListSysUserDTO();
        BeanUtils.copyProperties(getListDeptUserNoAuthDTO, sysUserDTO);
        sysUserDTO.setDeptId(sysDepartRoleMapper.selectById(deptRoleId).getSysDepartmentId());
        sysUserDTO.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
        final IPage<SysUserItemVO> listSysUser = sysUserService.getListSysUser(sysUserDTO);
        return RetData.ok(listSysUser);
    }

}