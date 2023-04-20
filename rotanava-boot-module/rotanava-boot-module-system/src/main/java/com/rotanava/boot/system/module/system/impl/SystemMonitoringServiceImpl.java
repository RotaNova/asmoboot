package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ejlchina.okhttps.HTTP;
import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.SystemMonitoringService;
import com.rotanava.boot.system.api.module.constant.Cluster;
import com.rotanava.boot.system.api.module.system.bo.SysBasicInfo;
import com.rotanava.boot.system.api.module.system.vo.*;
import com.rotanava.boot.system.module.dao.SysBasicInfoMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.exception.code.SysErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Value("${system.monitoring.type}")
    private Integer monitoring;

    @Autowired
    private SysPlatformDeployService sysPlatformDeployService;

    @Autowired
    private SysBasicInfoMapper sysBasicInfoMapper;

    @Override
    public BasicInfoVO getBasicInfo() {

        if (monitoring == 1) {
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
                if (Cluster.CLUSTER_CPU_TOTAL.getSign().equals(clusterResult.getMetric_name())) {
                    sysBasicInfoVO.setCpuName(String.format("Intel %s", longValue));
                }

            }

            return sysBasicInfoVO;
        }

        if (monitoring == 0) {
            List<SysBasicInfo> sysBasicInfos = sysBasicInfoMapper.selectList(Wrappers.emptyWrapper());
            Map<String, String> map = sysBasicInfos.stream().collect(Collectors.toMap(SysBasicInfo::getSysinfoCode, SysBasicInfo::getSysinfoValue));
            BasicInfoVO sysBasicInfoVO = BeanUtil.mapToBean(map, BasicInfoVO.class, Boolean.TRUE);

            HTTP http = sysPlatformDeployService.getHttpClient(CommonConstant.SYSTEM_BASIC_INFO);
            BasicInfoVO basicInfoVO = http.async(CommonConstant.SYSTEM_BASIC_INFO).get().getResult().getBody().toBean((BasicInfoVO.class));
            sysBasicInfoVO.setDiskSize(basicInfoVO.getDiskSize());
            sysBasicInfoVO.setCpuName(basicInfoVO.getCpuName());
            sysBasicInfoVO.setRamTotal(basicInfoVO.getRamTotal());

            return sysBasicInfoVO;
        }
        throw new CommonException(SysErrorCode.SYS_ERROR_19);
    }

    @Override
    public PerformanceVO getPerformance() {
        if (monitoring == 1) {
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

        if (monitoring == 0) {
            HTTP http = sysPlatformDeployService.getHttpClient(CommonConstant.SYSTEM_PERFORMANCE);
            return http.async(CommonConstant.SYSTEM_PERFORMANCE).get().getResult().getBody().toBean((PerformanceVO.class));
        }
        throw new CommonException(SysErrorCode.SYS_ERROR_19);
    }

}
