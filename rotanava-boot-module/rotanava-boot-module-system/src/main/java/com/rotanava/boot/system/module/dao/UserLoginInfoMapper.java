package com.rotanava.boot.system.module.dao;

import java.util.Date;
import java.util.Collection;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.vo.AccessVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.UserLoginInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户登录信息
 * </p>
 *
 * @author WeiQiangMiao
 * @since 2021-03-04
 */
@Mapper
public interface UserLoginInfoMapper extends BaseMapper<UserLoginInfo> {

    /**
     * 找到最新的登录用户id
     *
     * @param tokenCollection   token集合
     * @param loginName         登录名
     * @param loginIp           登录知识产权
     * @param loginLocation     登录的位置
     * @param beginTime         开始时间
     * @param endTime           结束时间
     * @param userLoginInfoPage 用户登录信息页面
     * @return {@link List<UserLoginInfo> }
     * @author WeiQiangMiao
     * @date 2021-03-11
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<UserLoginInfo> findLatestByLoginUserIdIn(
            @Param("tokenCollection") Collection<String> tokenCollection,
            @Param("loginName") String loginName,
            @Param("loginIp") String loginIp,
            @Param("loginLocation") String loginLocation,
            @Param("beginTime") Long beginTime,
            @Param("endTime") Long endTime,
            IPage<UserLoginInfo> userLoginInfoPage
    );

    /**
     * 更新离线时间通过id
     *
     * @param updatedOfflineTime 离线更新时间
     * @param idCollection       id集合
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-24
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updateOfflineTimeByIdIn(@Param("updatedOfflineTime") Date updatedOfflineTime, @Param("idCollection") Collection<Long> idCollection);


    /**
     * 之间找到登录用户id和登录时间相等
     *
     * @param loginUserId  登录的用户id
     * @param minLoginTime 分钟登录时间
     * @param maxLoginTime 最大的登录时间
     * @return {@link List<UserLoginInfo> }
     * @author WeiQiangMiao
     * @date 2021-03-31
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<UserLoginInfo> findByLoginUserIdAndLoginTimeBetweenEqual(@Param("loginUserId") Integer loginUserId, @Param("minLoginTime") Date minLoginTime, @Param("maxLoginTime") Date maxLoginTime);


    /**
     * 计数某天的访客量
     *
     * @param loginStatus  登录状态
     * @param minLoginTime 最早的登录时间
     * @param maxLoginTime 最晚的登录时间
     * @return {@link Integer }
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<Long> findVisitorVolume(@Param("loginStatus") Integer loginStatus, @Param("minLoginTime") Date minLoginTime, @Param("maxLoginTime") Date maxLoginTime);


    /**
     * 计数登录频率
     *
     * @param loginStatus  登录状态
     * @param minLoginTime 最早的登录时间
     * @param maxLoginTime 最晚的登录时间
     * @return {@link Integer }
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    Long countLoginFrequency(@Param("loginStatus") Integer loginStatus, @Param("minLoginTime") Date minLoginTime, @Param("maxLoginTime") Date maxLoginTime);

    /**
     * 查询登录平均时长
     *
     * @param loginStatus 登录状态
     * @param minTime     最早的时间
     * @param maxTime     最晚的时间
     * @return {@link List<UserLoginInfo> }
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<UserLoginInfo> findAverageUsageTime(
            @Param("loginStatus") Integer loginStatus,
            @Param("minTime") Date minTime,
            @Param("maxTime") Date maxTime);

    /**
     * 找到访问统计信息
     *
     * @param loginStatus 登录状态
     * @param minTime     分钟时间
     * @param maxTime     最大时间
     * @return {@link List<String> }
     * @author WeiQiangMiao
     * @date 2021-04-06
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<AccessVO> findAccessStatistics(
            @Param("loginStatus") Integer loginStatus,
            @Param("minTime") Date minTime,
            @Param("maxTime") Date maxTime);

    /**
     * 之间找到登录状态和登录时间相等
     *
     * @param loginStatus  登录状态
     * @param minLoginTime 分钟登录时间
     * @param maxLoginTime 最大的登录时间
     * @return {@link List<UserLoginInfo> }
     * @author WeiQiangMiao
     * @date 2021-04-06
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<UserLoginInfo> findByLoginStatusAndLoginTimeBetweenEqual(@Param("loginStatus")Integer loginStatus,@Param("minLoginTime")Date minLoginTime,@Param("maxLoginTime")Date maxLoginTime);

    List<AccessVO> findByAccessSourceStatistics(@Param("loginStatus")Integer loginStatus,@Param("minLoginTime")Date minLoginTime,@Param("maxLoginTime")Date maxLoginTime);

    List<AccessVO> findByAccessDeviceStatistics(@Param("loginStatus")Integer loginStatus,@Param("minLoginTime")Date minLoginTime,@Param("maxLoginTime")Date maxLoginTime);

    UserLoginInfo findByLoginUserIdOrderByLoginTimeDescLimit(@Param("loginUserId")Integer loginUserId);



}
