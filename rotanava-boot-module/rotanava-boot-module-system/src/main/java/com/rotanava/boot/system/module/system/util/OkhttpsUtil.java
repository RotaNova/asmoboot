package com.rotanava.boot.system.module.system.util;


import com.ejlchina.okhttps.*;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.global.GlobalClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OkhttpsUtil {

    @Autowired
    private GlobalClass globalClass;

    public HTTP builder(){

        HTTP http = HTTP.builder()
                .baseUrl(globalClass.getMappingValue("tenant_url"))
                .build();
        return http;
    }

    public static HttpResult result(HttpResult result){

        // 判断执行状态
        switch (result.getState()) {
            case RESPONSED:     // 请求已正常响应
                break;
            case CANCELED:      // 请求已被取消
            case NETWORK_ERROR: // 网络错误，说明用户没网了
            case TIMEOUT:       // 请求超时
            case EXCEPTION:     // 其它异常
                throw new CommonException(new ErrorCode(result.getStatus() ,result.getError().getMessage()));
        }
        return result;

    }

}
