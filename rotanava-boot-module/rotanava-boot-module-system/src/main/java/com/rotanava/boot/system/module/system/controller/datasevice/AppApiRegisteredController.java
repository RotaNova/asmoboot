package com.rotanava.boot.system.module.system.controller.datasevice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.OpenAppApiService;
import com.rotanava.boot.system.api.module.system.dto.GetOpenApiPageListDTO;
import com.rotanava.boot.system.api.module.system.dto.OpenAppApiDTO;
import com.rotanava.boot.system.api.module.system.dto.RegisteredOpenAppApiDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveSqlConfigurationDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateOpenAppApiDTO;
import com.rotanava.boot.system.api.module.system.vo.OpenApiItemVO;
import com.rotanava.boot.system.api.module.system.vo.SqlConfigurationVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @description: 应用api注册
 * @author: jintengzhou
 * @date: 2021-04-20 11:08
 */
@Api(tags = "API管理")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/appApiRegistered")
public class AppApiRegisteredController {

    @Autowired
    private OpenAppApiService openAppApiService;


    /**
     * 功能: 注册api
     * 作者: zjt
     * 日期: 2021/4/19 15:06
     * 版本: 1.0
     */
    @PostMapping("registeredOpenAppApi")
    @AutoLog(value = "注册api", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData registeredOpenAppApi(@RequestBody RegisteredOpenAppApiDTO registeredOpenAppApiDTO) {
        openAppApiService.registeredOpenAppApi(registeredOpenAppApiDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 修改api
     * 作者: zjt
     * 日期: 2021/4/19 15:06
     * 版本: 1.0
     */
    @PostMapping("updateOpenAppApi")
    @AutoLog(value = "修改api", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData updateOpenAppApi(@RequestBody UpdateOpenAppApiDTO updateOpenAppApiDTO) {
        openAppApiService.updateOpenAppApi(updateOpenAppApiDTO);
        return RetData.ok();
    }

    /**
     * 功能: 移除api
     * 作者: zjt
     * 日期: 2021/4/20 9:40
     * 版本: 1.0
     */
    @PostMapping("removeOpenAppApi")
    @AutoLog(value = "移除api", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData removeOpenAppApi(@RequestBody OpenAppApiDTO openAppApiDTO) {
        openAppApiService.removeOpenAppApi(openAppApiDTO.getOpenApiId());
        return RetData.ok();
    }

    /**
     * 功能: 锁定api
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    @PostMapping("lockOpenAppApi")
    @AutoLog(value = "锁定api", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData lockOpenAppApi(@RequestBody OpenAppApiDTO openAppApiDTO) {
        openAppApiService.lockOpenAppApi(openAppApiDTO.getOpenApiId());
        return RetData.ok();
    }

    /**
     * 功能: 解锁api
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    @PostMapping("unLockOpenAppApi")
    @AutoLog(value = "解锁api", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData unLockOpenAppApi(@RequestBody OpenAppApiDTO openAppApiDTO) {
        openAppApiService.unLockOpenAppApi(openAppApiDTO.getOpenApiId());
        return RetData.ok();
    }

    /**
     * 功能: 保存sql配置
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    @PostMapping("saveSqlConfiguration")
    @AutoLog(value = "保存sql配置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData saveSqlConfiguration(@RequestBody SaveSqlConfigurationDTO saveSqlConfigurationDTO) {
        openAppApiService.saveSqlConfiguration(saveSqlConfigurationDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 获取sql配置
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    @GetMapping("getSqlConfiguration")
    @AutoLog(value = "获取sql配置", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<SqlConfigurationVO> getSqlConfiguration(@NotNull Integer openApiId) {
        final SqlConfigurationVO sqlConfiguration = openAppApiService.getSqlConfiguration(openApiId);
        return RetData.ok(sqlConfiguration);
    }

    /**
     * 功能: 分页获取api管理列表
     * 作者: zjt
     * 日期: 2021/4/20 10:50
     * 版本: 1.0
     */
    @PostMapping("getOpenApiPageList")
    @AutoLog(value = "分页获取api管理列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<OpenApiItemVO>> getOpenApiPageList(@RequestBody GetOpenApiPageListDTO getOpenApiPageListDTO) {
        final IPage<OpenApiItemVO> openAppPageList = openAppApiService.getOpenApiPageList(getOpenApiPageListDTO);
        return RetData.ok(openAppPageList);
    }

}