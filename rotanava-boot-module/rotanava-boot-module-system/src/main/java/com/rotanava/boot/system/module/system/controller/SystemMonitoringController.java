package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SystemMonitoringService;
import com.rotanava.boot.system.api.module.system.vo.BasicInfoVO;
import com.rotanava.boot.system.api.module.system.vo.PerformanceVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.code.RetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统监控控制器
 *
 * @author WeiQiangMiao
 * @date 2021-03-11
 */
@RestController
@RequestMapping("/v1/systemMonitoring")
public class SystemMonitoringController {

    @Autowired
    private SystemMonitoringService systemMonitoringService;



    @AutoLog("获取系统基本信息")
    @AdviceResponseBody
    @GetMapping("/getBasicInfo")
    public RetData<BasicInfoVO> getBasicInfo() {

        return RetData.ok(systemMonitoringService.getBasicInfo());
    }

    @AutoLog("获取性能")
    @AdviceResponseBody
    @GetMapping("/getPerformance")
    public RetData<PerformanceVO> getPerformance() {

        return RetData.ok(systemMonitoringService.getPerformance());
    }
}
