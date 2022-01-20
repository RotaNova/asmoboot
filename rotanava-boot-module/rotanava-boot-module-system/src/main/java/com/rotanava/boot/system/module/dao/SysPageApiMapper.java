package com.rotanava.boot.system.module.dao;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysPageApi;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysPageApiMapper extends BaseMapper<SysPageApi> {

    /**
     * 按系统接口id删除
     *
     * @param sysApiId 系统接口id
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int deleteBySysApiId(@Param("sysApiId")Integer sysApiId);

    /**
     * 按接口id页面id查询
     *
     * @param sysApiId  sys api id
     * @param sysPageId 系统页面id
     * @return {@link List<SysPageApi> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPageApi> findBySysApiIdAndSysPageId(@Param("sysApiId")Integer sysApiId,@Param("sysPageId")Integer sysPageId);


    /**
     * 按接口资源id修改页面资源id
     *
     * @param updatedSysPageId 更新系统页面id
     * @param sysApiId         sys api id
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateSysPageIdBySysApiId(@Param("updatedSysPageId")Integer updatedSysPageId,@Param("sysApiId")Integer sysApiId);

    /**
     * 按接口id查询
     *
     * @param sysApiId 接口id
     * @return {@link List<SysPageApi> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    SysPageApi findBySysApiId(@Param("sysApiId")Integer sysApiId);

    /**
     * 按接口id查询
     *
     * @param idCollection 接口id集合
     * @return {@link List<SysPageApi> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPageApi> findByIdIn(@Param("idCollection")Collection<Integer> idCollection);

    /**
     * 按系统接口id集合删除
     *
     * @param sysApiIdCollection sys api id集合
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int deleteBySysApiIdIn(@Param("sysApiIdCollection")Collection<Integer> sysApiIdCollection);

    /**
     * 删除系统页面id
     *
     * @param sysPageIdCollection 系统页面id集合
     * @return int
     * @author WeiQiangMiao
     * @date 2021-04-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int deleteBySysPageIdIn(@Param("sysPageIdCollection")Collection<Integer> sysPageIdCollection);

    /**
     * 发现通过系统页面id
     *
     * @param sysPageIdCollection 系统页面id集合
     * @return {@link List<SysPageApi> }
     * @author weiqiangmiao
     * @date 2021/07/16
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPageApi> findBySysPageIdIn(@Param("sysPageIdCollection")Collection<Integer> sysPageIdCollection);


    Integer countBySysApiId(@Param("sysApiId")Integer sysApiId);



}