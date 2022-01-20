package com.rotanava.framework.config.shiro;

import com.rotanava.framework.common.api.CommonApi;
import lombok.Data;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2022-01-19 16:21
 */
@Component
@Data
public class CommonApiLazyService {
    
    @DubboReference
    private CommonApi commonApi;
    
}