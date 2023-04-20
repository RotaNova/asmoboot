package com.rotanava.boot.system.module.system.task;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.DataCockpitService;
import com.rotanava.boot.system.api.module.constant.LoginStatus;
import com.rotanava.boot.system.api.module.system.bo.UserLoginInfo;
import com.rotanava.boot.system.api.module.system.bo.VisitorBackup;
import com.rotanava.boot.system.api.module.system.vo.VisitorVolumeVO;
import com.rotanava.boot.system.module.dao.UserLoginInfoMapper;
import com.rotanava.boot.system.module.dao.VisitorBackupMapper;
import com.rotanava.boot.system.module.system.impl.DataCockpitServiceImpl;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


/**
 * 统计任务
 *
 * @author WeiQiangMiao
 * @date 2021-04-06
 */
@Slf4j
@Component
public class StatisticsTask {

    @Autowired
    private DataCockpitService dataCockpitService;
    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;
    @Autowired
    private VisitorBackupMapper visitorBackupMapper;

    @XxlJob(value = "timingStatistics")
    public ReturnT<String> timingStatistics(String data) {

        final DateTime dateTime = DateUtil.yesterday();
        final DateTime minLoginTime = DateUtil.beginOfDay(dateTime);
        final DateTime maxLoginTime = DateUtil.endOfDay(dateTime);

        List<UserLoginInfo> userLoginInfos = userLoginInfoMapper.findByLoginStatusAndLoginTimeBetweenEqual(LoginStatus.SUCCESS.getStatus(), minLoginTime, maxLoginTime);
        //查询访客量
        Long count = userLoginInfos.stream().map(UserLoginInfo::getLoginUserId).distinct().count();
        //查询某天平均使用时间
        long todayAverageUsageTime = getAverageUsageTime(userLoginInfos);

        long minUsageTime = getLongStream(userLoginInfos).min().orElse(0L);
        long maxUsageTime = getLongStream(userLoginInfos).max().orElse(0L);
        visitorBackupMapper.insert(new VisitorBackup( count, todayAverageUsageTime, minUsageTime, maxUsageTime, dateTime));

        return ReturnT.SUCCESS;
    }

    private static LongStream getLongStream(List<UserLoginInfo> todayUserLoginInfos) {
        return todayUserLoginInfos.stream().mapToLong(
                info -> {
                    long endOfDay = DateUtil.endOfDay(info.getLoginTime()).getTime();
                    long offlineTime = info.getOfflineTime() == null ? Math.min(System.currentTimeMillis(), endOfDay) : Math.min(info.getOfflineTime().getTime(), endOfDay);
                    if( offlineTime < info.getLoginTime().getTime() ){ return 1; }
                    return BigDecimal.valueOf(NumberUtil.div(offlineTime - info.getLoginTime().getTime(), DateUnit.MINUTE.getMillis())
                    ).setScale(0, BigDecimal.ROUND_CEILING).longValue();
                }
        );
    }

    public static long getAverageUsageTime(List<UserLoginInfo> todayUserLoginInfos) {

        return BigDecimal.valueOf(getLongStream(todayUserLoginInfos).average().orElse(-0.1)).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
    }

}
