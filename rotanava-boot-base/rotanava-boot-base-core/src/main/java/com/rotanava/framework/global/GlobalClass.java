package com.rotanava.framework.global;

import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.util.SpringContextUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-25 15:39
 **/
@Component
public class GlobalClass {

    @DubboReference(injvm = false)
    private CommonApi commonApi;


    /**
     * @description : 获取映射表的value,方便程序动态获取值
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public String getMappingValue(String key) {

        return commonApi.getMappingValue(key);
    }



}
