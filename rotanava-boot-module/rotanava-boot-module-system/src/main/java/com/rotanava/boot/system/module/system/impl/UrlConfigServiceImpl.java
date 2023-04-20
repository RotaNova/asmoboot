package com.rotanava.boot.system.module.system.impl;

import com.rotanava.boot.system.api.UrlConfigService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * url配置服务impl
 *
 * @author weiqiangmiao
 * @date 2022/08/18
 */
@Service
@DubboService
public class UrlConfigServiceImpl implements UrlConfigService {


    @Override
    public String getLocalAlgorithmBaseUrl() {
        return "http://192.168.0.117:9877";
    }

}
