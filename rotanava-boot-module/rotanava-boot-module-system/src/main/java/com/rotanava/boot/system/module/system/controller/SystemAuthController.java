package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.DeviceAuthService;
import com.rotanava.boot.system.api.SystemAuthService;
import com.rotanava.boot.system.api.module.system.vo.GetAuthInfoVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;


@Api(tags = "系统授权")
@Slf4j
@Validated
//@RestController
@Controller
@RequestMapping("/v1/systemAuth")
public class SystemAuthController {


    @Autowired
    private SystemAuthService systemAuthService;

    @AutoLog(value = "获取ai授权信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getAiAuthInfo")
    public RetData<String> getAiAuthInfo() {
        return RetData.ok(systemAuthService.getAiAuthInfo());
    }

    @AutoLog(value = "下载机械码", operateType = OperateTypeEnum.SELECT)
//    @AdviceResponseBody
    @PostMapping("/downloadMachineCode")
    public RetData<Void> downloadMachineCode(HttpServletResponse response) {
        systemAuthService.downloadMachineCode(response);
        return RetData.ok();
    }

    @AutoLog(value = "获取授权信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getAuthInfo")
    public RetData<GetAuthInfoVO> getAuthInfo() {
        return RetData.ok(systemAuthService.getAuthInfo());
    }


    @AutoLog(value = "上传许可证", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/uploadLicense")
    public RetData<Void> uploadLicense( @Validated @NotNull @RequestParam(name = "file") MultipartFile file) {
        systemAuthService.systemAuthService(file);
        return RetData.ok();
    }


}
