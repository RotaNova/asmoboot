package com.rotanava.framework.config.dubbo.filter;


import com.rotanava.framework.common.api.CommonApi;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: dubbo调用数据源切换 激活消费者
 * @author: jintengzhou
 * @date: 2020-08-06 20:35
 */
@Log4j2
@Activate(group = {CommonConstants.CONSUMER})
public class RemoteDataSourceConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (StringUtils.isBlank(RpcContext.getContext().getAttachment("userId"))) {
            RpcContext.getContext().setAttachment("userId", UserSourceUtil.getUserId());
        } else {
            UserSourceUtil.setUserID(RpcContext.getContext().getAttachment("userId"));
        }

        log.debug("dubbo 获取当消费者的 userId={} 线程id={}", UserSourceUtil.getUserId(), Thread.currentThread());
        return invoker.invoke(invocation);
    }

}