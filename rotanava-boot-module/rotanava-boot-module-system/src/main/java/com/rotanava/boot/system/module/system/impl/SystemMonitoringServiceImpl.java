package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ejlchina.okhttps.HTTP;
import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.SystemMonitoringService;
import com.rotanava.boot.system.api.module.constant.Cluster;
import com.rotanava.boot.system.api.module.system.bo.SysBasicInfo;
import com.rotanava.boot.system.api.module.system.vo.*;
import com.rotanava.boot.system.module.dao.SysBasicInfoMapper;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.global.GlobalClass;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统监控服务impl
 *
 * @author WeiQiangMiao
 * @date 2021-03-11
 */
@Service
public class SystemMonitoringServiceImpl implements SystemMonitoringService {


    @Autowired
    private SysPlatformDeployService sysPlatformDeployService;

    @Autowired
    private SysBasicInfoMapper sysBasicInfoMapper;

    @Override
    public BasicInfoVO getBasicInfo() {

        List<SysBasicInfo> sysBasicInfos = sysBasicInfoMapper.selectList(Wrappers.emptyWrapper());
        Map<String, String> map = sysBasicInfos.stream().collect(Collectors.toMap(SysBasicInfo::getSysinfoCode, SysBasicInfo::getSysinfoValue));
        BasicInfoVO sysBasicInfoVO = BeanUtil.mapToBean(map, BasicInfoVO.class, Boolean.TRUE);

        List<ClusterResultsVO> clusterResults = sysPlatformDeployService.getCluster(CommonConstant.SYSTEM_OAUTH_TOKEN);
        for (ClusterResultsVO clusterResult : clusterResults) {
            ClusterDataVO data = clusterResult.getData();
            List<ClusterResultVO> result = data.getResult();
            ClusterResultVO clusterResultVO = result.get(0);
            List<BigDecimal> value = clusterResultVO.getValue();
            long longValue = value.get(1).longValue();
            if (Cluster.CLUSTER_DISK_SIZE_CAPACITY.getSign().equals(clusterResult.getMetric_name())) {
                sysBasicInfoVO.setDiskSize(longValue);
            }
            if (Cluster.CLUSTER_MEMORY_TOTAL.getSign().equals(clusterResult.getMetric_name())) {
                sysBasicInfoVO.setRamTotal(longValue);
            }
            if(Cluster.CLUSTER_CPU_TOTAL.getSign().equals(clusterResult.getMetric_name())){
                sysBasicInfoVO.setCpuName(String.format("Intel %s",longValue));
            }

        }


        return sysBasicInfoVO;
    }

    @Override
    public PerformanceVO getPerformance() {
        PerformanceVO performanceVO = new PerformanceVO();
        List<ClusterResultsVO> clusterResults = sysPlatformDeployService.getCluster(CommonConstant.SYSTEM_OAUTH_TOKEN);

        String clusterCpuUsage = null;
        long clusterMemoryTotal = 0L;
        long clusterMemoryAvailable = 0L;
        long clusterDiskSizeCapacity = 0L;
        long clusterDiskSizeAvailable = 0L;

        for (ClusterResultsVO clusterResult : clusterResults) {
            ClusterDataVO data = clusterResult.getData();
            List<ClusterResultVO> result = data.getResult();
            ClusterResultVO clusterResultVO = result.get(0);
            List<BigDecimal> value = clusterResultVO.getValue();
            if (Cluster.CLUSTER_CPU_UTILISATION.getSign().equals(clusterResult.getMetric_name())) {
                clusterCpuUsage = value.get(1).setScale(2, BigDecimal.ROUND_CEILING).toString();
            }
            if (Cluster.CLUSTER_MEMORY_TOTAL.getSign().equals(clusterResult.getMetric_name())) {
                clusterMemoryTotal = value.get(1).longValue();
            }
            if (Cluster.CLUSTER_MEMORY_AVAILABLE.getSign().equals(clusterResult.getMetric_name())) {
                clusterMemoryAvailable = value.get(1).longValue();
            }
            if (Cluster.CLUSTER_DISK_SIZE_CAPACITY.getSign().equals(clusterResult.getMetric_name())) {
                clusterDiskSizeCapacity = value.get(1).longValue();
            }
            if (Cluster.CLUSTER_DISK_SIZE_AVAILABLE.getSign().equals(clusterResult.getMetric_name())) {
                clusterDiskSizeAvailable = value.get(1).longValue();
            }
        }
        performanceVO.setCpuUsage(clusterCpuUsage);
        performanceVO.setRamUsage(String.format("%s/%s", clusterMemoryTotal - clusterMemoryAvailable, clusterMemoryTotal));
        performanceVO.setDiskUsage(String.format("%s/%s", clusterDiskSizeCapacity - clusterDiskSizeAvailable, clusterDiskSizeCapacity));

        return performanceVO;
    }

}
