package com.rotanava.boot.system.module.system.controller.index;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.code.RetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-05-10 17:31
 **/
@RestController
@RequestMapping("/v1/index")
public class IndexController {

    @Autowired
    SysServiceSettingService sysServiceSettingService;

    @AdviceResponseBody
    @GetMapping("/getIndexTitle")
    @AutoLog(value = "获取前端页面title", operateType = OperateTypeEnum.SELECT)
    public RetData getIndexTitle() {
        QueryWrapper<SysServiceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_service_code","site_name");
        SysServiceSetting sysServiceSetting = sysServiceSettingService.getOne(queryWrapper);
        return RetData.ok(sysServiceSetting.getSysServiceValue());
    }



}
