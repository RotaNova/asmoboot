package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.bo.SysTimingTask;
import com.rotanava.boot.system.api.module.system.dto.PlatformSettingDTO;
import com.rotanava.boot.system.api.module.system.dto.system.LogBackupDTO;
import com.rotanava.boot.system.api.module.system.dto.system.NtpSettingDTO;
import com.rotanava.boot.system.api.module.system.dto.system.SystemBackupDTO;
import com.rotanava.boot.system.api.module.system.vo.PlatformSettingVO;
import com.rotanava.boot.system.api.module.system.vo.SysTimingTaskVO;
import com.rotanava.boot.system.module.dao.SysServiceSettingMapper;
import com.rotanava.boot.system.module.dao.SysTimingTaskMapper;
import com.rotanava.boot.system.module.system.mq.MqTransactionalMessageSender;
import com.rotanava.boot.system.module.system.mq.SysTimingListenter;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.DateUtil;
import com.rotanava.framework.util.PageUtils;
import com.rotanava.framework.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-23 14:59
 **/
@Service
public class SysServiceSettingServiceImpl extends ServiceImpl<SysServiceSettingMapper, SysServiceSetting> implements SysServiceSettingService {

    @Autowired
    FileUploadUtil fileUploadUtil;
    @Autowired
    private SysTimingTaskMapper sysTimingTaskMapper;
    @Autowired
    private MqTransactionalMessageSender mqTransactionalmessageSender;

    @Autowired
    SysPlatformDeployService sysPlatformDeployService;

    private static final Integer defaultType = 1;
    private static final Integer custiomType = 2;

    private static final String settingBucket = "rn-common-resources";


    private String getConfigValueByOptionType(Integer type, SysServiceSetting sysServiceSetting) {
        if (defaultType == type) {
            return sysServiceSetting.getSysServiceDefaultValue();
        } else if (custiomType == type) {
            return sysServiceSetting.getSysServiceValue();
        } else {
            return sysServiceSetting.getSysServiceValue();
        }
    }

    @Override
    public String getSiteName(){
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 2);
        List<SysServiceSetting> list = list(queryWrapper);

        for (SysServiceSetting sysServiceSetting : list) {
            if ("site_name".equals(sysServiceSetting.getSysServiceCode())){
                return sysServiceSetting.getSysServiceValue();
            }
        }
        return "新航物联网开源版本";
    }

    @Override
    public PlatformSettingVO getPlatformSetting() {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 2);
        List<SysServiceSetting> list = list(queryWrapper);

        HashMap<String, SysServiceSetting> map = new HashMap<>();

        for (SysServiceSetting sysServiceSetting : list) {
            map.put(sysServiceSetting.getSysServiceCode(), sysServiceSetting);
        }

        PlatformSettingVO platformSettingVO = new PlatformSettingVO();
        platformSettingVO.setBannerCloseOption(map.get("platform_banner_close").getIntegerValue());
        platformSettingVO.setBannerOption(map.get("platform_banner_option").getIntegerValue());
        platformSettingVO.setBannerFrequency(map.get("platform_banner_frequency").getIntegerValue());
//        platformSettingVO.setLogoOption(map.get("platform_logo_option").getIntegerValue());
//        platformSettingVO.setSiteOption(map.get("site_name_option").getIntegerValue());

        platformSettingVO.setSiteName(map.get("site_name").getSysServiceValue());


        String logoBucketName = map.get("platform_logo_bucket_name").getSysServiceValue();
        String logoObjectName = map.get("platform_logo_object_name").getSysServiceValue();
        String logoUrl = fileUploadUtil.getObjUrl(logoBucketName, logoObjectName, 60);
        platformSettingVO.setLogoUrl(logoUrl);

        String bannerBucketName = map.get("platform_banner_bucket_name").getSysServiceValue();
        String bannerObjectName = map.get("platform_banner_object_name").getSysServiceValue();

        String bannerUrl = fileUploadUtil.getObjUrl(bannerBucketName, bannerObjectName, 60);
        platformSettingVO.setBannerUrl(bannerUrl);


        return platformSettingVO;
    }

    @Override
    public void savePlatformSetting(PlatformSettingDTO platformSettingDTO) {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 2);
        List<SysServiceSetting> list = list(queryWrapper);

        HashMap<String, SysServiceSetting> map = new HashMap<>();

        for (SysServiceSetting sysServiceSetting : list) {
            map.put(sysServiceSetting.getSysServiceCode(), sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getBannerCloseOption())) {
            SysServiceSetting sysServiceSetting = map.get("platform_banner_close");
            sysServiceSetting.setSysServiceValue(platformSettingDTO.getBannerCloseOption().toString());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getBannerOption())) {
            SysServiceSetting sysServiceSetting = map.get("platform_banner_option");
            sysServiceSetting.setSysServiceValue(platformSettingDTO.getBannerOption().toString());
            updateById(sysServiceSetting);
        }

//        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getLogoOption())){
//            SysServiceSetting sysServiceSetting = map.get("platform_logo_option");
//            sysServiceSetting.setSysServiceValue(platformSettingDTO.getLogoOption().toString());
//            updateById(sysServiceSetting);
//        }


        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getBannerFrequency())) {
            SysServiceSetting sysServiceSetting = map.get("platform_banner_frequency");
            sysServiceSetting.setSysServiceValue(platformSettingDTO.getBannerFrequency().toString());
            updateById(sysServiceSetting);
        }

//        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getSiteOption())){
//            SysServiceSetting sysServiceSetting = map.get("site_name_option");
//            sysServiceSetting.setSysServiceValue(platformSettingDTO.getSiteOption().toString());
//            updateById(sysServiceSetting);
//        }

        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getSiteName())) {
            SysServiceSetting sysServiceSetting = map.get("site_name");
            sysServiceSetting.setSysServiceValue(platformSettingDTO.getSiteName());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getLogoImage())) {
            UploadResultBean uploadResultBean = fileUploadUtil.uploadImages(settingBucket, BaseUtil.getUIdImage(), platformSettingDTO.getLogoImage());
            SysServiceSetting sysServiceSetting = map.get("platform_logo_object_name");
            sysServiceSetting.setSysServiceValue(uploadResultBean.getObjName());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(platformSettingDTO.getBannerImage())) {
            UploadResultBean uploadResultBean = fileUploadUtil.uploadImages(settingBucket, BaseUtil.getUIdImage(), platformSettingDTO.getBannerImage());
            SysServiceSetting sysServiceSetting = map.get("platform_banner_object_name");
            sysServiceSetting.setSysServiceValue(uploadResultBean.getObjName());
            updateById(sysServiceSetting);
        }
    }


    @Override
    public void resetPlatformSetting() {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 2);
        List<SysServiceSetting> list = list(queryWrapper);
        Map<String, SysServiceSetting> map = settingListToMap(list);

        resetSetting(map.get("platform_logo_object_name"));

        resetSetting(map.get("platform_banner_object_name"));

        resetSetting(map.get("site_name"));

        resetSetting(map.get("platform_banner_option"));


    }


    private void resetSetting(SysServiceSetting sysServiceSetting) {
        sysServiceSetting.setSysServiceValue(sysServiceSetting.getSysServiceDefaultValue());
        updateById(sysServiceSetting);
    }

    @Override
    public void saveNtpSetting(NtpSettingDTO ntpSettingDTO) {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 3);
        List<SysServiceSetting> list = list(queryWrapper);
        HashMap<String, SysServiceSetting> map = new HashMap<>();

        for (SysServiceSetting sysServiceSetting : list) {
            map.put(sysServiceSetting.getSysServiceCode(), sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(ntpSettingDTO.getNtpAddress())) {
            SysServiceSetting sysServiceSetting = map.get("ntp_address");
            sysServiceSetting.setSysServiceValue(ntpSettingDTO.getNtpAddress());
            updateById(sysServiceSetting);


        }

        if (!StringUtil.isNullOrEmpty(ntpSettingDTO.getNtpPort())) {
            SysServiceSetting sysServiceSetting = map.get("ntp_port");
            sysServiceSetting.setSysServiceValue(ntpSettingDTO.getNtpPort());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(ntpSettingDTO.getNtpInterval())) {
            SysServiceSetting sysServiceSetting = map.get("ntp_interval");
            sysServiceSetting.setSysServiceValue(ntpSettingDTO.getNtpInterval().toString());
            updateById(sysServiceSetting);
        }


        if (!StringUtil.isNullOrEmpty(ntpSettingDTO.getNtpOption())) {
            SysServiceSetting sysServiceSetting = map.get("ntp_option");
            sysServiceSetting.setSysServiceValue(ntpSettingDTO.getNtpOption().toString());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(ntpSettingDTO.getLocalTime())) {

            sysPlatformDeployService.updateSystemTime(Date8Util.format(ntpSettingDTO.getLocalTime()));
        }

        if (!StringUtil.isNullOrEmpty(ntpSettingDTO.getNtpTimeZone())) {
            SysServiceSetting sysServiceSetting = map.get("ntp_time_zone");
            sysServiceSetting.setSysServiceValue(ntpSettingDTO.getNtpTimeZone());
            updateById(sysServiceSetting);
            sysPlatformDeployService.updateSystemTimeZone(ntpSettingDTO.getNtpTimeZone());
        }


    }

    @Override
    public NtpSettingDTO getNtpSetting() {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 3);
        List<SysServiceSetting> list = list(queryWrapper);
        Map<String, SysServiceSetting> map = settingListToMap(list);

        NtpSettingDTO ntpSettingDTO = new NtpSettingDTO();
        ntpSettingDTO.setNtpAddress(map.get("ntp_address").getSysServiceValue());
        ntpSettingDTO.setNtpPort(map.get("ntp_port").getSysServiceValue());
        ntpSettingDTO.setNtpInterval(map.get("ntp_interval").getIntegerValue());
        ntpSettingDTO.setNtpOption(map.get("ntp_option").getIntegerValue());
        ntpSettingDTO.setLocalTime(DateUtil.parseTime(sysPlatformDeployService.getSystemTime()));
        ntpSettingDTO.setNtpTimeZone(map.get("ntp_time_zone").getSysServiceValue());
        return ntpSettingDTO;
    }


    @Override
    public void systemBackupsConfig(SystemBackupDTO systemBackupDTO) {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 4);
        List<SysServiceSetting> list = list(queryWrapper);
        Map<String, SysServiceSetting> map = settingListToMap(list);

        if (!StringUtil.isNullOrEmpty(systemBackupDTO.getScheduledBackUpOption())) {
            SysServiceSetting sysServiceSetting = map.get("scheduled_backup_option");
            sysServiceSetting.setSysServiceValue(systemBackupDTO.getScheduledBackUpOption().toString());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(systemBackupDTO.getBackupFrequency())) {
            SysServiceSetting sysServiceSetting = map.get("backup_frequency");
            sysServiceSetting.setSysServiceValue(systemBackupDTO.getBackupFrequency().toString());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(systemBackupDTO.getBackupSaveDays())) {
            SysServiceSetting sysServiceSetting = map.get("backup_save_days");
            sysServiceSetting.setSysServiceValue(systemBackupDTO.getBackupSaveDays().toString());
            updateById(sysServiceSetting);
        }

    }

    public SystemBackupDTO getSystemBackupsConfig() {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 4);
        List<SysServiceSetting> list = list(queryWrapper);
        Map<String, SysServiceSetting> map = settingListToMap(list);

        SystemBackupDTO systemBackupDTO = new SystemBackupDTO();
        systemBackupDTO.setBackupFrequency(map.get("backup_frequency").getIntegerValue());
        systemBackupDTO.setBackupSaveDays(map.get("backup_save_days").getIntegerValue());
        systemBackupDTO.setScheduledBackUpOption(map.get("scheduled_backup_option").getIntegerValue());
        return systemBackupDTO;
    }


    public Map<String, SysServiceSetting> settingListToMap(List<SysServiceSetting> list) {
        Map<String, SysServiceSetting> map = new HashMap<>();

        for (SysServiceSetting sysServiceSetting : list) {
            map.put(sysServiceSetting.getSysServiceCode(), sysServiceSetting);
        }
        return map;
    }


    @Override
    public LogBackupDTO getLogBackupConfig() {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 5);
        List<SysServiceSetting> list = list(queryWrapper);
        Map<String, SysServiceSetting> map = settingListToMap(list);

        LogBackupDTO logBackupDTO = new LogBackupDTO();
        logBackupDTO.setLogScheduledBackupOption(map.get("log_scheduled_backup_option").getIntegerValue());
        logBackupDTO.setLogBackupFrequency(map.get("log_backup_frequency").getIntegerValue());
        logBackupDTO.setLogBackupSaveDays(map.get("log_backup_save_days").getIntegerValue());
        logBackupDTO.setLogInfoSaveDays(map.get("log_info_save_days").getIntegerValue());
        return logBackupDTO;
    }



    @Override
    public void saveLogBackupConfig(LogBackupDTO logBackupDTO) {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_type", 5);
        List<SysServiceSetting> list = list(queryWrapper);
        Map<String, SysServiceSetting> map = settingListToMap(list);

        if (!StringUtil.isNullOrEmpty(logBackupDTO.getLogScheduledBackupOption())) {
            SysServiceSetting sysServiceSetting = map.get("log_scheduled_backup_option");
            sysServiceSetting.setSysServiceValue(logBackupDTO.getLogScheduledBackupOption().toString());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(logBackupDTO.getLogBackupFrequency())) {
            SysServiceSetting sysServiceSetting = map.get("log_backup_frequency");
            sysServiceSetting.setSysServiceValue(logBackupDTO.getLogBackupFrequency().toString());
            updateById(sysServiceSetting);
        }

        if (!StringUtil.isNullOrEmpty(logBackupDTO.getLogBackupSaveDays())) {
            SysServiceSetting sysServiceSetting = map.get("log_backup_save_days");
            sysServiceSetting.setSysServiceValue(logBackupDTO.getLogBackupSaveDays().toString());
            updateById(sysServiceSetting);
        }



        if (!StringUtil.isNullOrEmpty(logBackupDTO.getLogInfoSaveDays())) {
            SysServiceSetting sysServiceSetting = map.get("log_info_save_days");
            sysServiceSetting.setSysServiceValue(logBackupDTO.getLogInfoSaveDays().toString());
            updateById(sysServiceSetting);
        }
    }

    @Override
    public void saveRestartRegularly(Long timing) {
        SysTimingTask sysTimingTask = new SysTimingTask(BaseUtil.getSnowflakeId(),new Date(timing));
        sysTimingTaskMapper.insert(sysTimingTask);
        mqTransactionalmessageSender.insertMqTransactionalMessage(SysTimingListenter.SYS_TIMING_QUEUE
                , Convert.toStr(sysTimingTask.getId()), Math.max(0, timing - System.currentTimeMillis()));
    }

    @Override
    public void deleteRestartRegularly(Integer sysTimingTaskId) {
        sysTimingTaskMapper.deleteById(sysTimingTaskId);
    }

    @Override
    public BaseVO<SysTimingTaskVO> listRestartRegularly(BaseDTO baseDTO) {
        QueryWrapper<SysTimingTask> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);

        IPage<SysTimingTask> page = sysTimingTaskMapper.selectPage(PageUtils.startPage(baseDTO), queryWrapper);
        List<SysTimingTaskVO> sysTimingTaskVOList = new ArrayList<>();
        for (SysTimingTask sysTimingTask : page.getRecords()) {
            SysTimingTaskVO sysTimingTaskVO = new SysTimingTaskVO();
            sysTimingTaskVO.setSysTimingTaskId(sysTimingTask.getId());
            sysTimingTaskVO.setTiming(sysTimingTask.getTiming().getTime());
            sysTimingTaskVOList.add(sysTimingTaskVO);
        }

        return BuildUtil.buildListVO(page.getTotal(), sysTimingTaskVOList);
    }

    @Override
    public void saveDingDingServiceSetting(String serviceCode, String serviceName, String sysServiceValue, String sysServiceDefaultValue, String sysServiceDescription) {
        SysServiceSetting sysServiceSetting = baseMapper.findBySysServiceCode(serviceCode);
        boolean insert = true;
        if (sysServiceSetting != null) {
            insert = false;
        } else {
            sysServiceSetting = new SysServiceSetting();
        }

        sysServiceSetting.setSysServiceCode(serviceCode);
        sysServiceSetting.setSysServiceName(serviceName);
        sysServiceSetting.setSysServiceValue(sysServiceValue);
        sysServiceSetting.setSysServiceDefaultValue(StringUtils.isBlank(sysServiceDefaultValue) ? "" : sysServiceDefaultValue);
        sysServiceSetting.setSysServiceDescription(sysServiceDescription);
        sysServiceSetting.setSysServiceType(7);

        if (insert) {
            baseMapper.insert(sysServiceSetting);
        } else {
            updateById(sysServiceSetting);
        }

    }

    @Override
    public void deleteByServiceCode(List<String> serviceCodes) {
        for (String serviceCode : serviceCodes) {
            baseMapper.deleteByServiceCode(serviceCode);
        }
    }

    @Override
    public List<SysServiceSetting> getSysServiceSetting(List<String> serviceCodes) {
        List<SysServiceSetting> serviceSettingList = Lists.newArrayList();
        for (String serviceCode : serviceCodes) {
            SysServiceSetting sysServiceSetting = baseMapper.findBySysServiceCode(serviceCode);
            if (sysServiceSetting != null) {
                serviceSettingList.add(sysServiceSetting);
            }
        }
        return serviceSettingList;
    }

}
