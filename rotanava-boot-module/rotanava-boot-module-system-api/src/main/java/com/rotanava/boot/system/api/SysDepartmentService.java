package com.rotanava.boot.system.api;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.AddDepartmentDTO;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.UpdateDepartmentDTO;
import com.rotanava.boot.system.api.module.system.vo.DeptInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 16:44
 */
@Validated
public interface SysDepartmentService {

    /**
     * 功能: 添加部门
     * 作者: zjt
     * 日期: 2021/3/5 17:23
     * 版本: 1.0
     */
    Integer addDepartment(@Validated AddDepartmentDTO addDepartmentDTO, int userId);

    /**
     * 功能: 修改部门
     * 作者: zjt
     * 日期: 2021/3/5 17:23
     * 版本: 1.0
     */
    void updateDepartment(@Validated UpdateDepartmentDTO updateDepartmentDTO, int userId, boolean isMyDepartment);

    /**
     * 功能: 删除部门
     * 作者: zjt
     * 日期: 2021/3/5 17:23
     * 版本: 1.0
     */
    void deleteBatchDepartment(Collection<Integer> deptIdList, int userId);

    /**
     * 功能: 获取部门列表 如果有传部门id 取的是本部门及下属部门
     * 作者: zjt
     * 日期: 2021/3/5 16:46
     * 版本: 1.0
     */
    List<Tree<Integer>> getDepartmentList(@Nullable Integer deptId);

    /**
     * 功能: 获取我的部门列表 如果有传部门id 取的是本部门及下属部门
     * 作者: zjt
     * 日期: 2021/3/5 16:46
     * 版本: 1.0
     */
    List<Tree<Integer>> getMyDepartmentList(int userId, @Nullable Integer deptId);

    /**
     * 功能: 获取部门详情
     * 作者: zjt
     * 日期: 2021/3/6 14:36
     * 版本: 1.0
     */
    @Nullable
    DeptInfoVO getDeptInfo(int deptId);

    /**
     * 功能: 部门关联用户
     * 作者: zjt
     * 日期: 2021/3/12 14:51
     * 版本: 1.0
     */
    void departmentLinkUser(int deptId, Collection<Integer> sysUserIdList);

    /**
     * 功能: 部门取消关联用户
     * 作者: zjt
     * 日期: 2021/3/12 14:51
     * 版本: 1.0
     */
    void departmentUnlinkUser(int deptId, Collection<Integer> sysUserIdList);

    /**
     * 功能: 获取部门角色用户
     * 作者: zjt
     * 日期: 2021/3/26 15:57
     * 版本: 1.0
     */
    IPage<SysUserItemVO> getListSysUser(GetListSysUserDeptRoleDTO getListSysUserDeptRoleDTO);

    /**
     * @description :获取所有部门
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    List<SysDepartment> getAllDept();

}
