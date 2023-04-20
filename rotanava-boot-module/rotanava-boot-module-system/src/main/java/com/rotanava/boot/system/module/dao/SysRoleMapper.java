package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bo.SysRole;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-03 17:48
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询所有
     *
     * @return {@link List<SysRole> }
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysRole> findall();


    String findRoleNameById(@Param("id") Integer id);


    /**
     * 找到系统的用户id
     *
     * @param sysUserId 系统的用户id
     * @return {@link List<SysRole> }
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysRole> findBySysUserId(Integer sysUserId);

    List<Integer> findIdByRoleCode(@Param("roleCode") String roleCode);

    List<Integer> findIdByRoleName(@Param("roleName") String roleName);

    List<Integer> findIdBySysDepartmentIdIn(@Param("sysDepartmentIdCollection") Collection<Integer> sysDepartmentIdCollection);

    IPage<SysUser> queryUserByRole(@Param(Constants.WRAPPER) QueryWrapper<SysUser> queryWrapper, IPage<SysUser> iPage);

    SysRole findByRoleCode(@Param("roleCode")String roleCode);


    int insertAdmin(@Param("sysRole")SysRole sysRole);



}