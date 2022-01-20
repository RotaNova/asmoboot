package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.dto.StatisticsDTO;
import com.rotanava.boot.system.api.module.system.vo.DataCockpitVO;
import com.rotanava.boot.system.api.module.system.vo.UsageTimeVO;
import com.rotanava.boot.system.api.module.system.vo.VisitorVolumeAndCountVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * 驾驶舱的数据服务
 *
 * @author WeiQiangMiao
 * @date 2021-04-01
 */
@Validated
public interface DataCockpitService {

    /**
     * 获取数据的驾驶舱
     *
     * @return {@link DataCockpitVO }
     * @author WeiQiangMiao
     * @date 2021-04-01
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    DataCockpitVO getDataCockpit();

    /**
     * 获取访问量统计
     *
     * @param statisticsDTO 统计dto
     * @return {@link List<VisitorVolumeAndCountVO> }
     * @author WeiQiangMiao
     * @date 2021-04-06
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<VisitorVolumeAndCountVO> getVisitorVolumeStatistics(@Valid StatisticsDTO statisticsDTO);

    /**
     * 获得使用时间统计
     *
     * @param statisticsDTO 统计dto
     * @return {@link List<UsageTimeVO> }
     * @author WeiQiangMiao
     * @date 2021-04-06
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<UsageTimeVO> getUsageTimeStatistics(@Valid StatisticsDTO statisticsDTO);
}
