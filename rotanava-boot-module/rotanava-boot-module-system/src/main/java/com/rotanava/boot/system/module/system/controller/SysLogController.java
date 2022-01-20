package com.rotanava.boot.system.module.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysBehaviorLogService;
import com.rotanava.boot.system.api.module.system.bo.SysBehaviorLog;
import com.rotanava.boot.system.api.module.system.dto.GetSysBehaviorLogDTO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginInfoVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-10 17:09
 **/
@RestController
@RequestMapping("/v1/sysLog")
public class SysLogController {


    @Autowired
    private SysBehaviorLogService sysBehaviorLogService;


    @AdviceResponseBody
    @PostMapping("/getBehaviorLogList")
    @AutoLog("获取系统日志列表")
    public RetData<BaseVO<SysBehaviorLog>> getBehaviorLogList(@RequestBody GetSysBehaviorLogDTO getSysBehaviorLogDTO) {



        if (getSysBehaviorLogDTO.getSearchCodeIsNull("begin_time")) {
            getSysBehaviorLogDTO.setSearchCodeValue("begin_time", "ge", com.rotanava.framework.util.DateUtil.getTodayZero());
        }
        if (getSysBehaviorLogDTO.getSearchCodeIsNull("end_time")) {
            getSysBehaviorLogDTO.setSearchCodeValue("end_time", "le", com.rotanava.framework.util.DateUtil.getNowTime());
        }

        QueryWrapper<SysBehaviorLog> queryWrapper = QueryGenerator.initQueryWrapper(getSysBehaviorLogDTO);
        queryWrapper.orderByDesc("id");

        IPage<SysBehaviorLog> page = sysBehaviorLogService.page(PageUtils.startPage(getSysBehaviorLogDTO), queryWrapper);

       return new RetData<>(BuildUtil.buildListVO(page.getTotal(),page.getRecords(),getSysBehaviorLogDTO.getDateArray())) ;
    }

    @AdviceResponseBody
    @PostMapping("/listUserLoginInfoLog")
    @AutoLog("获取登录日志列表")
    public RetData<BaseVO<UserLoginInfoVO>> listUserLoginInfoLog(@RequestBody BaseDTO baseDTO) {
        return RetData.ok(sysBehaviorLogService.listUserLoginInfoLog(baseDTO));

    }


}
