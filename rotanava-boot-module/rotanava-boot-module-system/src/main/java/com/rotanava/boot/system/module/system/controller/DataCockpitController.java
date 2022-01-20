package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.DataCockpitService;
import com.rotanava.boot.system.api.module.system.dto.StatisticsDTO;
import com.rotanava.boot.system.api.module.system.vo.DataCockpitVO;
import com.rotanava.boot.system.api.module.system.vo.UsageTimeVO;
import com.rotanava.boot.system.api.module.system.vo.VisitorVolumeAndCountVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.code.RetData;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据驾驶舱模块
 * @author WeiQiangMiao
 * @date 2021-04-01
 */
@Api(tags = "数据驾驶舱模块")
@RestController
@RequestMapping("/v1/dataCockpit")
public class DataCockpitController {

    @Autowired
    private DataCockpitService dataCockpitService;


    @AutoLog(value = "获取数据驾驶舱信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getDataCockpit")
    public RetData<DataCockpitVO> getDataCockpit() {
        return RetData.ok(dataCockpitService.getDataCockpit());
    }


    @AutoLog(value = "获取访问量统计", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getVisitorVolumeStatistics")
    public RetData<List<VisitorVolumeAndCountVO>> getVisitorVolumeStatistics(@RequestBody StatisticsDTO statisticsDTO) {
        return RetData.ok(dataCockpitService.getVisitorVolumeStatistics(statisticsDTO));
    }

    @AutoLog(value = "获取使用时间统计", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getUsageTimeStatistics")
    public RetData<List<UsageTimeVO>> getUsageTimeStatistics(@RequestBody StatisticsDTO statisticsDTO) {
        return RetData.ok(dataCockpitService.getUsageTimeStatistics(statisticsDTO));
    }

}
