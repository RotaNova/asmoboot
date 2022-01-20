package com.rotanava.framework.config.dubbo;

import com.google.gson.Gson;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import io.opentracing.propagation.TextMapAdapter;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class DubboTraceUtil {

    public static void printContextByHeader(RpcContext rpcContext, String header) {
        log.info("ctx local on [{}], uber-trace-id = {}", header, rpcContext.get("uber-trace-id"));
        log.info("ctx remote on [{}], uber-trace-id = {}", header, rpcContext.getAttachment("uber-trace-id"));

    }

    public static void printContextInfo(RpcContext rpcContext) {
        log.info("ctx local on uber-trace-id = {}", rpcContext.get("uber-trace-id"));
        log.info("ctx remote on uber-trace-id = {}", rpcContext.getAttachment("uber-trace-id"));
    }

    public static void attachTraceToRemoteCtx(Span span, final RpcContext rpcContext) {
        Tracer tracer = GlobalTracer.get();
        if (tracer != null) {

            tracer.inject(span.context(), Format.Builtin.TEXT_MAP, new TextMap() {
                @Override
                public void put(String key, String value) {
                    rpcContext.setAttachment(key, value);
                }

                @Override
                public Iterator<Map.Entry<String, String>> iterator() {
                    throw new UnsupportedOperationException("TextMapInjectAdapter should only be used with Tracer.inject()");
                }
            });

        } else {
            log.warn("failed to get tracer.");
        }

    }

    public static Span extractTraceFromRemoteCtx(final RpcContext rpcContext) {
        return extractTraceFromCtx(rpcContext, Boolean.FALSE);
    }

    public static void attachTraceToLocalCtx(Span span, final RpcContext rpcContext) {
        Tracer tracer = GlobalTracer.get();
        if (tracer != null) {

            tracer.inject(span.context(), Format.Builtin.TEXT_MAP, new TextMap() {
                @Override
                public void put(String key, String value) {
                    rpcContext.set(key, value);
                }

                @Override
                public Iterator<Map.Entry<String, String>> iterator() {
                    throw new UnsupportedOperationException("TextMapInjectAdapter should only be used with Tracer.inject()");
                }
            });

        } else {
            log.warn("failed to get tracer.");
        }

    }

    public static Span extractTraceFromLocalCtx(final RpcContext rpcContext) {
        return extractTraceFromCtx(rpcContext, Boolean.TRUE);
    }

    private static Span extractTraceFromCtx(final RpcContext rpcContext, Boolean isLocalCtx) {
        Span span = null;

        Tracer tracer = GlobalTracer.get();
        if (tracer != null) {
            log.info("调用 dubbo serviceName名称 = {} dubbo 调用方法名 = {} dubbo 调用方法参数 = {}",
                    rpcContext.getUrl().getServiceKey(), rpcContext.getMethodName(), StringUtils.substring(new Gson().toJson(rpcContext.getArguments()), 0, 300));

            String apiName = String.format("%s:%s", rpcContext.getUrl().getServiceKey(), rpcContext.getMethodName());
            Tracer.SpanBuilder spanBuilder = tracer.buildSpan(apiName);

            try {
                TextMap textMap = null;
                if (isLocalCtx) {
                    Map<String, Object> values = rpcContext.get();
                    Map<String, String> contexts = new HashMap<>();
                    for (Map.Entry<String, Object> value : values.entrySet()) {
                        contexts.put(value.getKey(), value.getValue().toString());
                    }
                    textMap = new TextMapAdapter(contexts);
                } else {
                    textMap = new TextMapAdapter(rpcContext.getAttachments());
                }
                SpanContext spanContext = tracer.extract(Format.Builtin.TEXT_MAP, textMap);
                if (spanContext != null) {
                    spanBuilder.asChildOf(spanContext);
                }
            } catch (Exception e) {
                spanBuilder.withTag("Error", "extract from request fail, error msg:" + e.getMessage());
            }

            span = spanBuilder.start();

            // dubbo方法调用的参数
            String arguments = "";
            try {
                arguments = new Gson().toJson(rpcContext.getArguments());
            } catch (Exception ignored) {
            }
            span.setTag("arguments", arguments);

            if (rpcContext.isConsumerSide()) {
                Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_CLIENT);
            } else if (rpcContext.isProviderSide()) {
                Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_SERVER);
            }
            // TODO record db trace
            Tags.DB_INSTANCE.set(span, "db-instance");

            InetSocketAddress remoteAddress = rpcContext.getRemoteAddress();
            if (remoteAddress != null) {
                Tags.PEER_PORT.set(span, remoteAddress.getPort());
                Tags.PEER_HOSTNAME.set(span, remoteAddress.getHostName());
                Tags.PEER_SERVICE.set(span, rpcContext.getUrl().getServiceKey());

                InetAddress inetAddress = remoteAddress.getAddress();
                if (inetAddress instanceof Inet4Address) {
                    Tags.PEER_HOST_IPV4.set(span, remoteAddress.getHostString());
                } else if (inetAddress instanceof Inet6Address) {
                    Tags.PEER_HOST_IPV6.set(span, remoteAddress.getHostString());
                } else {
                    Tags.PEER_HOST_IPV4.set(span, remoteAddress.getHostString());
                }
            }


        } else {
            log.warn("failed to get tracer.");
        }

        return span;
    }
}
