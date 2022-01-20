package com.rotanava.boot.system.module.dao;
import org.apache.ibatis.annotations.Param;
import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysTimingTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author WeiQiangMiao
 * @since 2021-04-20
 */
@Mapper
public interface SysTimingTaskMapper extends BaseMapper<SysTimingTask> {

    int deleteByTimingLessThanEqual(@Param("maxTiming")Date maxTiming);


}
