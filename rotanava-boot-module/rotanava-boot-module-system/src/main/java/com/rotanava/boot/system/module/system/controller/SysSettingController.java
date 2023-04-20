package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SysBackUpService;
import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.module.system.dto.system.LogBackupDTO;
import com.rotanava.boot.system.api.module.system.dto.system.NtpSettingDTO;
import com.rotanava.boot.system.api.module.system.dto.system.SystemBackupDTO;
import com.rotanava.boot.system.api.module.system.vo.NtpInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysTimingTaskVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.common.constant.enums.TimeZoneEnum;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.NtpUtil;
import com.rotanava.framework.util.StringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: 系统维护控制层
 * @author: richenLi
 * @create: 2021-03-29 09:26
 **/
@Slf4j
@Api(tags = "升级维护")
@RestController
@RequestMapping("/v1/sysSetting")
public class SysSettingController {

    @Autowired
    private SysPlatformDeployService sysPlatformDeployService;

    @Autowired
    private SysServiceSettingService sysServiceSettingService;

    @Autowired
    private SysBackUpService sysBackUpService;


    @AutoLog(value = "系统重启", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/systemReboot")
    public RetData systemReboot() {
        sysPlatformDeployService.systemReboot();
        return BuildUtil.buildSuccessResult();
    }


//    @AutoLog(value = "系统备份", operateType = OperateTypeEnum.ADD)
//    @AdviceResponseBody
//    @PostMapping("/systemBackups")
//    public RetData systemBackups() {
//        return BuildUtil.buildSuccessResult();
//    }


    @AutoLog(value = "系统备份配置信息保存", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/systemBackupsConfig")
    public RetData systemBackupsConfig(@RequestBody SystemBackupDTO systemBackupDTO) {
        sysServiceSettingService.systemBackupsConfig(systemBackupDTO);
        return BuildUtil.buildSuccessResult();
    }


    @AutoLog(value = "恢复系统出厂设置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/systemFactoryReset")
    public RetData systemFactoryReset() {
        sysBackUpService.systemFactoryReset();
        return BuildUtil.buildSuccessResult();
    }

    @AutoLog(value = "恢复备份", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/backupRecovery")
    public RetData backupRecovery(@RequestParam("file")MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!fileName.contains(".bak")){
            throw new CommonException(ParamErrorCode.PARAM_ERROR_60);
        }

        return BuildUtil.buildSuccessResult();
    }


    @AutoLog(value = "系统备份立即备份", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/systemBackupNow")
    public RetData systemBackupNow() {
        sysBackUpService.sysBackUp(sysBackUpService.manualBackupType, sysBackUpService.sysBackUpType);
        return BuildUtil.buildSuccessResult();
    }

    @AutoLog(value = "日志备份立即备份", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/logBackupNow")
    public RetData logBackupNow() {
        sysBackUpService.sysBackUp(sysBackUpService.manualBackupType, sysBackUpService.logBackUpType);
        return BuildUtil.buildSuccessResult();
    }

    @AutoLog(value = "删除系统备份", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    @PostMapping("/deleteBackup")
    public RetData deleteBackup(@RequestBody List<Integer> list) {
        for (Integer id : list) {
            sysBackUpService.removeById(id);
        }
        return BuildUtil.buildSuccessResult();
    }

    @AutoLog(value = "获取系统备份列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getSysBackupList")
    public RetData getSysBackupList() {
        return new RetData(sysBackUpService.getSysBackupList(sysBackUpService.sysBackUpType));
    }


    @AutoLog(value = "获取日志备份列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getLogBackupList")
    public RetData getLogBackupList() {
        return new RetData(sysBackUpService.getSysBackupList(sysBackUpService.logBackUpType));
    }


    @AutoLog(value = "获取系统备份配置信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getSystemBackupsConfig")
    public RetData getSystemBackupsConfig() {
        return new RetData(sysServiceSettingService.getSystemBackupsConfig());
    }



    @AutoLog(value = "保存日志备份配置信息", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/saveLogBackupConfig")
    public RetData saveLogBackupConfig(@RequestBody LogBackupDTO logBackupDTO) {
        sysServiceSettingService.saveLogBackupConfig(logBackupDTO);
        return BuildUtil.buildSuccessResult();
    }

    @AutoLog(value = "获取日志备份配置信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getLogBackupConfig")
    public RetData getLogBackupConfig() {
        return new RetData(sysServiceSettingService.getLogBackupConfig());
    }



    @AutoLog(value = "获取NTF信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getNtpInfo")
    public RetData<NtpInfoVO> getNtpInfo() {
        NtpSettingDTO ntpSetting = sysServiceSettingService.getNtpSetting();
        NtpInfoVO ntpInfoVO = new NtpInfoVO();
        ntpInfoVO.setNtpSetting(ntpSetting);
        ntpInfoVO.setZoneList(TimeZoneEnum.getZoneList());
        return new RetData(ntpInfoVO);
//        throw new CommonException(SysErrorCode.SYS_ERROR_18);
    }


    @AutoLog(value = "保存ntp服务器信息", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/saveNtpInfo")
    public RetData saveNtpInfo(@RequestBody NtpSettingDTO ntpSettingDTO) {
        sysServiceSettingService.saveNtpSetting(ntpSettingDTO);
        return BuildUtil.buildSuccessResult();
    }


    @AutoLog(value = "测试NTP服务器", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/testNtpServer")
    public RetData testNtpServer(@RequestParam("ntpAddress")String ntpAddress,@RequestParam("ntpPort")Integer ntpPort) {
        if (StringUtil.isNullOrEmpty(ntpAddress)){
            return BuildUtil.buildParamError();
        }
//        NtpSettingDTO ntpSetting = sysServiceSettingService.getNtpSetting();
        if (NtpUtil.testNtp(ntpAddress,ntpPort)) {
            return BuildUtil.buildSuccessResult();
        } else {
            throw new CommonException(SysErrorCode.SYS_ERROR_05);
        }
    }

    @AutoLog(value = "保存定时重启", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/saveRestartRegularly")
    public RetData<Void> saveRestartRegularly(@RequestParam("timing") Long timing) {
        sysServiceSettingService.saveRestartRegularly(timing);
        return RetData.ok();
    }

    @AutoLog(value = "删除定时重启", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    @DeleteMapping("/deleteRestartRegularly")
    public RetData<Void> deleteRestartRegularly(@RequestParam("sysTimingTaskId") Integer sysTimingTaskId) {
        sysServiceSettingService.deleteRestartRegularly(sysTimingTaskId);
        return RetData.ok();
    }

    @AutoLog(value = "获取定时重启列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/listRestartRegularly")
    public RetData<BaseVO<SysTimingTaskVO>> listRestartRegularly(@RequestBody BaseDTO baseDTO) {
        return RetData.ok(sysServiceSettingService.listRestartRegularly(baseDTO));
    }



}
