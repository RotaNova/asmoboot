package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRolePermission;
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
public interface SysDepartRolePermissionMapper extends BaseMapper<SysDepartRolePermission> {

    int deleteByDepartRoleId(@Param("departRoleId") Integer departRoleId);

    List<SysDepartRolePermission> findByDepartRoleId(@Param("departRoleId") Integer departRoleId);

    List<SysDepartRolePermission> findByDepartRoleIdIn(@Param("departRoleIdCollection") Collection<Integer> departRoleIdCollection);

    Integer countByDepartRoleIdAndPagePermissionId(@Param("departRoleId") Integer departRoleId, @Param("pagePermissionId") Integer pagePermissionId);

    int deleteByDepartRoleIdAndPagePermissionId(@Param("departRoleId")Integer departRoleId,@Param("pagePermissionId")Integer pagePermissionId);

    List<Integer> findPagePermissionIdByDepartRoleId(@Param("departRoleId")Integer departRoleId);

    int deleteByPagePermissionIdIn(@Param("pagePermissionIdCollection")Collection<Integer> pagePermissionIdCollection);

    int deleteByPagePermissionIdInAndDepartRoleId(@Param("pagePermissionIdCollection")Collection<Integer> pagePermissionIdCollection,@Param("departRoleId")Integer departRoleId);


}