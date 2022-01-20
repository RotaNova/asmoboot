package com.rotanava.boot.system.module.dao;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysDepartPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysDepartPermissionMapper extends BaseMapper<SysDepartPermission> {

    List<SysDepartPermission> findByDepartId(@Param("departId")Integer departId);

    int deleteByDepartId(@Param("departId")Integer departId);

    Integer countByDepartIdAndPagePermissionId(@Param("departId")Integer departId,@Param("pagePermissionId")Integer pagePermissionId);

    int deleteByDepartIdAndPagePermissionId(@Param("departId")Integer departId,@Param("pagePermissionId")Integer pagePermissionId);

    int deleteByPagePermissionIdIn(@Param("pagePermissionIdCollection")Collection<Integer> pagePermissionIdCollection);

    int deleteByPagePermissionIdInAndDepartId(@Param("pagePermissionIdCollection")Collection<Integer> pagePermissionIdCollection,@Param("departId")Integer departId);



}