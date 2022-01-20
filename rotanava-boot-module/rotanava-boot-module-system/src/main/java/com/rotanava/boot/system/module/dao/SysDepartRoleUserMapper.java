package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRoleUser;
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
public interface SysDepartRoleUserMapper extends BaseMapper<SysDepartRoleUser> {

    int deleteByDepartRoleId(@Param("departRoleId") Integer departRoleId);

    int deleteBySysUserIdAndDepartRoleId(@Param("sysUserId") Integer sysUserId, @Param("departRoleId") Integer departRoleId);

    int deleteBySysUserIdAndDepartRoleIdIn(@Param("sysUserId") Integer sysUserId, @Param("departRoleIdCollection") Collection<Integer> departRoleIdCollection);

    int deleteBySysUserIdIn(@Param("sysUserIdCollection") Collection<Integer> sysUserIdCollection);

    List<SysDepartRoleUser> findBySysUserId(@Param("sysUserId") Integer sysUserId);

    Integer countBySysUserIdAndDepartRoleId(@Param("sysUserId")Integer sysUserId,@Param("departRoleId")Integer departRoleId);

    List<Integer> findDepartRoleIdBySysUserId(@Param("sysUserId")Integer sysUserId);



}