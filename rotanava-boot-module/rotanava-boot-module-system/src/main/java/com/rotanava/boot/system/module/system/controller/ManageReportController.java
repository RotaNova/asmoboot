package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SysReportService;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.SysReportVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理报告控制器
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
@RestController
@RequestMapping("/v1/manageReport")
public class ManageReportController {

    @Autowired
    private SysReportService sysReportService;

    @AutoLog(value = "保存系统报告",operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/saveSysReport")
    public RetData<Void> saveSysReport(@RequestBody AddSysReportDTO addSysReportDTO) {
        sysReportService.saveSysReport(addSysReportDTO);
        return RetData.ok();
    }

    public static void main(String[] args) {
        System.out.println(BaseUtil.getUId());
        System.out.println(BaseUtil.getSnowflakeId());
    }

    @AutoLog(value = "撤销系统报告",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/cancelSysReport")
    public RetData<Void> cancelSysReport(Integer sysReportId) {
        sysReportService.cancelSysReport(sysReportId);
        return RetData.ok();
    }

    @AutoLog(value = "编辑系统报告",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateSysReport")
    public RetData<Void> updateSysReport(@RequestBody UpdateSysReportDTO updateSysReportDTO) {
        sysReportService.updateSysReport(updateSysReportDTO);
        return RetData.ok();
    }

    @AutoLog(value = "确认系统报告",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/confirmSysReport")
    public RetData<Void> confirmSysReport(Integer sysReportId) {
        sysReportService.confirmSysReport(sysReportId);
        return RetData.ok();
    }

    @AutoLog("获取个人系统报告")
    @AdviceResponseBody
    @GetMapping("/getPersonalSystemReport")
    public RetData<BaseVO<SysReportVO>> getPersonalSystemReport(Integer sysReportId) {
        return RetData.ok(sysReportService.getSystemReport(sysReportId));
    }

    @AutoLog("获取个人系统报告列表")
    @AdviceResponseBody
    @PostMapping("/listPersonalSystemReport")
    public RetData<BaseVO<SysReportVO>> listPersonalSystemReport(@RequestBody BaseDTO baseDTO) {
        return RetData.ok(sysReportService.listPersonalSystemReport(baseDTO));
    }

    @AutoLog(value = "驳回系统报告",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/rejectSystemReport")
    public RetData<Void> rejectSystemReport(Integer sysReportId) {
        sysReportService.rejectSystemReport(sysReportId);
        return RetData.ok();
    }

    @AutoLog(value = "回复系统报告",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/replySysReport")
    public RetData<Void> replySysReport(@RequestBody ReplySysReportDTO replySysReportDTO) {
        sysReportService.replySysReport(replySysReportDTO);
        return RetData.ok();
    }

    @AutoLog("获取管理系统报告")
    @AdviceResponseBody
    @GetMapping("/getAdminSystemReport")
    public RetData<BaseVO<SysReportVO>> getAdminSystemReport(Integer sysReportId) {
        return RetData.ok(sysReportService.getSystemReport(sysReportId));
    }

    @AutoLog("获取管理系统报告列表")
    @AdviceResponseBody
    @PostMapping("/listAdminSystemReport")
    public RetData<BaseVO<SysReportVO>> listAdminSystemReport(@RequestBody BaseDTO baseDTO) {

        return RetData.ok(sysReportService.listAdminSystemReport(baseDTO));
    }



}
