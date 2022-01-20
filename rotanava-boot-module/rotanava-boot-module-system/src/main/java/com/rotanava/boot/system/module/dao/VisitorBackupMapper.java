package com.rotanava.boot.system.module.dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.VisitorBackup;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author WeiQiangMiao
 * @since 2021-04-06
 */
@Mapper
public interface VisitorBackupMapper extends BaseMapper<VisitorBackup> {

    /**
     * 之间找到创建时间相等
     *
     * @param minCreateTime 分钟创建时间
     * @param maxCreateTime 最大创建时间
     * @return {@link List<VisitorBackup> }
     * @author WeiQiangMiao
     * @date 2021-04-06
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<VisitorBackup> findByCreateTimeBetweenEqual(@Param("minCreateTime")String minCreateTime,@Param("maxCreateTime")String maxCreateTime);



}
