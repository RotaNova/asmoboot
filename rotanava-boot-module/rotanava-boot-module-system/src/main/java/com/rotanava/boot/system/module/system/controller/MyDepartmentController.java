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
import com.rotanava.boot.system.api.module.system.bean.DeptRoleLabel;
import com.rotanava.boot.system.api.module.system.bean.SysRoleLabel;
import com.rotanava.boot.system.api.module.system.dto.AddSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.AuthUserDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.DepartmentUnlinkUserDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListDeptUserAddDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.ListDeptRoleItemDTO;
import com.rotanava.boot.system.api.module.system.dto.RestPasswordDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveDeptRolePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.UpdateDepartmentDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.DeptRoleAuthorizedUserDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.SysRoleAuthorizedUserDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.group.Department;
import com.rotanava.boot.system.api.module.system.vo.DeptInfoVO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleInfoVO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleItemVO;
import com.rotanava.boot.system.api.module.system.vo.SysApiChooseVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @description: 我的部门
 * @author: jintengzhou
 * @date: 2021-03-09 16:36
 */
@Api(tags = "我的部门")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/myDepartment")
public class MyDepartmentController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleManagementService sysRoleManagementService;

    @Autowired
    private SysPageAndApiService sysPageAndApiService;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private DeptRoleManagementService deptRoleManagementService;

    /**
     * 功能: 获取部门列表
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @GetMapping("getDepartmentList")
    @AutoLog(value = "获取部门列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getDepartmentList(Integer deptId) {
        final List<Tree<Integer>> departmentList = sysDepartmentService.getMyDepartmentList(SysUtil.getCurrentReqUserId(), deptId);
        return RetData.ok(departmentList);
    }

    /**
     * 功能: 获取部门详情
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @GetMapping("getDeptInfo")
    @AutoLog(value = "获取部门详情", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<DeptInfoVO> getDeptInfo(int deptId) {
        final DeptInfoVO deptInfo = sysDepartmentService.getDeptInfo(deptId);
        return RetData.ok(deptInfo);
    }

    /**
     * 功能: 修改部门
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @PutMapping("updateDepartment")
    @AutoLog(value = "修改部门", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateDepartment(@RequestBody UpdateDepartmentDTO updateDepartmentDTO) {
        updateDepartmentDTO.setSysUserIdList(null);
        sysDepartmentService.updateDepartment(updateDepartmentDTO, SysUtil.getCurrentReqUserId(), true);
        return RetData.ok();
    }


    //部门成员

    /**
     * 功能: 添加系统用户
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @PostMapping("addSysUser")
    @AutoLog(value = "添加系统用户", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData addSysUser(@Validated AddSysUserDTO addSysUserDTO, @RequestParam(name = "photoFile", required = false) MultipartFile photoFile) throws Exception {
        byte[] bytes = null;
        if (photoFile != null) {
            bytes = photoFile.getBytes();
        }
        sysUserService.addSysUser(addSysUserDTO, bytes, SysUtil.getCurrentReqUserId(), true);
        return RetData.ok();
    }


    /**
     * 功能: 修改用户
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @PutMapping("updateSysUser")
    @AutoLog(value = "修改用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateSysUser(@Validated UpdateSysUserDTO updateSysUserDTO, @RequestParam(name = "photoFile", required = false) MultipartFile photoFile) throws IOException {
        byte[] bytes = null;
        if (photoFile != null) {
            bytes = photoFile.getBytes();
        }
        sysUserService.updateSysUser(updateSysUserDTO, bytes, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 获取用户信息
     * 作者: zjt
     * 日期: 2021/3/5 10:13
     * 版本: 1.0
     */
    @GetMapping("getSysUserInfo")
    @AutoLog(value = "获取用户信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<SysUserInfoVO> getSysUserInfo(int sysUserId) {
        final SysUserInfoVO sysUserInfo = sysUserService.getSysUserInfo(sysUserId, null);
        return RetData.ok(sysUserInfo);
    }

    /**
     * 功能: 部门成员获取用户分页列表
     * 作者: zjt
     * 日期: 2021/3/4 18:14
     * 版本: 1.0
     */
    @PostMapping("getListSysUser")
    @AutoLog(value = "部门成员获取用户分页列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListSysUser(@RequestBody @Validated({Department.class}) GetListSysUserDTO sysUserDTO) {
        sysUserDTO.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
        final IPage<SysUserItemVO> listSysUser = sysUserService.getListSysUser(sysUserDTO);
        return RetData.ok(listSysUser);
    }

    /**
     * 功能: 获取已有的部门成员
     * 作者: zjt
     * 日期: 2021/3/29 11:29
     * 版本: 1.0
     */
    @PostMapping("getListDeptUserAdd")
    @AutoLog(value = "获取已有的部门成员", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListDeptUserAdd(@RequestBody GetListDeptUserAddDTO GetListSysUserDTO) {
        final IPage<SysUserItemVO> listSysUser = sysUserService.getNotAddListSysUser(GetListSysUserDTO);
        return RetData.ok(listSysUser);
    }

    /**
     * 功能: 删除用户
     * 作者: zjt
     * 日期: 2021/3/4 17:57
     * 版本: 1.0
     */
    @PostMapping("deleteSysUser")
    @AutoLog(value = "删除用户", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteSysUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.deleteSysUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 冻结用户
     * 作者: zjt
     * 日期: 2021/3/4 17:51
     * 版本: 1.0
     */
    @PutMapping("freezeSysUser")
    @AutoLog(value = "冻结用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData freezeSysUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.freezeSysUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 解冻用户
     * 作者: zjt
     * 日期: 2021/3/4 17:51
     * 版本: 1.0
     */
    @PutMapping("unfreezeSysUser")
    @AutoLog(value = "解冻用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData unfreezeSysUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.unfreezeSysUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 用户密码重置
     * 作者: zjt
     * 日期: 2021/3/4 18:19
     * 版本: 1.0
     */
    @PutMapping("restPassword")
    @AutoLog(value = "用户密码重置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData restPassword(@RequestBody RestPasswordDTO restPasswordDTO) {
        sysUserService.restPassword(restPasswordDTO.getSysUserId(), restPasswordDTO.getPassword(), SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }


    /**
     * 功能: 获取全部用户角色
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @GetMapping("getSysRoleList")
    @AutoLog(value = "获取全部用户角色", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<SysRoleLabel>> getSysRoleList() {
        final List<SysRoleLabel> sysRoleList = sysRoleManagementService.getSysRoleList();
        return RetData.ok(sysRoleList);
    }

    /**
     * 功能: 部门成员取消关联用户
     * 作者: zjt
     * 日期: 2021/3/12 14:50
     * 版本: 1.0
     */
    @PostMapping("departmentUnlinkUser")
    @AutoLog(value = "部门取消关联用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData departmentUnlinkUser(@RequestBody DepartmentUnlinkUserDTO departmentUnlinkUserDTO) {
        sysDepartmentService.departmentUnlinkUser(departmentUnlinkUserDTO.getDeptId(), departmentUnlinkUserDTO.getSysUserIdList());
        return RetData.ok();
    }

    /**
     * 功能: 部门成员添加已有成员
     * 作者: zjt
     * 日期: 2021/3/29 11:28
     * 版本: 1.0
     */
    @PostMapping("departmentLinkUser")
    @AutoLog(value = "部门成员添加已有成员", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData departmentLinkUser(@RequestBody DepartmentUnlinkUserDTO departmentUnlinkUserDTO) {
        sysDepartmentService.departmentLinkUser(departmentUnlinkUserDTO.getDeptId(), departmentUnlinkUserDTO.getSysUserIdList());
        return RetData.ok();
    }

    /**
     * 功能: 授权用户部门角色
     * 作者: zjt
     * 日期: 2021/5/14 15:03
     * 版本: 1.0
     */
    @PostMapping("authUserDeptRole")
    @AutoLog(value = "授权用户部门角色", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData authUserDeptRole(@RequestBody AuthUserDeptRoleDTO authUserDeptRoleDTO) {
        deptRoleManagementService.authUserDeptRole(authUserDeptRoleDTO);
        return RetData.ok();
    }

    /**
     * 功能: 根据部门id获取部门下的角色
     * 作者: zjt
     * 日期: 2021/4/7 11:28
     * 版本: 1.0
     */
    @GetMapping("getDeptRoleList")
    @AutoLog(value = "根据部门id获取部门下的角色", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<DeptRoleLabel>> getDeptRoleList(@NotNull Integer deptId, @NotNull Integer sysUserId) {
        final List<DeptRoleLabel> deptRoleList = deptRoleManagementService.getDeptRoleList(sysUserId, deptId);
        return RetData.ok(deptRoleList);
    }

    //部门角色

    /**
     * 功能: 分页获取部门用户分页列表
     * 作者: zjt
     * 日期: 2021/3/4 18:14
     * 版本: 1.0
     */
    @PostMapping("getListDeptUser")
    @AutoLog(value = "分页获取部门用户分页列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListDeptUser(@RequestBody GetListSysUserDeptRoleDTO getListSysUserDeptRoleDTO) {
        final IPage<SysUserItemVO> listSysUser = sysDepartmentService.getListSysUser(getListSysUserDeptRoleDTO);
        return RetData.ok(listSysUser);
    }


    /**
     * 功能: 部门角色获取部门列表
     * 作者: zjt
     * 日期: 2021/3/11 16:31
     * 版本: 1.0
     */
    @GetMapping("getDeptRoleDepartmentList")
    @AutoLog(value = "部门角色获取部门列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getDeptRoleDepartmentList(@NotNull Integer deptId) {
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
     * 功能: 获取该部门角色菜单资源
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
    public RetData<List<SysApiChooseVO>> getDeptRoleSysApiPermission(int deptRoleId, int sysPageId) {
        final List<SysApiChooseVO> sysApiPermission = sysPageAndApiService.getSysApiPermission(SysPermissionType.DEPT_ROLE, deptRoleId, sysPageId);
        return RetData.ok(sysApiPermission);
    }

    /**
     * 功能: 保存部门角色系统权限配置
     * 作者: zjt
     * 日期: 2021/3/10 14:47
     * 版本: 1.0
     */
    @PostMapping("saveDeptRolePermission")
    @AutoLog(value = "保存部门角色系统权限配置", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData saveDeptRolePermission(@RequestBody SaveDeptRolePermissionDTO saveDeptPermissionDTO) {
        sysPageAndApiService.saveSysPermission(SysPermissionType.SYSTEM_ROLE, saveDeptPermissionDTO.getDeptRoleId(), saveDeptPermissionDTO.getSysPageModuleTypeId(), saveDeptPermissionDTO.getSysPageIdList());
        return RetData.ok();
    }

    /**
     * 功能: 保存系统角色系统权限配置 api级别
     * 作者: zjt
     * 日期: 2021/3/10 14:47
     * 版本: 1.0
     */
    @PostMapping("saveDeptRolePermissionApi")
    @AutoLog(value = "保存系统角色系统权限配置 api级别", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData saveDeptRolePermissionApi(@RequestBody SaveDeptRolePermissionDTO saveDeptRolePermission) {
        sysPageAndApiService.saveSysPermissionApi(SysPermissionType.SYSTEM_ROLE, saveDeptRolePermission.getDeptRoleId(), saveDeptRolePermission.getSysPageModuleTypeId(), saveDeptRolePermission.getSysParentPageId(), saveDeptRolePermission.getSysPageIdList());
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
    public RetData<IPage<DeptRoleItemVO>> listDeptRoleItem(@RequestBody @Validated({Department.class}) ListDeptRoleItemDTO listDeptRoleItemDTO) {
        final IPage<DeptRoleItemVO> deptRoleItemVOIPage = deptRoleManagementService.listDeptRoleItem(listDeptRoleItemDTO);
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
     * 功能: 部门角色添加已有用户
     * 作者: zjt
     * 日期: 2021/3/29 11:34
     * 版本: 1.0
     */
    @PostMapping("getHaveListSysUser")
    @AutoLog(value = "部门角色添加已有用户", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getHaveListSysUser(@RequestBody @Validated({Department.class}) GetListSysUserDTO sysUserDTO) {
        sysUserDTO.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
        final IPage<SysUserItemVO> listSysUser = sysUserService.getListSysUser(sysUserDTO);
        return RetData.ok(listSysUser);
    }

}