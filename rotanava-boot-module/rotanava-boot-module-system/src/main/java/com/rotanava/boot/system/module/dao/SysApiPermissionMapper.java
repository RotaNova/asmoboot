package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bean.SysApiBean;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysApiPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysApiPermissionMapper extends BaseMapper<SysApiPermission> {

    /**
     * 按用户id查询
     *
     * @param  sysUserId 用户id
     * @return {@link List<SysApiPermission> }
     * @author WeiQiangMiao
     * @date 2021-03-05
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysApiPermission> findBySysUserId(Integer sysUserId);


    /**
     *
     * @return {@link List<SysApiBean> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysApiBean> findSysApiBean(
            @Param(Constants.WRAPPER) QueryWrapper<SysApiPermission> queryWrapper,
            IPage<SysApiPermission> sysReportPage
    );


    /**
     * 按请求方式和路径查询
     *
     * @param apiUrl    api的url
     * @param apiMethod api方法
     * @return {@link List<SysApiPermission> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysApiPermission> findByApiUrlAndApiMethod(@Param("apiUrl")String apiUrl,@Param("apiMethod")Integer apiMethod);

    Integer countByApiUrl(@Param("apiUrl")String apiUrl);



}