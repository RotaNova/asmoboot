package com.rotanava.framework.module.base.service;

import com.rotanava.framework.model.LogDTO;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SysBehaviorLog;
import org.apache.dubbo.config.annotation.DubboService;


/**
 * common接口
 */

public interface BaseLogService {

    /**
     * 保存日志
     * @param logDTO
     */
    void addLog(SysBehaviorLog logDTO);

    /**
     * 保存日志
     * @param LogContent
     * @param logType
     * @param operateType
     * @param user
     */
    void addLog(String LogContent, Integer logType, Integer operateType, LoginUser user);

    /**
     * 保存日志
     * @param LogContent
     * @param logType
     * @param operateType
     */
    void addLog(String LogContent, Integer logType, Integer operateType);

}
