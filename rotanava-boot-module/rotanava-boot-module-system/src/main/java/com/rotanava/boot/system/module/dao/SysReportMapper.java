package com.rotanava.boot.system.module.dao;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysReport;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysReportMapper extends BaseMapper<SysReport> {


    /**
     * 找到管理系统报告
     *
     * @param queryWrapper  查询规则
     * @param sysReportPage 分页
     * @return {@link List<SysReport> }
     * @author WeiQiangMiao
     * @date 2021-03-17
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysReport> findAdminSystemReport(
            @Param(Constants.WRAPPER) QueryWrapper<SysReport> queryWrapper,
            IPage<SysReport> sysReportPage
    );




}