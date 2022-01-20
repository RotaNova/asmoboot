package com.rotanava.boot.system.module.system.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rotanava.boot.system.api.SysBackUpService;
import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.module.system.bo.SysBackup;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.dto.system.SystemBackupDTO;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.DateUtil;
import com.rotanava.framework.util.PageUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 系统备份定时任务
 * @author: richenLi
 * @create: 2021-04-13 13:57
 **/
@Slf4j
@Component
public class SystemBackupTask {


    @Autowired
    private SysBackUpService sysBackUpService;

    @Autowired
    SysServiceSettingService sysServiceSettingService;



    @Autowired
    private FileUploadUtil fileUploadUtil;

    private static final Integer BEGIN =1;
    private static final Integer CLOSE =2;

    @XxlJob(value = "sysBackupTask")
    public ReturnT<String> sysBackupTask(String data){

        //获取系统备份配置信息
        SystemBackupDTO systemBackupsConfig = sysServiceSettingService.getSystemBackupsConfig();
        if (CLOSE==systemBackupsConfig.getScheduledBackUpOption()){
            return ReturnT.SUCCESS;
        }
        QueryWrapper<SysBackup> queryWrapper = new QueryWrapper<>();
        Date date = DateUtil.subtractDays(new Date(), systemBackupsConfig.getBackupSaveDays());

        queryWrapper.le("create_time", Date8Util.format(date));
        queryWrapper.eq("service_type",1);
        //删除大于保存天数的备份文件和数据信息
        List<SysBackup> list = sysBackUpService.list(queryWrapper);
        if (list.size()>0){
            List<Integer> ids = new ArrayList<>();
            for (SysBackup sysBackup : list) {
                ids.add(sysBackup.getId());
                fileUploadUtil.rmObject(sysBackup.getBucketName(),sysBackup.getObjectName());
            }
            sysBackUpService.removeByIds(ids);
        }


        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_type",1);
        IPage<SysBackup> page = new Page<>(1, 1);
        IPage<SysBackup> pageList = sysBackUpService.page(page, queryWrapper);
        if (pageList.getRecords().size()<=0) {
            //直接进行备份
            sysBackUpService.sysBackUp(sysBackUpService.autoBackupType, sysBackUpService.sysBackUpType);
        }else {
            SysBackup sysBackup = pageList.getRecords().get(0);
            Integer day = DateUtil.daysBetween(new Date(),sysBackup.getCreateTime());
            //判断是否需要备份
            if (day>=systemBackupsConfig.getBackupFrequency()){
                sysBackUpService.sysBackUp(sysBackUpService.autoBackupType, sysBackUpService.sysBackUpType);
            }
        }

        return ReturnT.SUCCESS;
    }


//    @XxlJob(value = "sysLogBackeupTask")
//    public void sysLogBackeupTask(){
//
//        sysBackUpService.sysBackUp(sysBackUpService.autoBackupType, sysBackUpService.logBackUpType);
//
//    }
}
