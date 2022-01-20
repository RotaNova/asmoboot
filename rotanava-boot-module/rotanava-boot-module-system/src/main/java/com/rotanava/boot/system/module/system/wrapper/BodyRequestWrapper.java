package com.rotanava.boot.system.module.system.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @description: application/json 包裝request
 * @author: jintengzhou
 * @date: 2020-07-02 14:04
 */
public class BodyRequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public BodyRequestWrapper(HttpServletRequest request, byte[] body) {
        super(request);
        this.body = body;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public void setAttribute(String name, Object o) {
        super.setAttribute(name, o);
    }

    /**
     * 功能: 在使用@RequestBody注解的时候，其实框架是调用了getInputStream()方法，所以我们要重写这个方法
     * 作者: zjt
     * 日期: 2020/7/2 14:08
     * 版本: 1.0
     */
    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            private final InputStream in = new ByteArrayInputStream(body);

            @Override
            public int read() throws IOException {
                return in.read();
            }

            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isReady() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // TODO Auto-generated method stub

            }
        };
    }
}