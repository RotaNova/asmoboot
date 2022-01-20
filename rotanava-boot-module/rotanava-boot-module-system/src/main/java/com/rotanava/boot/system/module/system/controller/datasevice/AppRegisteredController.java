package com.rotanava.boot.system.module.system.controller.datasevice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.OpenAppApiService;
import com.rotanava.boot.system.api.OpenAppService;
import com.rotanava.boot.system.api.module.system.dto.ApiBindAppDTO;
import com.rotanava.boot.system.api.module.system.dto.CreateApplicationDTO;
import com.rotanava.boot.system.api.module.system.dto.EnableAppDTO;
import com.rotanava.boot.system.api.module.system.dto.GetOpenApiPageListDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateApplicationDTO;
import com.rotanava.boot.system.api.module.system.vo.OpenApiItemVO;
import com.rotanava.boot.system.api.module.system.vo.OpenAppItemVO;
import com.rotanava.boot.system.api.module.system.vo.OpenAppVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @description: 应用注册
 * @author: jintengzhou
 * @date: 2021-04-20 11:08
 */
@Api(tags = "应用注册")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/appRegistered")
public class AppRegisteredController {

    @Autowired
    private OpenAppService openAppService;

    @Autowired
    private OpenAppApiService openAppApiService;

    /**
     * 功能: 创建应用
     * 作者: zjt
     * 日期: 2021/4/19 15:06
     * 版本: 1.0
     */
    @PostMapping("createOpenApp")
    @AutoLog(value = "创建应用", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData createOpenApp(@RequestBody CreateApplicationDTO createApplication) {
        openAppService.createOpenApp(createApplication, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 更新应用
     * 作者: zjt
     * 日期: 2021/4/19 15:42
     * 版本: 1.0
     */
    @PostMapping("updateOpenApp")
    @AutoLog(value = "更新应用", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateOpenApp(@Validated UpdateApplicationDTO updateApplicationDTO, @RequestParam(name = "photoFile", required = false) MultipartFile photoFile) throws IOException {
        byte[] bytes = null;
        if (photoFile != null) {
            bytes = photoFile.getBytes();
        }
        openAppService.updateOpenApp(updateApplicationDTO, bytes);
        return RetData.ok();
    }

    /**
     * 功能: 删除应用
     * 作者: zjt
     * 日期: 2021/4/19 16:13
     * 版本: 1.0
     */
    @GetMapping("deleteOpenApp")
    @AutoLog(value = "删除应用", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteOpenApp(@NotNull Integer openAppId) {
        openAppService.deleteOpenApp(openAppId);
        return RetData.ok();
    }

    /**
     * 功能: 是否启用该应用
     * 作者: zjt
     * 日期: 2021/7/6 15:33
     * 版本: 1.0
     */
    @PostMapping("enableApp")
    @AutoLog(value = "是否启用该应用", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData enableApp(@RequestBody EnableAppDTO enableAppDTO) {
        openAppService.enableApp(enableAppDTO);
        return RetData.ok();
    }


    /**
     * 功能: 获取应用详情
     * 作者: zjt
     * 日期: 2021/4/19 16:15
     * 版本: 1.0
     */
    @GetMapping("getOpenApp")
    @AutoLog(value = "获取应用详情", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<OpenAppVO> getOpenApp(@NotNull Integer openAppId) {
        final OpenAppVO openApp = openAppService.getOpenApp(openAppId);
        return RetData.ok(openApp);
    }

    /**
     * 功能: 分页获取应用列表
     * 作者: zjt
     * 日期: 2021/4/19 17:34
     * 版本: 1.0
     */
    @PostMapping("getOpenAppPageList")
    @AutoLog(value = "分页获取应用列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<OpenAppItemVO>> getOpenAppPageList(@RequestBody BaseDTO baseDTO) {
        final IPage<OpenAppItemVO> openAppPageList = openAppService.getOpenAppPageList(baseDTO);
        return RetData.ok(openAppPageList);
    }

    /**
     * 功能: 分页获取权限接口列表
     * 作者: zjt
     * 日期: 2021/4/20 10:50
     * 版本: 1.0
     */
    @PostMapping("getOpenApiPageList")
    @AutoLog(value = "分页获取权限接口列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<OpenApiItemVO>> getOpenApiPageList(@RequestBody GetOpenApiPageListDTO getOpenApiPageList) {
        final IPage<OpenApiItemVO> openAppPageList = openAppApiService.getOpenApiPageList(getOpenApiPageList);
        return RetData.ok(openAppPageList);
    }

    /**
     * 功能: 添加已有接口权限
     * 作者: zjt
     * 日期: 2021/4/20 10:57
     * 版本: 1.0
     */
    @PostMapping("apiBindApp")
    @AutoLog(value = "添加已有接口权限", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData apiBindApp(@RequestBody ApiBindAppDTO apiBindAppDTO) {
        openAppApiService.apiBindApp(apiBindAppDTO.getOpenApiIdList(), apiBindAppDTO.getOpenAppId(), SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 移除接口权限
     * 作者: zjt
     * 日期: 2021/4/20 11:06
     * 版本: 1.0
     */
    @PostMapping("apiUnBindApp")
    @AutoLog(value = "移除接口权限", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData apiUnBindApp(@RequestBody ApiBindAppDTO apiBindAppDTO) {
        openAppApiService.apiUnBindApp(apiBindAppDTO.getOpenApiIdList(), apiBindAppDTO.getOpenAppId());
        return RetData.ok();
    }

}