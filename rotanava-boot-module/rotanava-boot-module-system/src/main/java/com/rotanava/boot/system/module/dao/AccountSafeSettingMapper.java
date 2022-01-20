package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.AccountSafeSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface AccountSafeSettingMapper extends BaseMapper<AccountSafeSetting> {

    /**
     * 找到系统的用户id
     *
     * @param sysUserId 系统的用户id
     * @return {@link List<AccountSafeSetting> }
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    AccountSafeSetting findBySysUserId(@Param("sysUserId") Integer sysUserId);

    /**
     * 更新系统用户id
     *
     * @param updated   更新
     * @param sysUserId 系统的用户id
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateBySysUserId(@Param("updated") AccountSafeSetting updated, @Param("sysUserId") Integer sysUserId);

    int deleteBySysUserIdIn(@Param("sysUserIdCollection") Collection<Integer> sysUserIdCollection);

    int updateEmailSafeTypeBySysUserId(@Param("updatedEmailSafeType")Integer updatedEmailSafeType,@Param("sysUserId")Integer sysUserId);

    int updatePhoneSafeTypeBySysUserId(@Param("updatedPhoneSafeType")Integer updatedPhoneSafeType,@Param("sysUserId")Integer sysUserId);


}