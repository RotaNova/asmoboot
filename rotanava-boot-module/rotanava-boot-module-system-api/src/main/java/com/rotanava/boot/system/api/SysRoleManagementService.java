package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.bean.SysRoleLabel;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddSysRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateSysRoleDTO;
import com.rotanava.boot.system.api.module.system.vo.SysRoleInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysRoleItemVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.framework.model.BaseDTO;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @description: 系统角色管理
 * @author: jintengzhou
 * @date: 2021-03-03 17:52
 */
@Validated
public interface SysRoleManagementService {

    /**
     * 功能: 查询角色列表
     * 作者: zjt
     * 日期: 2021/3/3 17:57
     * 版本: 1.0
     *
     * @param baseDTO
     * @return
     */
    IPage<SysRoleItemVO> listSysRoleItem(BaseDTO baseDTO);

    /**
     * 功能: 获取系统角色
     * 作者: zjt
     * 日期: 2021/3/10 16:08
     * 版本: 1.0
     */
    List<SysRoleLabel> getSysRoleList();

    /**
     * 功能: 新增系统角色
     * 作者: zjt
     * 日期: 2021/3/3 18:12
     * 版本: 1.0
     */
    void addSysRole(@Validated AddSysRoleDTO addRoleDTO, int userId);

    /**
     * 功能: 编辑系统角色
     * 作者: zjt
     * 日期: 2021/3/3 18:12
     * 版本: 1.0
     */
    void updateSysRole(@Validated UpdateSysRoleDTO updateSysRoleDTO, int userId);

    /**
     * 功能: 获取系统角色详情
     * 作者: zjt
     * 日期: 2021/3/5 15:45
     * 版本: 1.0
     */
    @Nullable
    SysRoleInfoVO getSysRoleInfo(int sysRoleId);

    /**
     * 功能: 部门角色批量删除
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    void deleteSysRole(Collection<Integer> sysRoleIdList);

    /**
     * 功能: 系统角色授权用户
     * 作者: zjt
     * 日期: 2021/3/5 11:48
     * 版本: 1.0
     */
    void sysRoleAuthorizedUser(int sysRoleId, Collection<Integer> sysUserIdList);

    /**
     * 功能: 删除系统角色授权的用户
     * 作者: zjt
     * 日期: 2021/3/10 17:56
     * 版本: 1.0
     */
    void deleteSysRoleAuthorizedUser(Integer sysRoleId, Collection<Integer> sysUserIdList);

    /**
     * 功能: 获取管理系统用户
     * 作者: zjt
     * 日期: 2021/3/26 15:33
     * 版本: 1.0
     */
    IPage<SysUserItemVO> getListSysUser(GetListSysUserRoleDTO sysUserDTO);

    /**
     * 功能: 根据用户id 获取系统角色包括 数据范围里面指定的 有权限的所有关联部门
     * 作者: zjt
     * 日期: 2021/7/22 14:16
     * 版本: 1.0
     */
    Set<Integer> getRelationDeptIdByUserId(@NotNull Integer userId);

}
