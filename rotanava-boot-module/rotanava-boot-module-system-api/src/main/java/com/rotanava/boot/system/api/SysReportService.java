package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rotanava.boot.system.api.module.system.bo.SysReport;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.SysReportVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 报告服务
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
@Validated
public interface SysReportService extends IService<SysReport> {

    /**
     * 保存系统报告
     *
     * @param addSysReportDTO 添加系统报告dto
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void saveSysReport(@Validated AddSysReportDTO addSysReportDTO);

    /**
     * 取消系统报告
     *
     * @param sysReportId 系统报告Id
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void cancelSysReport(@NotNull(message = "系统报告Id不能为空") Integer sysReportId);

    /**
     * 更新系统报告
     *
     * @param updateSysReportDTO 更新系统报告dto
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateSysReport(@Validated UpdateSysReportDTO updateSysReportDTO);

    /**
     * 确认系统报告
     *
     * @param sysReportId sys报告id
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void confirmSysReport(@NotNull(message = "系统报告Id不能为空") Integer sysReportId);

    /**
     * 获得系统报告
     *
     * @param sysReportId sys报告id
     * @return {@link SysReportVO }
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysReportVO> getSystemReport(@NotNull(message = "系统报告Id不能为空") Integer sysReportId);

    /**
     * 列表的个人系统报告
     *
     * @param baseDTO baseDTO
     * @return {@link BaseVO<SysReportVO> }
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysReportVO> listPersonalSystemReport(@Validated BaseDTO baseDTO);


    /**
     * 拒绝系统报告
     *
     * @param sysReportId sys报告id
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void rejectSystemReport(@NotNull(message = "系统报告Id不能为空") Integer sysReportId);

    /**
     * 回复系统报告
     *
     * @param replySysReportDTO 回复系统报告dto
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void replySysReport(@Validated ReplySysReportDTO replySysReportDTO);

    /**
     * 系统管理员列表报告
     *
     * @param baseDTO baseDTO
     * @return {@link BaseVO<SysReportVO> }
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysReportVO> listAdminSystemReport(@Validated BaseDTO baseDTO);
}
