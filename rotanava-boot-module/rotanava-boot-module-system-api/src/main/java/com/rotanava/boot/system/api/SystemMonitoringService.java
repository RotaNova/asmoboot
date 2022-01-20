package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.vo.BasicInfoVO;
import com.rotanava.boot.system.api.module.system.vo.PerformanceVO;
import org.springframework.validation.annotation.Validated;

/**
 * 系统监控服务
 *
 * @author WeiQiangMiao
 * @date 2021-03-11
 */
@Validated
public interface SystemMonitoringService {

    /**
     * 得到基本信息
     *
     * @return {@link BasicInfoVO }
     * @author WeiQiangMiao
     * @date 2021-03-11
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BasicInfoVO getBasicInfo();

    /**
     * 得到性能
     *
     * @return {@link PerformanceVO }
     * @author WeiQiangMiao
     * @date 2021-03-11
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    PerformanceVO getPerformance();
}
