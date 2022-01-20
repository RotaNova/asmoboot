package com.rotanava.boot.system.module.dao;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysTableField;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysTableFieldMapper extends BaseMapper<SysTableField> {


    /**
     * 发现表配置id的
     *
     * @param tableConfigIdCollection 表配置id集合
     * @return {@link List<SysTableField> }
     * @author weiqiangmiao
     * @date 2021/06/26
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysTableField> findByTableConfigIdIn(@Param("tableConfigIdCollection")Collection<Integer> tableConfigIdCollection);

    /**
     * 发现表配置id
     *
     * @param tableConfigId 表配置id
     * @return {@link List<SysTableField> }
     * @author weiqiangmiao
     * @date 2021/06/26
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysTableField> findByTableConfigId(@Param("tableConfigId")Integer tableConfigId);

    /**
     * 更新通过id和表配置id
     *
     * @param updated       更新
     * @param id            id
     * @param tableConfigId 表配置id
     * @return int
     * @author weiqiangmiao
     * @date 2021/06/28
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateByIdAndTableConfigId(@Param("updated")SysTableField updated,@Param("id")Integer id,@Param("tableConfigId")Integer tableConfigId);

    /**
     * 插入或更新列表
     *
     * @param list 列表
     * @return int
     * @author weiqiangmiao
     * @date 2021/07/05
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int insertListOrUpdate(@Param("list")List<SysTableField> list);


}