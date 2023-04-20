package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SysDingDingConfigService;
import com.rotanava.boot.system.api.module.system.dto.SysDingDingConfigDTO;
import com.rotanava.boot.system.api.module.system.vo.SysDingDingConfigVO;
import com.rotanava.framework.async.ThreadPoolUtil;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 功能: 钉钉对接配置
 * 作者: zjt
 * 日期: 2021/12/20 11:58
 * 版本: 1.0
 */
@Api(tags = "钉钉对接配置")
@Validated
@RestController
@RequestMapping("/v1/sysDingDingConfig")
public class SysDingDingConfigController {

    @Autowired
    private SysDingDingConfigService sysDingDingConfigService;


    /**
     * 功能: 保存配置
     * 作者: zjt
     * 日期: 2021/10/26 15:41
     * 版本: 1.0
     */
    @AutoLog(value = "保存配置", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/saveConfig")
    public RetData saveConfig(@RequestBody SysDingDingConfigDTO sysDingDingConfigDTO) {
        sysDingDingConfigService.saveConfig(sysDingDingConfigDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 获取配置
     * 作者: zjt
     * 日期: 2021/10/26 16:52
     * 版本: 1.0
     */
    @AutoLog(value = "获取配置", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getSysDingDingConfigVO")
    public RetData<SysDingDingConfigVO> getSysDingDingConfigVO() {
        return RetData.ok(sysDingDingConfigService.getSysDingDingConfigVO());
    }

    /**
     * 功能: 同步
     * 作者: zjt
     * 日期: 2021/12/16 17:41
     * 版本: 1.0
     */
    @AutoLog(value = "同步", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/syncDingDingUserData")
    public RetData syncDingDingUserData(@NotBlank String uid) throws Exception {
        Integer currentReqUserId = SysUtil.getCurrentReqUserId();
        ThreadPoolUtil.execute(() -> {
            sysDingDingConfigService.syncDingDingUserData(uid,currentReqUserId );
        });
        return RetData.ok();
    }

}