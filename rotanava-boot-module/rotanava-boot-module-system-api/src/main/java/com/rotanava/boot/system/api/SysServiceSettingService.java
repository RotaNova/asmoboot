package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.dto.ManageSecurityDTO;
import com.rotanava.boot.system.api.module.system.dto.MqttSetDTO;
import com.rotanava.boot.system.api.module.system.dto.PlatformSettingDTO;
import com.rotanava.boot.system.api.module.system.dto.system.LogBackupDTO;
import com.rotanava.boot.system.api.module.system.dto.system.NtpSettingDTO;
import com.rotanava.boot.system.api.module.system.dto.system.SystemBackupDTO;
import com.rotanava.boot.system.api.module.system.vo.MqttSetVO;
import com.rotanava.boot.system.api.module.system.vo.PlatformSettingVO;
import com.rotanava.boot.system.api.module.system.vo.SysTimingTaskVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-23 14:58
 **/
public interface SysServiceSettingService extends IService<SysServiceSetting> {

    PlatformSettingVO getPlatformSetting();


    String getSiteName();


    void savePlatformSetting(PlatformSettingDTO platformSettingDTO);


    /**
     * @description : 重置平台配置
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    void resetPlatformSetting();

    /**
     * @description :保存ntp服务器信息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    void saveNtpSetting(NtpSettingDTO ntpSettingDTO);


    /**
     * @description : 获取ntp服务器信息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    NtpSettingDTO getNtpSetting();


    /**
     * @description : 保存系统备份信息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    void systemBackupsConfig(SystemBackupDTO systemBackupDTO);


    /**
     * @description : 获取系统备份配置信息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    SystemBackupDTO getSystemBackupsConfig();


    /**
     * @description : 获取日志备份配置信息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    LogBackupDTO getLogBackupConfig();


    /**
     * @description :保存日志备份配置信息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    void saveLogBackupConfig(LogBackupDTO logBackupDTO);


    /**
     * 定期保存重启
     *
     * @param timing 时机
     * @author WeiQiangMiao
     * @date 2021-04-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void saveRestartRegularly(@NotNull(message = "定时时间不能为空") Long timing);

    /**
     * 定期删除重新启动
     *
     * @param sysTimingTaskId 系统定时任务id
     * @author WeiQiangMiao
     * @date 2021-04-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void deleteRestartRegularly(@NotNull(message = "定时重启id不能为空") Integer sysTimingTaskId);

    /**
     * 列表经常重启
     *
     * @return {@link List<SysTimingTaskVO> }
     * @author WeiQiangMiao
     * @date 2021-04-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysTimingTaskVO> listRestartRegularly(BaseDTO baseDTO);


    /**
     * 功能: 保存数据 如果code相同进行覆盖
     * 作者: zjt
     * 日期: 2022/1/13 16:59
     * 版本: 1.0
     */
    void saveDingDingServiceSetting(String serviceCode, String serviceName, String sysServiceValue, String sysServiceDefaultValue, String sysServiceDescription);

    /**
     * 功能: 根据名称删除
     * 作者: zjt
     * 日期: 2022/1/13 17:01
     * 版本: 1.0
     */
    void deleteByServiceCode(List<String> serviceCode);

    /**
     * 功能: 根据名称获取配置信息
     * 作者: zjt
     * 日期: 2022/1/13 17:12
     * 版本: 1.0
     */
    List<SysServiceSetting> getSysServiceSetting(List<String> serviceCodes);

    /**
     * 获取mqtt设置信息
     *
     * @return @return {@link MqttSetVO }
     * @author weiqiangmiao
     * @date 2022/05/12
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    MqttSetVO getMqttSetInfo();

    /**
     * 更新mqtt组信息
     *
     * @param mqttSetDTO mqtt设置dto
     * @author weiqiangmiao
     * @date 2022/05/12
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateMqttSetInfo(@Valid MqttSetDTO mqttSetDTO);
}
