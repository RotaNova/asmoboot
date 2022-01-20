package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.dto.SysDingDingConfigDTO;
import com.rotanava.boot.system.api.module.system.vo.SysDingDingConfigVO;
import org.springframework.validation.annotation.Validated;

/**
 * @description: 钉钉对接配置
 * @author: jintengzhou
 * @date: 2021-10-25 18:04
 */
@Validated
public interface SysDingDingConfigService {

    /**
     * 功能: 保存配置
     * 作者: zjt
     * 日期: 2021/10/26 15:41
     * 版本: 1.0
     */
    void saveConfig(@Validated SysDingDingConfigDTO sysDingDingConfigDTO, Integer currentReqUserId);

    /**
     * 功能: 获取配置
     * 作者: zjt
     * 日期: 2021/10/26 16:52
     * 版本: 1.0
     */
    SysDingDingConfigVO getSysDingDingConfigVO();

    /**
     * 功能: 同步
     * 作者: zjt
     * 日期: 2021/12/16 17:41
     * 版本: 1.0
     */
    void syncDingDingUserData(String uid, Integer currentReqUserId) ;

}
