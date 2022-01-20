package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {

    String findDeptNameById(@Param("id") Integer id);

    List<SysDepartment> findall();

    Integer findIdByDeptName(@Param("deptName") String deptName);

    Integer findIdByDeptCode(@Param("deptCode") String deptCode);

    List<SysDepartment> findDepartmentByUserId(@Param("userId") int userId);

    List<Integer> findId();


}