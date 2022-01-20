package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.bean.DeptRoleLabel;
import com.rotanava.boot.system.api.module.system.dto.AuthUserDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.ListDeptRoleItemDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleInfoVO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleItemVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @description: 部门角色管理
 * @author: jintengzhou
 * @date: 2021-03-05 11:46
 */
@Validated
public interface DeptRoleManagementService {

    /**
     * 功能: 获取部门角色列表
     * 作者: zjt
     * 日期: 2021/3/5 15:29
     * 版本: 1.0
     */
    IPage<DeptRoleItemVO> listDeptRoleItem(ListDeptRoleItemDTO listDeptRoleItemDTO);

    /**
     * 功能: 根据部门id获取部门下的角色
     * 作者: zjt
     * 日期: 2021/4/7 11:19
     * 版本: 1.0
     */
    List<DeptRoleLabel> getDeptRoleList(@NotNull Integer sysUserId, int deptId);

    /**
     * 功能: 添加部门角色
     * 作者: zjt
     * 日期: 2021/3/5 11:47
     * 版本: 1.0
     */
    void addDeptRole(@Validated AddDeptRoleDTO addDeptRoleDTO);

    /**
     * 功能: 编辑部门角色
     * 作者: zjt
     * 日期: 2021/3/5 13:47
     * 版本: 1.0
     */
    void updateDeptRole(@Validated UpdateDeptRoleDTO updateDeptRoleDTO);

    /**
     * 功能: 获取部门角色详情
     * 作者: zjt
     * 日期: 2021/3/5 13:48
     * 版本: 1.0
     */
    DeptRoleInfoVO getDeptRoleInfo(int deptRoleId);

    /**
     * 功能: 部门角色批量删除
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    void deleteDeptRole(Collection<Integer> departRoleIdList);

    /**
     * 功能: 部门角色授权用户
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    void deptRoleAuthorizedUser(int departRoleId, Collection<Integer> sysUserIdList);

    /**
     * 功能: 删除部门角色授权的用户
     * 作者: zjt
     * 日期: 2021/3/10 18:06
     * 版本: 1.0
     */
    void deleteDeptRoleAuthorizedUser(Integer sysRoleId, Collection<Integer> sysUserIdList);

    /**
     * 功能: 授权用户部门角色
     * 作者: zjt
     * 日期: 2021/5/14 15:03
     * 版本: 1.0
     */
    void authUserDeptRole(AuthUserDeptRoleDTO authUserDeptRoleDTO);

    /**
     * 功能: 根据用户Id获取 部门角色 关联的所有部门
     * 作者: zjt
     * 日期: 2021/7/22 14:04
     * 版本: 1.0
     */
    Set<Integer> getRelationDeptIdByUserId(@NotNull Integer userId);
}
