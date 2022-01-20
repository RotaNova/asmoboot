package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rotanava.boot.system.api.DeptRoleManagementService;
import com.rotanava.boot.system.api.module.constant.DeptStatus;
import com.rotanava.boot.system.api.module.system.bean.DeptRoleLabel;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRole;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRoleUser;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.dto.AuthUserDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.ListDeptRoleItemDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleInfoVO;
import com.rotanava.boot.system.api.module.system.vo.DeptRoleItemVO;
import com.rotanava.boot.system.module.dao.SysDepartRoleMapper;
import com.rotanava.boot.system.module.dao.SysDepartRolePermissionMapper;
import com.rotanava.boot.system.module.dao.SysDepartRoleUserMapper;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 14:06
 */
@Slf4j
@Validated
@Service
@DubboService
public class DeptRoleManagementServiceImpl implements DeptRoleManagementService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysDepartRoleMapper sysDepartRoleMapper;

    @Autowired
    private SysDepartRolePermissionMapper sysDepartRolePermissionMapper;

    @Autowired
    private SysDepartRoleUserMapper sysDepartRoleUserMapper;


    @Override
    public IPage<DeptRoleItemVO> listDeptRoleItem(ListDeptRoleItemDTO listDeptRoleItemDTO) {

        final QueryWrapper<SysDepartRole> queryWrapper = QueryGenerator.initQueryWrapper(listDeptRoleItemDTO);
        if (listDeptRoleItemDTO.getDeptId() != null) {
            queryWrapper.eq("b.id", listDeptRoleItemDTO.getDeptId());
        }

        final IPage<SysDepartRole> sysDepartRoleIPage = sysDepartRoleMapper.queryList(queryWrapper, PageUtils.startPage(listDeptRoleItemDTO));

        final List<DeptRoleItemVO> deptRoleItemVOList = sysDepartRoleIPage.getRecords().stream().map(sysDepartRole -> {
            final DeptRoleItemVO deptRoleItem = new DeptRoleItemVO();
            deptRoleItem.setDeptRoleId(sysDepartRole.getId());
            deptRoleItem.setRoleCode(sysDepartRole.getRoleCode());
            deptRoleItem.setRoleDeptName(sysDepartRole.getDepartRoleName());
            deptRoleItem.setRoleDescription(sysDepartRole.getDescription());
            deptRoleItem.setDeptRoleName(sysDepartmentMapper.findDeptNameById(sysDepartRole.getSysDepartmentId()));
            deptRoleItem.setCreateTime(sysDepartRole.getCreateTime().getTime());
            return deptRoleItem;
        }).collect(Collectors.toList());

        return PageUtils.conversionIpageRecords(sysDepartRoleIPage, DeptRoleItemVO.class, deptRoleItemVOList);
    }

    @Override
    public List<DeptRoleLabel> getDeptRoleList(Integer sysUserId, int deptId) {
        final List<SysDepartRole> sysDepartRoleList = sysDepartRoleMapper.findBySysDepartmentId(deptId);
        final List<Integer> departRoleIdList = sysDepartRoleUserMapper.findBySysUserId(sysUserId).stream().map(SysDepartRoleUser::getDepartRoleId).collect(Collectors.toList());

        return sysDepartRoleList.stream().map(sysDepartRole -> {
            final DeptRoleLabel deptRoleLabel = new DeptRoleLabel();
            deptRoleLabel.setDeptRoleId(sysDepartRole.getId());
            deptRoleLabel.setDeptRoleName(sysDepartRole.getDepartRoleName());
            deptRoleLabel.setChoose(departRoleIdList.contains(sysDepartRole.getId()));
            return deptRoleLabel;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void addDeptRole(AddDeptRoleDTO deptRoleDTO) {
        final SysDepartRole sysDepartRole = new SysDepartRole();
        sysDepartRole.setSysDepartmentId(deptRoleDTO.getDeptId());
        sysDepartRole.setDepartRoleName(deptRoleDTO.getRoleName());
        sysDepartRole.setRoleCode(deptRoleDTO.getRoleCode());
        sysDepartRole.setDescription(deptRoleDTO.getRoleDescription());
        sysDepartRole.setCreateTime(new Date());

        final List<Integer> chooseDepartmentIdList = deptRoleDTO.getChooseDepartmentIdList();
        sysDepartRole.setAssignDeptIds(JSONUtil.toJsonStr(chooseDepartmentIdList));

        //参数检查
        checkParams(null, sysDepartRole);

        sysDepartRoleMapper.insert(sysDepartRole);

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateDeptRole(UpdateDeptRoleDTO updateDeptRoleDTO) {
        final SysDepartRole sysDepartRole = new SysDepartRole();
        sysDepartRole.setId(updateDeptRoleDTO.getDeptRoleId());
        sysDepartRole.setDepartRoleName(updateDeptRoleDTO.getRoleName());
        sysDepartRole.setDescription(updateDeptRoleDTO.getRoleDescription());
        sysDepartRole.setUpdateTime(new Date());

        final List<Integer> chooseDepartmentIdList = updateDeptRoleDTO.getChooseDepartmentIdList();
        sysDepartRole.setAssignDeptIds(JSONUtil.toJsonStr(chooseDepartmentIdList));

        //参数检查
        checkParams(sysDepartRole.getId(), sysDepartRole);

        sysDepartRoleMapper.updateById(sysDepartRole);
    }


    /**
     * 功能: 检查参数
     * 作者: zjt
     * 日期: 2021/3/20 14:25
     * 版本: 1.0
     */
    private void checkParams(Integer id, SysDepartRole sysRole) {
        final String roleCode = sysRole.getRoleCode();
        final String roleName = sysRole.getDepartRoleName();

        final List<Integer> roleCodeList = sysDepartRoleMapper.findIdByRoleCode(roleCode);
        doCheck(id, roleCodeList, "角色编码重复");
        final List<Integer> roleNameList = sysDepartRoleMapper.findIdByDepartRoleName(roleName);
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

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DeptRoleInfoVO getDeptRoleInfo(int deptRoleId) {
        final SysDepartRole sysDepartRole = sysDepartRoleMapper.selectById(deptRoleId);
        if (sysDepartRole != null) {
            final DeptRoleInfoVO deptRoleInfo = new DeptRoleInfoVO();
            deptRoleInfo.setDeptRoleId(sysDepartRole.getId());
            deptRoleInfo.setRoleCode(sysDepartRole.getRoleCode());
            deptRoleInfo.setRoleDescription(sysDepartRole.getDescription());
            deptRoleInfo.setRoleName(sysDepartRole.getDepartRoleName());
            deptRoleInfo.setDeptId(sysDepartRole.getSysDepartmentId());
            deptRoleInfo.setDeptName(sysDepartmentMapper.findDeptNameById(sysDepartRole.getSysDepartmentId()));

            final List<Integer> deptIdList = JSONUtil.toList(JSONUtil.parseArray(sysDepartRole.getAssignDeptIds()), Integer.class);
            deptRoleInfo.setChooseDepartmentList(deptIdList.stream().map(this::createSysDepartmentLabel).collect(Collectors.toList()));

            return deptRoleInfo;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteDeptRole(Collection<Integer> departRoleIdList) {
        for (Integer departRoleId : departRoleIdList) {
            sysDepartRoleMapper.deleteById(departRoleId);
            sysDepartRolePermissionMapper.deleteByDepartRoleId(departRoleId);
            sysDepartRoleUserMapper.deleteByDepartRoleId(departRoleId);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deptRoleAuthorizedUser(int departRoleId, Collection<Integer> sysUserIdList) {
        for (Integer sysUserId : sysUserIdList) {
            final Integer count = sysDepartRoleUserMapper.countBySysUserIdAndDepartRoleId(sysUserId, departRoleId);
            if (count == 0) {
                final SysDepartRoleUser sysDepartRoleUser = new SysDepartRoleUser();
                sysDepartRoleUser.setDepartRoleId(departRoleId);
                sysDepartRoleUser.setSysUserId(sysUserId);
                sysDepartRoleUserMapper.insert(sysDepartRoleUser);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteDeptRoleAuthorizedUser(Integer sysRoleId, Collection<Integer> sysUserIdList) {
        for (Integer sysUserId : sysUserIdList) {
            sysDepartRoleUserMapper.deleteBySysUserIdAndDepartRoleId(sysUserId, sysRoleId);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void authUserDeptRole(AuthUserDeptRoleDTO authUserDeptRoleDTO) {
        final Integer deptId = authUserDeptRoleDTO.getDeptId();
        final Integer sysUserId = authUserDeptRoleDTO.getSysUserId();
        final Collection<Integer> deptRoleIdList = authUserDeptRoleDTO.getDeptRoleIdList();
        final List<SysDepartRole> departRoleList = deptRoleIdList.isEmpty() ? Lists.newArrayList() : sysDepartRoleMapper.selectBatchIds(deptRoleIdList);
        final List<Integer> deptRoleIds = departRoleList.stream().map(departRole -> {
            if (departRole.getSysDepartmentId().equals(deptId)) {
                return departRole.getId();
            } else {
                throw new CommonException(AuthErrorCode.AUTH_ERROR_04);
            }
        }).collect(Collectors.toList());

        final List<Integer> idBySysDepartmentIdIn = sysDepartRoleMapper.findIdBySysDepartmentIdIn(Lists.newArrayList(deptId));
        if (idBySysDepartmentIdIn.size() > 0) {
            sysDepartRoleUserMapper.deleteBySysUserIdAndDepartRoleIdIn(sysUserId, idBySysDepartmentIdIn);
        }

        for (Integer deptRoleId : deptRoleIds) {
            final SysDepartRoleUser sysDepartRoleUser = new SysDepartRoleUser();
            sysDepartRoleUser.setDepartRoleId(deptRoleId);
            sysDepartRoleUser.setSysUserId(sysUserId);
            sysDepartRoleUserMapper.insert(sysDepartRoleUser);
        }

    }

    @Override
    public Set<Integer> getRelationDeptIdByUserId(Integer userId) {
        final Set<Integer> deptIdList = Sets.newHashSet();
        final List<Integer> departRoleIdList = sysDepartRoleUserMapper.findDepartRoleIdBySysUserId(userId);
        if (CollectionUtil.isNotEmpty(departRoleIdList)) {
            final List<String> assignDeptStrArray = sysDepartRoleMapper.findAssignDeptIdsByIdIn(departRoleIdList);
            for (String assignDeptStr : assignDeptStrArray) {
                final List<Integer> sysDeptIds = JSONUtil.toList(JSONUtil.parseArray(assignDeptStr), Integer.class);
                final List<SysDepartment> sysDepartments = sysDepartmentMapper.selectBatchIds(sysDeptIds);
                for (SysDepartment sysDepartment : sysDepartments) {
                    if (sysDepartment.getDeptStatus().equals(DeptStatus.NORMAL.getStatus())) {
                        deptIdList.add(sysDepartment.getId());
                    }
                }
            }
        }
        return deptIdList;
    }

    /**
     * 功能: 创建部门标签
     * 作者: zjt
     * 日期: 2021/3/16 15:27
     * 版本: 1.0
     */
    private SysDepartmentLabel createSysDepartmentLabel(int deptId) {
        final SysDepartmentLabel sysDepartmentLabel = new SysDepartmentLabel();
        sysDepartmentLabel.setSysDepartmentId(deptId);
        sysDepartmentLabel.setSysDepartmentName(sysDepartmentMapper.findDeptNameById(deptId));
        return sysDepartmentLabel;
    }
}