package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rotanava.boot.system.api.module.system.bo.SysBackup;
import com.rotanava.boot.system.api.module.system.vo.SysBackupVO;
import com.rotanava.framework.model.BaseVO;

import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-06 10:19
 **/
public interface SysBackUpService extends IService<SysBackup> {
    Integer autoBackupType = 1;
    Integer manualBackupType = 2;
    Integer sysBackUpType = 1;
    Integer logBackUpType = 2;

    void sysBackUp(Integer backupType, Integer serviceType);


    BaseVO<SysBackupVO> getSysBackupList(Integer serviceType);

    /**
     * @description : 恢复出厂设置
     * @Author : richenLi
     * @version :  1.0
     */
    void systemFactoryReset();
}
