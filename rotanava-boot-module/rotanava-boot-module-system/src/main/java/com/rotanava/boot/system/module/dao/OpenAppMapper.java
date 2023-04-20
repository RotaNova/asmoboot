package com.rotanava.boot.system.module.dao;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.OpenApp;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 14:48
 */
public interface OpenAppMapper extends BaseMapper<OpenApp> {

    Integer countByAppName(@Param("appName") String appName);

    void updateSetIsSwitch(@Param("id") Integer id);
    /**
     * 发现通过代理id和应用关键和应用的秘密
     *
     * @param agentId   代理人身份证
     * @param appKey    应用的关键
     * @param appSecret 应用程序的秘密
     * @return {@link List<OpenApp> }
     * @author weiqiangmiao
     * @date 2021/07/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    OpenApp findByAgentIdAndAppKeyAndAppSecret(@Param("agentId")String agentId,@Param("appKey")String appKey,@Param("appSecret")String appSecret);


}