package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Sets;
import com.rotanava.boot.system.api.SysRoleManagementService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.constant.enums.RoleDataScopeType;
import com.rotanava.boot.system.api.module.constant.DeptStatus;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bean.SysRoleLabel;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysRole;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.bo.SysUserRole;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddSysRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateSysRoleDTO;
import com.rotanava.boot.system.api.module.system.vo.SysRoleInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysRoleItemVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysRoleDataMapper;
import com.rotanava.boot.system.module.dao.SysRoleMapper;
import com.rotanava.boot.system.module.dao.SysRolePermissionMapper;
import com.rotanava.boot.system.module.dao.SysUserDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserRoleMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 角色管理
 * @author: jintengzhou
 * @date: 2021-03-03 17:53
 */
@Slf4j
@Validated
@Service
@DubboService
@Transactional
public class SysRoleManagementServiceImpl implements SysRoleManagementService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleDataMapper sysRoleDataMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;


    @Override
    public IPage<SysRoleItemVO> listSysRoleItem(BaseDTO baseDTO) {

        final IPage<SysRole> sysRoleIPage = sysRoleMapper.selectPage(PageUtils.startPage(baseDTO), QueryGenerator.initQueryWrapper(baseDTO));

        final List<SysRoleItemVO> sysRoleItemVOList = sysRoleIPage.getRecords().stream().map(sysRole -> {
            final SysRoleItemVO roleVO = new SysRoleItemVO();
            roleVO.setSysRoleId(sysRole.getId());
            roleVO.setSysRoleName(sysRole.getRoleName());
            roleVO.setRoleCode(sysRole.getRoleCode());
            roleVO.setRoleDescription(sysRole.getRoleDescription());
            roleVO.setCreateTime(sysRole.getCreateTime().getTime());
            return roleVO;
        }).collect(Collectors.toList());

        return PageUtils.conversionIpageRecords(sysRoleIPage, SysRoleItemVO.class, sysRoleItemVOList);
    }

    @Override
    public List<SysRoleLabel> getSysRoleList() {
        return sysRoleMapper.findall().stream().map(sysRole -> {
            final SysRoleLabel sysRoleLabel = new SysRoleLabel();
            sysRoleLabel.setSysRoleId(sysRole.getId());
            sysRoleLabel.setSysRoleName(sysRole.getRoleName());
            return sysRoleLabel;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void addSysRole(AddSysRoleDTO addRoleDTO, int userId) {
        final SysRole sysRole = new SysRole();
        sysRole.setCreateBy(userId);
        sysRole.setCreateTime(new Date());
        sysRole.setRoleName(addRoleDTO.getRoleName());
        sysRole.setRoleCode(addRoleDTO.getRoleCode());
        sysRole.setRoleDescription(addRoleDTO.getRoleDescription());
        sysRole.setRoleDataScope(addRoleDTO.getRoleDataScope());
        //如果是指定部门
        setAndCheckAssignDeptIds(sysRole, addRoleDTO.getRoleDataScope(), addRoleDTO.getChooseDepartmentIdList());

        //参数检查
        checkParams(null, sysRole);
        sysRoleMapper.insert(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysRole(UpdateSysRoleDTO updateSysRoleDTO, int userId) {
        final SysRole sysRole = sysRoleMapper.selectById(updateSysRoleDTO.getSysRoleId());
        if (sysRole != null) {
            sysRole.setRoleName(updateSysRoleDTO.getRoleName());
            sysRole.setRoleCode(updateSysRoleDTO.getRoleCode());
            sysRole.setRoleDataScope(updateSysRoleDTO.getRoleDataScope());
            sysRole.setRoleDescription(updateSysRoleDTO.getRoleDescription());
            sysRole.setUpdateTime(new Date());
            sysRole.setUpdateBy(userId);

            //如果是指定部门
            setAndCheckAssignDeptIds(sysRole, updateSysRoleDTO.getRoleDataScope(), updateSysRoleDTO.getChooseDepartmentIdList());

            //参数检查
            checkParams(sysRole.getId(), sysRole);

            sysRoleMapper.updateById(sysRole);
        } else {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
    }

    /**
     * 功能: 检查参数
     * 作者: zjt
     * 日期: 2021/3/20 14:25
     * 版本: 1.0
     */
    private void checkParams(Integer id, SysRole sysRole) {
        final String roleCode = sysRole.getRoleCode();
        final String roleName = sysRole.getRoleName();

        final List<Integer> roleCodeList = sysRoleMapper.findIdByRoleCode(roleCode);
        doCheck(id, roleCodeList, "角色编码重复");
        final List<Integer> roleNameList = sysRoleMapper.findIdByRoleName(roleName);
        doCheck(id, roleNameList, "角色名称重复");
    }

    /**
     * 功能: 异常就抛出 throwStr
     * 作者: zjt
     * 日期: 2021/3/20 14:16
     * 版本: 1.0
     */
    private void doCheck(Integer userId, List<Integer> userIdList, String throwStr) {
        if (userId != null) {
            userIdList.remove(userId);
        }

        if (!userIdList.isEmpty()) {
            throw new CommonException(new ErrorCode(100100, throwStr));
        }
    }

    /**
     * 功能: 添加指定部门
     * 作者: zjt
     * 日期: 2021/3/20 11:40
     * 版本: 1.0
     */
    private void setAndCheckAssignDeptIds(SysRole sysRole, Integer roleDataScope, List<Integer> chooseDepartmentIdList) {
        if (roleDataScope == RoleDataScopeType.CHOOSE_DEPARTMENT.getType()) {
            if (CollectionUtils.isEmpty(chooseDepartmentIdList)) {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_32);
            } else {
                sysRole.setAssignDeptIds(JSONUtil.toJsonStr(chooseDepartmentIdList));
            }
        }
    }

    @Override
    public SysRoleInfoVO getSysRoleInfo(int sysRoleId) {
        final SysRole sysRole = sysRoleMapper.selectById(sysRoleId);
        if (sysRole != null) {
            final SysRoleInfoVO sysRoleInfoVO = new SysRoleInfoVO();

            sysRoleInfoVO.setSysRoleId(sysRoleId);
            sysRoleInfoVO.setRoleName(sysRole.getRoleName());
            sysRoleInfoVO.setRoleDescription(sysRole.getRoleDescription());
            sysRoleInfoVO.setRoleDataScope(sysRole.getRoleDataScope());
            sysRoleInfoVO.setRoleCode(sysRole.getRoleCode());

            final String deptIds = sysRole.getAssignDeptIds();
            final List<Integer> deptIdList = JSONUtil.toList(JSONUtil.parseArray(deptIds), Integer.class);

            final List<SysDepartmentLabel> departmentLabelList = deptIdList.stream().map(deptId -> {
                final SysDepartmentLabel sysDepartmentLabel = new SysDepartmentLabel();
                final String deptName = sysDepartmentMapper.findDeptNameById(deptId);
                sysDepartmentLabel.setSysDepartmentId(deptId);
                sysDepartmentLabel.setSysDepartmentName(deptName);
                return sysDepartmentLabel;
            }).collect(Collectors.toList());
            sysRoleInfoVO.setChooseDepartmentList(departmentLabelList);

            return sysRoleInfoVO;
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysRole(Collection<Integer> sysRoleIdList) {
        //1 不能删除
        sysRoleIdList.remove(1);
        for (Integer sysRoleId : sysRoleIdList) {
            sysRoleMapper.deleteById(sysRoleId);
            sysRoleDataMapper.deleteBySysRoleId(sysRoleId);
            sysRolePermissionMapper.deleteBySysRoleId(sysRoleId);
            sysUserRoleMapper.deleteBySysRoleId(sysRoleId);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void sysRoleAuthorizedUser(int sysRoleId, Collection<Integer> sysUserIdList) {
        for (Integer sysUserId : sysUserIdList) {
            final int countBySysRoleIdAndSysUserId = sysUserRoleMapper.countBySysRoleIdAndSysUserId(sysRoleId, sysUserId);
            if (countBySysRoleIdAndSysUserId == 0) {
                final SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setSysUserId(sysUserId);
                sysUserRole.setSysRoleId(sysRoleId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysRoleAuthorizedUser(Integer sysRoleId, Collection<Integer> sysUserIdList) {
        for (Integer sysUserId : sysUserIdList) {
            //当为管理员的时候无法删除
            if (!(sysUserId == 1 && sysRoleId == 1)) {
                sysUserRoleMapper.deleteBySysRoleIdAndSysUserId(sysRoleId, sysUserId);
            }
        }
    }

    @Override
    public IPage<SysUserItemVO> getListSysUser(GetListSysUserRoleDTO sysUserDTO) {
        final QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(sysUserDTO);
        queryWrapper.eq("b.user_delete_status", UserDeleteStatus.NOT_DELETED.getStatus());
        queryWrapper.eq("a.sys_role_id", sysUserDTO.getSysRoleId());
        final IPage<SysUser> sysUserIPage = sysRoleMapper.queryUserByRole(queryWrapper, PageUtils.startPage(sysUserDTO));
        return sysUserService.getUserItemVOIPage(sysUserIPage, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Integer> getRelationDeptIdByUserId(Integer sysUserId) {
        final Set<Integer> deptIdList = Sets.newHashSet();
        final List<Integer> sysRoleIdList = sysUserRoleMapper.findSysRoleIdBySysUserId(sysUserId);

        boolean thisDepartment = true;
        boolean thisDepartmentAndItsSubordinateDepts = true;

        final List<SysDepartment> departmentList = sysDepartmentMapper.findall();
        final Map<Integer, Integer> deptStatusMap = departmentList.stream().collect(Collectors.toMap(SysDepartment::getId, SysDepartment::getDeptStatus));

        if (CollectionUtil.isNotEmpty(sysRoleIdList)) {
            final List<SysRole> sysRoleList = sysRoleMapper.selectBatchIds(sysRoleIdList);
            for (SysRole sysRole : sysRoleList) {
                final Integer roleDataScope = sysRole.getRoleDataScope();
                if (RoleDataScopeType.THIS_DEPARTMENT.getType() == roleDataScope && thisDepartment) {
                    final List<Integer> sysDepartmentIdList = sysUserDepartmentMapper.findSysDepartmentIdBySysUserId(sysUserId);
                    for (Integer sysDeptId : sysDepartmentIdList) {
                        if (deptStatusMap.getOrDefault(sysDeptId, DeptStatus.INACTIVATED.getStatus()).equals(DeptStatus.NORMAL.getStatus())) {
                            deptIdList.add(sysDeptId);
                        }
                    }
                    thisDepartment = false;
                } else if (RoleDataScopeType.THIS_DEPARTMENT_AND_ITS_SUBORDINATE_DEPTS.getType() == roleDataScope && thisDepartmentAndItsSubordinateDepts) {
                    final List<Integer> sysDepartmentIdList = sysUserDepartmentMapper.findSysDepartmentIdBySysUserId(sysUserId);
                    if (CollectionUtil.isNotEmpty(sysDepartmentIdList)) {

                        final List<Tree<Integer>> treeList = TreeUtil.build(departmentList, 0, (sysDepartment, treeNode) -> {
                            final Integer departmentId = sysDepartment.getId();
                            treeNode.setId(departmentId);
                            treeNode.setParentId(sysDepartment.getParentDeptId());
                            treeNode.put("sysDepartment", sysDepartment);
                        });

                        for (Integer sysDepartmentId : sysDepartmentIdList) {
                            deptIdList.add(sysDepartmentId);
                            addLowLevelDeptId(deptIdList, treeList, sysDepartmentId, false);
                        }
                    }
                    thisDepartmentAndItsSubordinateDepts = false;
                } else if (RoleDataScopeType.COMPANY_ALL.getType() == roleDataScope) {
                    for (SysDepartment sysDepartment : departmentList) {
                        if (sysDepartment.getDeptStatus().equals(DeptStatus.NORMAL.getStatus())) {
                            deptIdList.add(sysDepartment.getId());
                        }
                    }
                } else if (RoleDataScopeType.CHOOSE_DEPARTMENT.getType() == roleDataScope) {
                    final List<Integer> sysDeptIds = JSONUtil.toList(JSONUtil.parseArray(sysRole.getAssignDeptIds()), Integer.class);
                    for (Integer sysDeptId : sysDeptIds) {
                        if (deptStatusMap.getOrDefault(sysDeptId, DeptStatus.INACTIVATED.getStatus()).equals(DeptStatus.NORMAL.getStatus())) {
                            deptIdList.add(sysDeptId);
                        }
                    }
                }
            }

        }


        return deptIdList;
    }


    /**
     * 功能: 添加下级部门
     * 作者: zjt
     * 日期: 2021/7/22 15:42
     * 版本: 1.0
     */
    private void addLowLevelDeptId(Set<Integer> deptIdList, List<Tree<Integer>> treeList, Integer sysDepartmentId, boolean canAdd) {
        for (Tree<Integer> tree : treeList) {
            boolean copyCanAdd = canAdd;
            final Integer deptId = tree.getId();
            final SysDepartment sysDepartment = (SysDepartment) tree.get("sysDepartment");
            if (sysDepartment.getDeptStatus().equals(DeptStatus.NORMAL.getStatus())) {
                if (copyCanAdd || deptId.equals(sysDepartmentId)) {
                    deptIdList.add(deptId);
                    copyCanAdd = true;
                }

                final List<Tree<Integer>> children = tree.getChildren();
                if (CollectionUtil.isNotEmpty(children)) {
                    addLowLevelDeptId(deptIdList, children, sysDepartmentId, copyCanAdd);
                }
            }

        }

    }

}