package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.framework.model.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @description: 系统用户
 * @author: richenLi
 * @create: 2021-03-01 16:51
 **/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    List<SysUser> findall();
    
    String findUserNameById(@Param("id") Integer id);
    
    Integer findUserStatusById(@Param("id") Integer id);
    
    
    List<Integer> findId();
    
    /**
     * 找到用户账户名称
     *
     * @param userAccountName 用户账户名称
     * @return {@link List<SysUser> }
     * @author WeiQiangMiao
     * @date 2021-03-03
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    SysUser findByUserAccountName(@Param("userAccoutName") String userAccountName);
    
    /**
     * 根据用户编码找用户
     */
    SysUser findByUserCode(@Param("userCode") String userCode);
    
    
    /**
     * 按用户状态和用户有效时间和用户删除状态查询
     *
     * @param userAccountName  用户账户名称
     * @param userStatus       用户状态
     * @param userDeleteStatus 用户删除状态
     * @param maxUserValidTime 最大用户有效时间
     * @return {@link List<SysUser> }
     * @author WeiQiangMiao
     * @date 2021-03-03
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    SysUser findByUserAccountNameAndUserStatusAndUserDeleteStatusAndUserValidTimeLessThanEqual(
            @Param("userAccountName") String userAccountName,
            @Param("userStatus") Integer userStatus,
            @Param("userDeleteStatus") Boolean userDeleteStatus,
            @Param("maxUserValidTime") Date maxUserValidTime);
    
    /**
     * 按用户账号和用户删除状态查询
     *
     * @param userAccountName  用户账户名称
     * @param userDeleteStatus 用户删除状态
     * @return {@link List<SysUser> }
     * @author WeiQiangMiao
     * @date 2021-03-03
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    SysUser findByUserAccountNameAndUserDeleteStatus(
            @Param("userAccountName") String userAccountName,
            @Param("userDeleteStatus") Integer userDeleteStatus);
    
    @Select("select * from sys_user  ${ew.customSqlSegment}")
    List<SysUser> getAllSysUser(@Param(Constants.WRAPPER) QueryWrapper<SysDict> queryWrapper);
    
    /**
     * 更新用户安全电子邮件id
     *
     * @param id id
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateUserSafeEmailById(@Param("id") Integer id);
    
    /**
     * 更新用户安全电话通过id
     *
     * @param id id
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateUserSafePhoneById(@Param("id") Integer id);
    
    
    List<Integer> findIdByUserCode(@Param("userCode") String userCode);
    
    List<Integer> findIdByUserPhone(@Param("userPhone") String userPhone);
    
    List<Integer> findIdByUserAccountName(@Param("userAccountName") String userAccountName);
    
    List<Integer> findIdByUserName(@Param("userName") String userName);
    
    List<Integer> findIdByUserEmail(@Param("userEmail") String userEmail);
    
    void unbindEmail(@Param("userId") int userId, @Param("update") Date update);
    
    void unbindPhone(@Param("userId") int userId, @Param("update") Date update);
    
    IPage<SysUser> queryPage(@Param(Constants.WRAPPER) QueryWrapper<SysUser> queryWrapper, IPage<SysUser> iPage);
    
    /**
     * 数由用户状态和用户删除状态
     *
     * @param userStatus       用户状态
     * @param userDeleteStatus 用户删除状态
     * @return {@link Integer }
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    Integer countByUserStatusAndUserDeleteStatus(@Param("userStatus") Integer userStatus, @Param("userDeleteStatus") Integer userDeleteStatus);
    
    /**
     * 按系统用户绑定的手机号和用户删除状态查询
     *
     * @param userSafePhone    系统用户绑定的手机号
     * @param userDeleteStatus 用户删除状态
     * @return {@link List<SysUser> }
     * @author WeiQiangMiao
     * @date 2021-03-03
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    SysUser findByUserSafePhoneAndUserDeleteStatus(
            @Param("userSafePhone") String userSafePhone,
            @Param("userDeleteStatus") Integer userDeleteStatus);
    
    
    List<SysUser> findByUserPhone(@Param("userPhone") String userPhone);
    
    List<SysUser> findByUserEmail(@Param("userEmail") String userEmail);
    
    
    /**
     * 找到用户安全的电话
     *
     * @param userSafePhone 用户安全的电话
     * @return {@link List<SysUser> }
     * @author WeiQiangMiao
     * @date 2021-04-30
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysUser> findByUserSafePhone(@Param("userSafePhone") String userSafePhone);
    
    List<SysUser> findByUserSafeEmail(@Param("userSafeEmail") String userSafeEmail);
    
    
    SysUser findByUserAccountNameAndUserStatusAndUserDeleteStatus(@Param("userAccountName") String userAccountName, @Param("userStatus") Integer userStatus, @Param("userDeleteStatus") Integer userDeleteStatus);
    
    
}