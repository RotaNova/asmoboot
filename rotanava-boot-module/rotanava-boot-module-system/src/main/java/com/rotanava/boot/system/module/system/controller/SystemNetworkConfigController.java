package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.module.system.dto.GatewayInfoDTO;
import com.rotanava.boot.system.api.module.system.vo.GatewayInfoVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.code.RetData;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网络配置控制器
 *
 * @author WeiQiangMiao
 * @date 2021-03-25
 */
@Api(tags = "网络配置")
@RestController
@RequestMapping("/v1/networkConfig")
public class SystemNetworkConfigController {


    @Autowired
    private SysPlatformDeployService sysPlatformDeployService;

    @AutoLog("网关数据获取")
    @AdviceResponseBody
    @GetMapping("/getGatewayInfo")
    public RetData<List<GatewayInfoVO>> getGatewayInfo() {
        return RetData.ok(sysPlatformDeployService.getGatewayInfo());
    }

    @AutoLog(value = "修改网关信息",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateGatewayConfig")
    public RetData<Void> updateGatewayConfig(@RequestBody GatewayInfoDTO gatewayInfoDTO) {
        sysPlatformDeployService.updateGatewayConfig(gatewayInfoDTO);
        return RetData.ok();
    }




}
