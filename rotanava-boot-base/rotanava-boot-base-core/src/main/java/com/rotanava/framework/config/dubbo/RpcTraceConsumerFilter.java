package com.rotanava.framework.config.dubbo;

import io.opentracing.Span;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

/**
 * @description: 链路追踪
 * @author: jintengzhou
 * @date: 2021-08-19 16:53
 */
@Slf4j
@Activate(group = {CommonConstants.CONSUMER})
public class RpcTraceConsumerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        RpcContext rpcContext = RpcContext.getContext();

        Result result = null;
        Span span = null;

        try {
            span = DubboTraceUtil.extractTraceFromLocalCtx(rpcContext);
        } catch (Exception ignored) {
            log.info("failed to extract span from rpcContext", ignored);
        }

        try {
            // attach the span context
            try {
                DubboTraceUtil.attachTraceToRemoteCtx(span, rpcContext);
            } catch (Exception ignored) {
            }

            // invoke the dubbo rpc method
            result = invoker.invoke(invocation);

        } catch (RpcException rpcException) {
            log.info("dubbo rpc consumer: received an exception", rpcException);
            if (span != null) {
                //Tags.ERROR.set(span, true);
                span.setTag("error", "1");
                span.setTag("error.code", rpcException.getCode());
                span.setTag("error.message", rpcException.getMessage());
            }
            throw rpcException;
        } finally {
            try {
                // 若consumer返回的结果为异常，则标记为错误-101
                if (result != null && result.getException() != null && span != null) {
                    Throwable e = result.getException();

                    span.setTag("error", "1");
                    span.setTag("error.code", "-101");
                    span.setTag("error.message", e.toString());
                }

                if (span != null) {
                    span.finish();
                }
            } catch (Exception ignored) {
            }
        }

        return result;
    }
}