package com.rotanava.boot.system.module.dao;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysOnlineUser;


/**
 * 系统在线用户映射器
 *
 * @author WeiQiangMiao
 * @date 2021-03-16
 */
public interface SysOnlineUserMapper extends BaseMapper<SysOnlineUser> {

    /**
     * 删除系统用户令牌
     *
     * @param sysUserTokenCollection 系统收集用户令牌
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-16
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int deleteBySysUserTokenInNot(@Param("sysUserTokenCollection")Collection<String> sysUserTokenCollection);

    /**
     * 查询 排除 用户token 的信息
     *
     * @param sysUserTokenCollection 系统收集用户令牌
     * @return {@link List<Integer> }
     * @author WeiQiangMiao
     * @date 2021-03-18
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysOnlineUser> findSysUserTokenBySysUserTokenInNot(@Param("sysUserTokenCollection")Collection<String> sysUserTokenCollection);


    /**
     * 找到系统用户令牌由系统用户令牌
     *
     * @param notSysUserToken 不是系统用户令牌
     * @return {@link List<String> }
     * @author WeiQiangMiao
     * @date 2021-03-24
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    String findSysUserTokenBySysUserTokenNot(@Param("notSysUserToken")String notSysUserToken);


    /**
     * 找到系统用户令牌
     *
     * @param sysUserToken 系统用户令牌
     * @return {@link List<SysOnlineUser> }
     * @author WeiQiangMiao
     * @date 2021-03-24
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    SysOnlineUser findBySysUserToken(@Param("sysUserToken")String sysUserToken);


    /**
     * 删除系统用户令牌
     *
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-24
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int deleteBySysUserToken(@Param("sysUserToken")String sysUserToken);


    /**
     * 找到的用户帐户名
     *
     * @param userAccountName 用户帐户名
     * @return {@link List<SysOnlineUser> }
     * @author WeiQiangMiao
     * @date 2021-05-10
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysOnlineUser> findByUserAccountName(@Param("userAccountName")String userAccountName);


}
