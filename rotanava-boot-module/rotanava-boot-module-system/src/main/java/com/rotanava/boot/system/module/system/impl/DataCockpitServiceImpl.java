package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.*;
import cn.hutool.core.util.NumberUtil;
import com.ejlchina.okhttps.HTTP;
import com.rotanava.boot.system.api.DataCockpitService;
import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.module.constant.*;
import com.rotanava.boot.system.api.module.system.bo.UserLoginInfo;
import com.rotanava.boot.system.api.module.system.bo.VisitorBackup;
import com.rotanava.boot.system.api.module.system.dto.StatisticsDTO;
import com.rotanava.boot.system.api.module.system.vo.*;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.boot.system.module.dao.UserLoginInfoMapper;
import com.rotanava.boot.system.module.dao.VisitorBackupMapper;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.constant.enums.UserStatus;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.util.Date8Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据座舱服务实现类
 *
 * @author WeiQiangMiao
 * @date 2021-04-01
 */
@Service
public class DataCockpitServiceImpl implements DataCockpitService {

    public static final long CONTINUOUS_WORKING_PERIOD = System.currentTimeMillis();

    @Value("${system.monitoring.type}")
    private Integer monitoring;

    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysPlatformDeployService sysPlatformDeployService;
    @Autowired
    private VisitorBackupMapper visitorBackupMapper;


    @Override
    public DataCockpitVO getDataCockpit() {

        TodayData todayData = getTrafficStatistics(new Date());
        DateTime newBeginOfWeek = DateUtil.beginOfWeek(new Date());
        DateTime newEndOfWeek = DateUtil.endOfWeek(new Date());
        DateTime offsetWeek = DateUtil.offsetWeek(new Date(), -1);
        DateTime oldBeginOfWeek = DateUtil.beginOfWeek(offsetWeek);
        DateTime oldEndOfWeek = DateUtil.endOfWeek(offsetWeek);
        VisitorVolumeVO weeklyVisitorStatistics = getVisitorVolumeVO(newBeginOfWeek, newEndOfWeek, oldBeginOfWeek, oldEndOfWeek);

        DateTime newBeginOfMonth = DateUtil.beginOfMonth(new Date());
        DateTime newEndOfMonth = DateUtil.endOfMonth(new Date());
        DateTime offsetMonth = DateUtil.offsetMonth(new Date(), -1);
        DateTime oldBeginOfMonth = DateUtil.beginOfMonth(offsetMonth);
        DateTime oldEndOfMonth = DateUtil.endOfMonth(offsetMonth);
        VisitorVolumeVO monthVisitorStatistics = getVisitorVolumeVO(newBeginOfMonth, newEndOfMonth, oldBeginOfMonth, oldEndOfMonth);


        List<VisitorVolumeAndContrastRateVO> sevenDaysVisitorStatistics = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_YEAR, -6);
        List<Calendar> dayCalendars = Date8Util.between(instance.getTimeInMillis(), System.currentTimeMillis(), Calendar.DAY_OF_YEAR, 1);
        for (Calendar calendar : dayCalendars) {
            DateTime newBeginOfDay = DateUtil.beginOfDay(calendar.getTime());
            DateTime newEndOfDay = DateUtil.endOfDay(calendar.getTime());
            DateTime dateTime = DateUtil.offsetDay(calendar.getTime(), -1);
            DateTime oldBeginOfDay = DateUtil.beginOfDay(dateTime);
            DateTime oldEndOfDay = DateUtil.endOfDay(dateTime);

            VisitorVolumeVO visitorStatistics = getVisitorVolumeVO(newBeginOfDay, newEndOfDay, oldBeginOfDay, oldEndOfDay);
            sevenDaysVisitorStatistics.add(new VisitorVolumeAndContrastRateVO(visitorStatistics.getNewVisitorVolume(), DateUtil.format(calendar.getTime(), "MM.dd"), visitorStatistics.getVisitorVolumeContrastRate()));

        }


        DataCockpitVO dataCockpitVO = new DataCockpitVO();
        dataCockpitVO.setTodayData(todayData);
        dataCockpitVO.setWeekVisitorStatistics(new VisitorVolumeAndContrastRateVO(weeklyVisitorStatistics.getNewVisitorVolume(), weeklyVisitorStatistics.getVisitorVolumeContrastRate()));
        dataCockpitVO.setMonthVisitorStatistics(new VisitorVolumeAndContrastRateVO(monthVisitorStatistics.getNewVisitorVolume(), monthVisitorStatistics.getVisitorVolumeContrastRate()));
        dataCockpitVO.setSevenDaysVisitorStatistics(sevenDaysVisitorStatistics);

        List<AccessVO> weekAccessStatistics = userLoginInfoMapper.findAccessStatistics(LoginStatus.SUCCESS.getStatus(), newBeginOfWeek, new Date());
        List<AccessVO> monthAccessStatistics = userLoginInfoMapper.findAccessStatistics(LoginStatus.SUCCESS.getStatus(), newBeginOfMonth, new Date());
        List<AccessVO> weekAccessSourceStatistics = userLoginInfoMapper.findByAccessSourceStatistics(LoginStatus.SUCCESS.getStatus(), newBeginOfWeek, new Date());
        List<AccessVO> monthAccessSourceStatistics = userLoginInfoMapper.findByAccessSourceStatistics(LoginStatus.SUCCESS.getStatus(), newBeginOfMonth, new Date());
        List<AccessVO> weekAccessDeviceStatistics = userLoginInfoMapper.findByAccessDeviceStatistics(LoginStatus.SUCCESS.getStatus(), newBeginOfWeek, new Date());
        List<AccessVO> monthAccessDeviceStatistics = userLoginInfoMapper.findByAccessDeviceStatistics(LoginStatus.SUCCESS.getStatus(), newBeginOfMonth, new Date());

        for (int i = 0; i < weekAccessStatistics.size(); i++) {
            weekAccessStatistics.get(i).setRank(Convert.toLong(i + 1));
        }
        for (int i = 0; i < monthAccessStatistics.size(); i++) {
            monthAccessStatistics.get(i).setRank(Convert.toLong(i + 1));
        }
        for (int i = 0; i < weekAccessSourceStatistics.size(); i++) {
            weekAccessSourceStatistics.get(i).setRank(Convert.toLong(i + 1));
            if (StringUtils.isEmpty(weekAccessSourceStatistics.get(i).getName()) || "Unknown" .equals(weekAccessSourceStatistics.get(i).getName())) {
                weekAccessSourceStatistics.get(i).setName("未知");
            }
        }
        for (int i = 0; i < monthAccessSourceStatistics.size(); i++) {
            monthAccessSourceStatistics.get(i).setRank(Convert.toLong(i + 1));
            if (StringUtils.isEmpty(monthAccessSourceStatistics.get(i).getName()) || "Unknown" .equals(monthAccessSourceStatistics.get(i).getName())) {
                monthAccessSourceStatistics.get(i).setName("未知");
            }
        }
        for (int i = 0; i < weekAccessDeviceStatistics.size(); i++) {
            weekAccessDeviceStatistics.get(i).setRank(Convert.toLong(i + 1));
            weekAccessDeviceStatistics.get(i).setName(OsType.findByOs(weekAccessDeviceStatistics.get(i).getName()).getDescription());
        }
        for (int i = 0; i < monthAccessDeviceStatistics.size(); i++) {
            monthAccessDeviceStatistics.get(i).setRank(Convert.toLong(i + 1));
            monthAccessDeviceStatistics.get(i).setName(OsType.findByOs(monthAccessDeviceStatistics.get(i).getName()).getDescription());
        }

        dataCockpitVO.setWeekAccessStatistics(weekAccessStatistics);
        dataCockpitVO.setMonthAccessStatistics(monthAccessStatistics);
        dataCockpitVO.setWeekAccessSourceStatistics(weekAccessSourceStatistics);
        dataCockpitVO.setMonthAccessSourceStatistics(monthAccessSourceStatistics);
        dataCockpitVO.setWeekAccessDeviceStatistics(weekAccessDeviceStatistics);
        dataCockpitVO.setMonthAccessDeviceStatistics(monthAccessDeviceStatistics);
        if (monitoring == 1) {
            long clusterDiskSizeCapacity = 0L;
            long clusterDiskSizeAvailable = 0L;
            List<ClusterResultsVO> clusterResults = sysPlatformDeployService.getCluster(CommonConstant.SYSTEM_OAUTH_TOKEN);
            for (ClusterResultsVO clusterResult : clusterResults) {
                ClusterDataVO data = clusterResult.getData();
                List<ClusterResultVO> result = data.getResult();
                ClusterResultVO clusterResultVO = result.get(0);
                List<BigDecimal> value = clusterResultVO.getValue();

                if (Cluster.CLUSTER_DISK_SIZE_CAPACITY.getSign().equals(clusterResult.getMetric_name())) {
                    clusterDiskSizeCapacity = value.get(1).longValue();
                }
                if (Cluster.CLUSTER_DISK_SIZE_AVAILABLE.getSign().equals(clusterResult.getMetric_name())) {
                    clusterDiskSizeAvailable = value.get(1).longValue();
                }
            }
            dataCockpitVO.setDiskUsage(String.format("%s/%s", clusterDiskSizeCapacity - clusterDiskSizeAvailable, clusterDiskSizeCapacity));
        }

        if (monitoring == 0) {
            HTTP http = sysPlatformDeployService.getHttpClient(CommonConstant.SYSTEM_DISK_USAGE);
            dataCockpitVO.setDiskUsage(http.async(CommonConstant.SYSTEM_DISK_USAGE).get().getResult().getBody().toString());
        }
        dataCockpitVO.setTodayPageViews(todayData.getLoginFrequency());
        dataCockpitVO.setOperatingTime(System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime());
        return dataCockpitVO;
    }

    @Override
    public List<VisitorVolumeAndCountVO> getVisitorVolumeStatistics(StatisticsDTO statisticsDTO) {
        DateTime startTime = new DateTime(statisticsDTO.getStartTime());
        DateTime endTime = new DateTime(statisticsDTO.getEndTime());

        StatisticsUnit statisticsUnit = StatisticsUnit.findByUnit(statisticsDTO.getUnit());

        if (statisticsUnit == null) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        } else if (statisticsDTO.getStartTime() > System.currentTimeMillis() || statisticsDTO.getEndTime() > System.currentTimeMillis()
                || DateUtil.isSameDay(startTime, new Date()) || DateUtil.isSameDay(startTime, new Date())
        ) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }


        List<VisitorVolumeAndCountVO> visitorVolumeAndCountVOList = new ArrayList<>();
        switch (statisticsUnit) {
            case DAY:

                Map<String, VisitorBackup> dayVisitorBackupMap = visitorBackupMapper.findByCreateTimeBetweenEqual(DateUtil.formatDate(startTime), DateUtil.formatDate(endTime))
                        .stream().collect(Collectors.toMap(visitorBackup -> DateUtil.format(visitorBackup.getCreateTime(), "yyyy-MM-dd"), Function.identity()));

                List<Calendar> dayCalendars = Date8Util.between(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), Calendar.DAY_OF_YEAR, 1);
                for (Calendar calendar : dayCalendars) {
                    String formatDate = DateUtil.format(calendar.getTime(), "yyyy-MM-dd");
                    VisitorBackup visitorBackup = dayVisitorBackupMap.get(formatDate);
                    if (visitorBackup != null) {
                        visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(visitorBackup.getVisitorVolume(), formatDate));
                        continue;
                    }
                    visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(0L, formatDate));
                }

                return visitorVolumeAndCountVOList;
            case WEEK:

                List<Calendar> weekCalendars = Date8Util.between(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), Calendar.WEEK_OF_YEAR, 1);
                for (Calendar calendar : weekCalendars) {
                    DateTime beginOfWeek = DateUtil.beginOfWeek(calendar.getTime());
                    DateTime endOfWeek = DateUtil.endOfWeek(calendar.getTime());
                    List<VisitorBackup> weekVisitorBackups = getVisitorBackups(statisticsDTO, startTime, endTime, weekCalendars, calendar, beginOfWeek, endOfWeek);
                    String week = String.format("%sW%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR));
                    if (!CollectionUtils.isEmpty(weekVisitorBackups)) {
                        long weekCount = weekVisitorBackups.stream().mapToLong(VisitorBackup::getVisitorVolume).sum();
                        visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(weekCount, week));
                        continue;
                    }
                    visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(0L, week));
                }

                return visitorVolumeAndCountVOList;
            case MONTH:

                List<Calendar> monthCalendars = Date8Util.between(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), Calendar.MONTH, 1);
                for (Calendar calendar : monthCalendars) {
                    DateTime beginOfMonth = DateUtil.beginOfMonth(calendar.getTime());
                    DateTime endOfMonth = DateUtil.endOfMonth(calendar.getTime());
                    List<VisitorBackup> weekVisitorBackups = getVisitorBackups(statisticsDTO, startTime, endTime, monthCalendars, calendar, beginOfMonth, endOfMonth);
                    String month = String.format("%s.%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
                    if (!CollectionUtils.isEmpty(weekVisitorBackups)) {
                        long weekCount = weekVisitorBackups.stream().mapToLong(VisitorBackup::getVisitorVolume).sum();
                        visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(weekCount, month));
                        continue;
                    }
                    visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(0L, month));
                }

                return visitorVolumeAndCountVOList;
            case QUARTERLY:

                List<Calendar> quarterlyCalendars = Date8Util.between(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), Calendar.MONTH, 3);
                for (Calendar calendar : quarterlyCalendars) {
                    DateTime beginOfQuarter = DateUtil.beginOfQuarter(calendar.getTime());
                    DateTime endOfQuarter = DateUtil.endOfQuarter(calendar.getTime());

                    List<VisitorBackup> monthVisitorBackups = getVisitorBackups(statisticsDTO, startTime, endTime, quarterlyCalendars, calendar, beginOfQuarter, endOfQuarter);
                    String quarterly = String.format("%sQ%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) / 3 + 1);
                    if (!CollectionUtils.isEmpty(monthVisitorBackups)) {
                        long weekCount = monthVisitorBackups.stream().mapToLong(VisitorBackup::getVisitorVolume).sum();
                        visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(weekCount, quarterly));
                        continue;
                    }
                    visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(0L, quarterly));
                }

                return visitorVolumeAndCountVOList;
            case YEAR:

                List<Calendar> yearCalendars = Date8Util.between(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), Calendar.YEAR, 1);
                for (Calendar calendar : yearCalendars) {
                    DateTime beginOfYear = DateUtil.beginOfYear(calendar.getTime());
                    DateTime endOfYear = DateUtil.endOfYear(calendar.getTime());
                    List<VisitorBackup> monthVisitorBackups = getVisitorBackups(statisticsDTO, startTime, endTime, yearCalendars, calendar, beginOfYear, endOfYear);

                    String year = String.format("%s", calendar.get(Calendar.YEAR));
                    if (!CollectionUtils.isEmpty(monthVisitorBackups)) {
                        long weekCount = monthVisitorBackups.stream().mapToLong(VisitorBackup::getVisitorVolume).sum();
                        visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(weekCount, year));
                        continue;
                    }
                    visitorVolumeAndCountVOList.add(new VisitorVolumeAndCountVO(0L, year));
                }

                return visitorVolumeAndCountVOList;
            default:
                throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }

    }


    @Override
    public List<UsageTimeVO> getUsageTimeStatistics(StatisticsDTO statisticsDTO) {

        Map<String, VisitorBackup> dayVisitorBackupMap = visitorBackupMapper.findByCreateTimeBetweenEqual(DateUtil.formatDate(new Date(statisticsDTO.getStartTime())), DateUtil.formatDate(new Date(statisticsDTO.getEndTime())))
                .stream().collect(Collectors.toMap(visitorBackup -> DateUtil.format(visitorBackup.getCreateTime(), "MM.dd"), Function.identity()));

        List<UsageTimeVO> usageTimeVOList = new ArrayList<>();
        List<Calendar> dayCalendars = Date8Util.between(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), Calendar.DAY_OF_YEAR, 1);
        for (Calendar calendar : dayCalendars) {
            String formatDate = DateUtil.format(calendar.getTime(), "MM.dd");
            VisitorBackup visitorBackup = dayVisitorBackupMap.get(formatDate);
            if (visitorBackup != null) {
                usageTimeVOList.add(new UsageTimeVO(visitorBackup.getAverageUsageTime(), visitorBackup.getMinUsageTime(), visitorBackup.getMaxUsageTime(), formatDate));
                continue;
            }
            usageTimeVOList.add(new UsageTimeVO(0L, 0L, 0L, formatDate));
        }

        return usageTimeVOList;
    }

    /**
     * 获得访问
     *
     * @return {@link AccessVO }
     * @author WeiQiangMiao
     * @date 2021-04-06
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private List<AccessVO> getAccessStatistics(Collection<String> list) {

        Map<String, Long> map = list.stream().collect(Collectors.groupingBy(lt -> StringUtils.isEmpty(lt) || "Unknown" .equals(lt) ? "其他" : lt, Collectors.counting()));

        List<AccessVO> accessVOList = new ArrayList<>();
        long i = 0;
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            accessVOList.add(new AccessVO(++i, entry.getKey(), entry.getValue()));
        }

        return accessVOList.size() > 3 ? accessVOList.subList(0, 3) : accessVOList;
    }


    /**
     * 获取访客量VO
     *
     * @param newMinLoginTime 新最小登录时间
     * @param newMaxLoginTime 新最大登录时间
     * @param oldMinLoginTime 旧最小登录时间
     * @param oldMaxLoginTime 旧最大登录时间
     * @return {@link VisitorVolumeVO }
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private VisitorVolumeVO getVisitorVolumeVO(DateTime newMinLoginTime, DateTime newMaxLoginTime, DateTime
            oldMinLoginTime, DateTime oldMaxLoginTime) {
        //查询某天访客量
        Long todayVisitorVolume = Convert.toLong(userLoginInfoMapper.findVisitorVolume(LoginStatus.SUCCESS.getStatus(), newMinLoginTime, newMaxLoginTime).size());
        //查询某天的前天访客量
        Long yesterdayVisitorVolume = Convert.toLong(userLoginInfoMapper.findVisitorVolume(LoginStatus.SUCCESS.getStatus(), oldMinLoginTime, oldMaxLoginTime).size());

        double visitorVolumeContrastRate = getContrastRate(todayVisitorVolume, yesterdayVisitorVolume);

        return new VisitorVolumeVO(todayVisitorVolume, yesterdayVisitorVolume, visitorVolumeContrastRate);
    }


    /**
     * 获取某天的数据访问量统计
     *
     * @param day 一天
     * @return {@link TodayData }
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private TodayData getTrafficStatistics(@Valid @NotNull(message = "时间不能为空") Date day) {
        final DateTime todayMinLoginTime = DateUtil.beginOfDay(day);
        final DateTime todayMaxLoginTime = DateUtil.endOfDay(day);
        final DateTime dateTime = DateUtil.offsetDay(day, -1);
        final DateTime yesterdayMinLoginTime = DateUtil.beginOfDay(dateTime);
        final DateTime yesterdayMaxLoginTime = DateUtil.endOfDay(dateTime);
        List<UserLoginInfo> today = userLoginInfoMapper.findByLoginStatusAndLoginTimeBetweenEqual(LoginStatus.SUCCESS.getStatus(), todayMinLoginTime, todayMaxLoginTime);
        List<UserLoginInfo> yesterday = userLoginInfoMapper.findByLoginStatusAndLoginTimeBetweenEqual(LoginStatus.SUCCESS.getStatus(), yesterdayMinLoginTime, yesterdayMaxLoginTime);

        //新访客量
        Long newVisitorVolume = today.stream().map(UserLoginInfo::getLoginUserId).distinct().count();
        //旧访客量
        Long oldVisitorVolume = yesterday.stream().map(UserLoginInfo::getLoginUserId).distinct().count();
        //访客量对比率
        double visitorVolumeContrastRate = getContrastRate(newVisitorVolume, oldVisitorVolume);
        //查询某天平均使用时间
        long todayAverageUsageTime = getAverageUsageTime(today);
        //查询某天的前天平均使用时间
        long yesterdayAverageUsageTime = getAverageUsageTime(yesterday);

        //查询某天登录率
        Integer userCount = sysUserMapper.countByUserStatusAndUserDeleteStatus(UserStatus.NORMAL.getStatus(), UserDeleteStatus.NOT_DELETED.getStatus());
        double todayLoginRate = NumberUtil.div(newVisitorVolume, userCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //查询某天的前天登录率
        double yesterdayLoginRate = NumberUtil.div(oldVisitorVolume, userCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


        double loginFrequencyContrastRate = getContrastRate(today.size(), yesterday.size());
        double averageUsageTimeContrastRate = getContrastRate(todayAverageUsageTime, yesterdayAverageUsageTime);
        double loginRateContrastRate = getContrastRate(todayLoginRate, yesterdayLoginRate);


        TodayData todayData = new TodayData();
        todayData.setVisitorVolume(newVisitorVolume);
        todayData.setVisitorVolumeContrastRate(visitorVolumeContrastRate);
        todayData.setLoginFrequency(Convert.toLong(today.size()));
        todayData.setAverageUsageTime(todayAverageUsageTime);
        todayData.setLoginRate(todayLoginRate);
        todayData.setLoginFrequencyContrastRate(loginFrequencyContrastRate);
        todayData.setAverageUsageTimeContrastRate(averageUsageTimeContrastRate);
        todayData.setLoginRateContrastRate(loginRateContrastRate);

        return todayData;
    }


    /**
     * 获取对比率
     *
     * @param today     今天
     * @param yesterday 昨天
     * @return double
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private double getContrastRate(Number today, Number yesterday) {
        if (today != null & yesterday != null) {
            if (yesterday.doubleValue() != 0) {
                return NumberUtil.div(NumberUtil.sub(today, yesterday), yesterday)
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            } else {
                double doubleValue = BigDecimal.valueOf(1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (NumberUtil.add(today, yesterday).doubleValue() != 0 && yesterday.doubleValue() == 0) {
                    return doubleValue;
                } else {
                    if (today.doubleValue() != 0 && yesterday.doubleValue() != 0) {
                        return doubleValue;
                    } else {
                        return 0.00D;
                    }
                }
            }
        }
        return 0.00D;

    }

    /**
     * 获取平均使用时间
     *
     * @param todayUserLoginInfos 今天用户登录信息
     * @return long 毫秒
     * @author WeiQiangMiao
     * @date 2021-04-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static long getAverageUsageTime(List<UserLoginInfo> todayUserLoginInfos) {

        Map<Integer, Long> map = todayUserLoginInfos.stream().collect(Collectors.toMap(UserLoginInfo::getLoginUserId,
                info -> {
                    long endOfDay = DateUtil.endOfDay(info.getLoginTime()).getTime();
                    long offlineTime = info.getOfflineTime() == null ? Math.min(System.currentTimeMillis(), endOfDay) : Math.min(info.getOfflineTime().getTime(), endOfDay);
                    if (offlineTime < info.getLoginTime().getTime()) {
                        return DateUnit.SECOND.getMillis();
                    }
                    return offlineTime - info.getLoginTime().getTime();
                },

                ((oldVal, currVal) -> Math.min(oldVal + currVal, DateUnit.DAY.getMillis()))
        ));
        return BigDecimal.valueOf(map.values().stream()
                .mapToLong(value -> BigDecimal.valueOf(NumberUtil.div(value.longValue(), DateUnit.MINUTE.getMillis())).setScale(0, BigDecimal.ROUND_CEILING).longValue())
                .average().orElse(-0.1)).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * 让游客备份
     *
     * @param statisticsDTO 统计dto
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param weekCalendars 周日历
     * @param calendar      日历
     * @param beginOfWeek   开始的一周
     * @param endOfWeek     周结束
     * @return {@link List<VisitorBackup> }
     * @author WeiQiangMiao
     * @date 2021-04-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private List<VisitorBackup> getVisitorBackups(StatisticsDTO statisticsDTO, DateTime startTime, DateTime
            endTime, List<Calendar> weekCalendars, Calendar calendar, DateTime beginOfWeek, DateTime endOfWeek) {
        if (weekCalendars.size() == 1) {
            return visitorBackupMapper.findByCreateTimeBetweenEqual(DateUtil.formatDate(startTime), DateUtil.formatDate(endTime));
        } else if (calendar.getTimeInMillis() == statisticsDTO.getStartTime()) {
            return visitorBackupMapper.findByCreateTimeBetweenEqual(DateUtil.formatDate(startTime), DateUtil.formatDate(endOfWeek));
        } else if (calendar.getTimeInMillis() == statisticsDTO.getEndTime()) {
            return visitorBackupMapper.findByCreateTimeBetweenEqual(DateUtil.formatDate(beginOfWeek), DateUtil.formatDate(endTime));
        } else {
            return visitorBackupMapper.findByCreateTimeBetweenEqual(DateUtil.formatDate(beginOfWeek), DateUtil.formatDate(endOfWeek));
        }
    }

}
