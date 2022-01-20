package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    int deleteBySysUserId(@Param("sysUserId") Integer sysUserId);

    List<Integer> findSysRoleIdBySysUserId(@Param("sysUserId") Integer sysUserId);

    int deleteBySysRoleId(@Param("sysRoleId") Integer sysRoleId);

    int deleteBySysRoleIdAndSysUserId(@Param("sysRoleId") Integer sysRoleId, @Param("sysUserId") Integer sysUserId);

    int countBySysRoleIdAndSysUserId(@Param("sysRoleId") Integer sysRoleId, @Param("sysUserId") Integer sysUserId);
}