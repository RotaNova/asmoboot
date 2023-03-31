package com.rotanava.framework.config.dubbo.filter;

import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @description: dubbo调用数据源切换 激活提供者
 * @author: jintengzhou
 * @date: 2020-08-06 20:35
 */
@Log4j2
@Activate(group = {CommonConstants.PROVIDER})
public class RemoteDataSourceProviderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        final String userId = RpcContext.getContext().getAttachment("userId");
        UserSourceUtil.setUserID(userId);
        log.debug("dubbo 获取调用者的 userId={} 线程id={}", userId, Thread.currentThread());
        return invoker.invoke(invocation);
    }
}