package com.rotanava.dingding.dingtalk;

import cn.hutool.core.convert.Convert;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.taobao.api.TaobaoResponse;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-10-25 16:01
 */
public class BaseSDK {

    /**
     * 功能: 检查返回错误
     * 作者: zjt
     * 日期: 2021/10/25 16:03
     * 版本: 1.0
     */
    protected static void checkRsp(TaobaoResponse taobaoResponse) {
//        if (StringUtils.isBlank(taobaoResponse.getErrorCode())) {
//            throw new CommonException(DBErrorCode.DB_ERROR_00);
//        }


        final int errorCode = Convert.toInt(taobaoResponse.getErrorCode(), 0);
        final String msg = taobaoResponse.getMsg();

        if (errorCode != 0) {
            throw new CommonException(new ErrorCode(errorCode, "钉钉接口:" + msg));
        }

    }


}