package com.rotanava.boot.system.module.dao;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    int deleteBySysRoleId(@Param("sysRoleId") Integer sysRoleId);

    List<SysRolePermission> findBySysRoleId(@Param("sysRoleId") Integer sysRoleId);

    List<Integer> findSysRoleIdBySysRoleId(@Param("sysRoleId") Integer sysRoleId);

    Integer countBySysRoleIdAndPageId(@Param("sysRoleId") Integer sysRoleId, @Param("pageId") Integer pageId);

    int deleteBySysRoleIdAndPageId(@Param("sysRoleId") Integer sysRoleId, @Param("pageId") Integer pageId);

    List<Integer> findPageIdBySysRoleId(@Param("sysRoleId") Integer sysRoleId);

    int deleteByPageIdIn(@Param("pageIdCollection")Collection<Integer> pageIdCollection);

    int deleteByPageIdInAndSysRoleId(@Param("pageIdCollection")Collection<Integer> pageIdCollection,@Param("sysRoleId")Integer sysRoleId);



}