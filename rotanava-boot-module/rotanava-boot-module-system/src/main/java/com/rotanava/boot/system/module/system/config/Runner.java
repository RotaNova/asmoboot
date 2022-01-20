package com.rotanava.boot.system.module.system.config;
import java.util.Date;

import cn.hutool.core.util.IdUtil;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.module.system.vo.GatewayInfoVO;
import com.rotanava.boot.system.api.module.system.vo.PlatformSettingVO;
import com.rotanava.boot.system.module.system.util.OkhttpsUtil;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.global.GlobalClass;
import com.rotanava.framework.model.SysMappingInfo;
import com.rotanava.framework.module.dao.BaseCommonMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.GregorianCalendar;

@Component
@Log4j2
public class Runner{

    @Autowired
    private GlobalClass globalClass;

    @Autowired
    @Lazy
    private OkhttpsUtil okhttpsUtil;

    @Autowired
    private SysServiceSettingService sysServiceSettingService;

    @Autowired
    private BaseCommonMapper baseCommonMapper;

    @Autowired
    FileUploadUtil fileUploadUtil;

//    @Value("rotanava.tenant.app.key")
//    private String tenantAppKey;
//
//    @Value("rotanava.tenant.app.secret")
//    private String tenantAppSecret;


    @PostConstruct
    @Async
    public void run() {
        try {
            String tenantInfoUuid = globalClass.getMappingValue("tenant_info_uuid");



            if (tenantInfoUuid == null) {

                HTTP http = okhttpsUtil.builder();

//            RetData retData = http.sync("/v1/manage/getToken")
//                    .addUrlParam("appKey", tenantAppKey)
//                    .addUrlParam("appSecret", tenantAppSecret)
//                    .nothrow().get().getBody().toBean(RetData.class);

                String siteName = sysServiceSettingService.getSiteName();
                String uuid = IdUtil.fastSimpleUUID();
                HttpResult post = http.sync("/v1/manage/addTenantInfo")
                        .addJsonParam("uuid", uuid)
                        .addJsonParam("name", siteName)
//                    .addJsonParam("token", retData.getData())
                        .nothrow().post();
                if (post.getStatus() == 200) {
                    SysMappingInfo sysMappingInfo = new SysMappingInfo();
                    sysMappingInfo.setKey("tenant_info_uuid");
                    sysMappingInfo.setValue(uuid);
                    sysMappingInfo.setCreateTime(new Date());
                    baseCommonMapper.insert(sysMappingInfo);
                    log.info("上报成功!");
                }
            }
        }catch (Exception e){
            log.error("租户上报异常",e);
        }

    }
}
