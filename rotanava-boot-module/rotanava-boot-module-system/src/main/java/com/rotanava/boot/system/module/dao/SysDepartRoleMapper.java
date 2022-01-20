package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRole;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysDepartRoleMapper extends BaseMapper<SysDepartRole> {

    List<SysDepartRole> findall();

    /**
     * 按用户id查询
     *
     * @param sysUserId 用户id
     * @return {@link List<SysDepartRole> }
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysDepartRole> findBySysUserId(Integer sysUserId);

    List<SysDepartRole> findBySysDepartmentId(@Param("sysDepartmentId")Integer sysDepartmentId);



    List<Integer> findIdByRoleCode(@Param("roleCode") String roleCode);

    List<Integer> findIdByDepartRoleName(@Param("departRoleName") String departRoleName);

    List<Integer> findIdBySysDepartmentIdIn(@Param("sysDepartmentIdCollection") Collection<Integer> sysDepartmentIdCollection);

    IPage<SysDepartRole> queryList(@Param(Constants.WRAPPER) QueryWrapper<SysDepartRole> queryWrapper, IPage<SysDepartRole> sysDepartRoleIPage);

    IPage<SysUser> queryUserByRole(@Param(Constants.WRAPPER) QueryWrapper<SysUser> queryWrapper, IPage<SysUser> iPage);

    List<String> findAssignDeptIdsByIdIn(@Param("idCollection")Collection<Integer> idCollection);


}