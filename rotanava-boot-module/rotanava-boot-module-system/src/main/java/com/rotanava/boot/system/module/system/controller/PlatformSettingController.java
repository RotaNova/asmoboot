package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.module.system.dto.PlatformSettingDTO;
import com.rotanava.boot.system.api.module.system.vo.PlatformSettingVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.ImageUtil;
import com.rotanava.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-23 15:00
 **/
@RestController
@RequestMapping("/v1/platformSetting")
public class PlatformSettingController {

    @Autowired
    SysServiceSettingService sysServiceSettingService;

    @AutoLog("获取平台配置")
    @AdviceResponseBody
    @GetMapping("/getPlatformSetting")
    public RetData<PlatformSettingVO> getPlatformSetting() {
        return new RetData<>(sysServiceSettingService.getPlatformSetting());
    }





    @AutoLog("修改平台配置")
    @AdviceResponseBody
    @PostMapping("/savePlatformSetting")
    public RetData savePlatformSetting(@RequestParam(value = "logoFile",required = false)MultipartFile logoFile,
                                                          @RequestParam(value = "bannerFile",required = false)MultipartFile bannerFile,
                                                          @RequestParam(value = "logoOption",required = false)Integer logoOption,
                                                          @RequestParam(value = "bannerOption",required = false)Integer bannerOption,
                                                          @RequestParam(value = "bannerCloseOption",required = false)Integer bannerCloseOption,
                                                          @RequestParam(value = "bannerFrequency",required = false)Integer bannerFrequency,
                                                          @RequestParam(value = "siteOption",required = false)Integer siteOption,
                                                          @RequestParam(value = "siteName",required = false)String siteName) throws IOException {

        if (StringUtil.isNullOrEmpty(bannerOption)){
            return BuildUtil.buildParamError();
        }
//        if (StringUtil.isNullOrEmpty(logoOption)){
//            return BuildUtil.buildParamError();
//        }
        if (StringUtil.isNullOrEmpty(bannerCloseOption)){
            return BuildUtil.buildParamError();
        }
        PlatformSettingDTO platformSettingDTO = new PlatformSettingDTO();
        platformSettingDTO.setBannerCloseOption(bannerCloseOption);
        platformSettingDTO.setBannerFrequency(bannerFrequency);
        platformSettingDTO.setBannerOption(bannerOption);
        platformSettingDTO.setLogoOption(logoOption);
        platformSettingDTO.setSiteOption(siteOption);
        platformSettingDTO.setSiteName(siteName);

        if (logoFile!=null){
            platformSettingDTO.setLogoImage(ImageUtil.getBase64FromInputStream(logoFile.getInputStream()));
        }
        if (bannerFile!=null){
            platformSettingDTO.setBannerImage(ImageUtil.getBase64FromInputStream(bannerFile.getInputStream()));
        }
        sysServiceSettingService.savePlatformSetting(platformSettingDTO);
        return BuildUtil.buildSuccessResult();
    }



    @AutoLog("重置平台配置")
    @AdviceResponseBody
    @PostMapping("/resetPlatformSetting")
    public RetData resetPlatformSetting() {
        sysServiceSettingService.resetPlatformSetting();
        return BuildUtil.buildSuccessResult();
    }


}
