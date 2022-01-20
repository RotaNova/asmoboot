package com.rotanava.boot.system.module.dao;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysUserDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysUserDepartmentMapper extends BaseMapper<SysUserDepartment> {

    int deleteBySysUserId(@Param("sysUserId") Integer sysUserId);

    List<Integer> findSysDepartmentIdBySysUserId(@Param("sysUserId")Integer sysUserId);

    List<SysUserDepartment> findAllBySysUserId(@Param("sysUserId")Integer sysUserId);

    int deleteBySysDepartmentId(@Param("sysDepartmentId")Integer sysDepartmentId);

    List<SysUserDepartment> findBySysDepartmentId(@Param("sysDepartmentId")Integer sysDepartmentId);

    List<Integer> findSysUserIdBySysDepartmentId(@Param("sysDepartmentId")Integer sysDepartmentId);

    List<Integer> findSysUserIdBySysDepartmentIdIn(@Param("sysDepartmentIdCollection")Collection<Integer> sysDepartmentIdCollection);

    int deleteBySysDepartmentIdAndSysUserId(@Param("sysDepartmentId")Integer sysDepartmentId,@Param("sysUserId")Integer sysUserId);

    int deleteBySysDepartmentIdIn(@Param("sysDepartmentIdCollection")Collection<Integer> sysDepartmentIdCollection);

    SysUserDepartment findAllBySysUserIdAndSysDepartmentId(@Param("sysUserId")Integer sysUserId,@Param("sysDepartmentId")Integer sysDepartmentId);

    Integer countBySysDepartmentIdAndSysUserId(@Param("sysDepartmentId")Integer sysDepartmentId,@Param("sysUserId")Integer sysUserId);

    int deleteBySysUserIdIn(@Param("sysUserIdCollection")Collection<Integer> sysUserIdCollection);

    int deleteBySysUserIdAndDeptManage(@Param("sysUserId")Integer sysUserId,@Param("deptManage")Integer deptManage);

    int deleteBySysDepartmentIdAndDeptManage(@Param("sysDepartmentId")Integer sysDepartmentId,@Param("deptManage")Integer deptManage);

    List<Integer> findIdBySysDepartmentId(@Param("sysDepartmentId")Integer sysDepartmentId);

}