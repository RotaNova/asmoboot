package com.rotanava.boot.system.module.dao;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysServiceSettingMapper extends BaseMapper<SysServiceSetting> {

    /**
     * 找到系统服务名称
     *
     * @param sysServiceName 系统服务名称
     * @return {@link List<SysServiceSetting> }
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    SysServiceSetting findBySysServiceName(@Param("sysServiceName") String sysServiceName);

    SysServiceSetting findBySysServiceCode(@Param("sysServiceCode") String sysServiceCode);

    /**
     * 找到系统服务类型
     *
     * @param sysServiceType 系统服务类型
     * @return {@link List<SysServiceSetting> }
     * @author WeiQiangMiao
     * @date 2021-03-23
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysServiceSetting> findBySysServiceType(@Param("sysServiceType") Integer sysServiceType);


    /**
     * 更新系统服务代码
     *
     * @param map 地图
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-23
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateManageSecurityByMap(@Param("map") Map<String, Object> map);

    /**
     * 更新LDAP
     *
     * @param map 地图
     * @return int
     * @author WeiQiangMiao
     * @date 2021-04-23
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateLdapByMap(@Param("map") Map<String, Object> map);


    String findSysServiceValueBySysServiceCode(@Param("sysServiceCode") String sysServiceCode);
    
    
    void deleteByServiceCode(@Param("sysServiceCode") String sysServiceCode);

    int updateMqttSetByMap(@Param("map") Map<String, Object> map);

    List<String> findSysServiceCodeBySysServiceCodeIn(@Param("sysServiceCodeCollection")Collection<String> sysServiceCodeCollection);

    int insertList(@Param("list")List<SysServiceSetting> list);




}