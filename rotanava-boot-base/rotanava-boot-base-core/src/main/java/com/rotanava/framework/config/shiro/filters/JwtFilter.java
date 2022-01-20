package com.rotanava.framework.config.shiro.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.config.shiro.JwtToken;
import com.rotanava.framework.config.shiro.UrlPermission;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.code.RetData;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @version :  1.0
 * @description :鉴权登录拦截器
 * @Author : richenLi
 * @Date : 2020/9/1 13:51
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private boolean allowOrigin = true;

    public JwtFilter() {
    }

    public JwtFilter(boolean allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        OutputStream os = response.getOutputStream();
        try {
            executeLogin(request, response);
            String method = ((ShiroHttpServletRequest) request).getMethod();
            String url = ((ShiroHttpServletRequest) request).getRequestURI();
            UrlPermission urlPermission = new UrlPermission(url, method);
            long begin = System.currentTimeMillis();
            getSubject(request, response).checkPermission(urlPermission);
            long end = System.currentTimeMillis();
            log.debug("shiro验权耗时{}ms", (end - begin));
            return true;
        } catch (AuthorizationException e) {
            if (e instanceof UnauthorizedException) {
                os.write(new ObjectMapper().writeValueAsString(RetData.error(AuthErrorCode.AUTH_ERROR_04)).getBytes(StandardCharsets.UTF_8));
            } else if (e instanceof UnauthenticatedException) {
                os.write(new ObjectMapper().writeValueAsString(RetData.error(AuthErrorCode.AUTH_ERROR_25)).getBytes(StandardCharsets.UTF_8));
            } else {
                os.write(new ObjectMapper().writeValueAsString(RetData.error()).getBytes(StandardCharsets.UTF_8));
            }
        } catch (CredentialsException | AccountException e) {
            os.write(new ObjectMapper().writeValueAsString(RetData.error().code(100500).message(e.getMessage())).getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            os.write(new ObjectMapper().writeValueAsString(RetData.error().code(500).message("服务运行异常")).getBytes(StandardCharsets.UTF_8));
        }
        os.flush();
        os.close();
        return false;
    }


    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(CommonConstant.X_ACCESS_TOKEN);

        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        if(allowOrigin){
//            httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//            httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//            //update-begin-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
//            // 是否允许发送Cookie，默认Cookie不包括在CORS请求之中。设为true时，表示服务器允许Cookie包含在请求中。
//            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            //update-end-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
//        }
//        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(HttpStatus.OK.value());
//            return false;
//        }
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", httpServletRequest.getMethod());
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(HttpStatus.OK.value());
//            log.info("option返回成功");
//            return false;
//        }
//        log.info("进入正常拦截器流程");
        return super.preHandle(request, response);

    }

//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpReq = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//
//        /*系统重定向会默认把请求头清空，这里通过拦截器重新设置请求头，解决跨域问题*/
//        httpServletResponse.addHeader("Access-Control-Allow-Origin", httpReq.getHeader("Origin"));
//        httpServletResponse.addHeader("Access-Control-Allow-Headers", "*");
//        httpServletResponse.addHeader("Access-Control-Allow-Methods", "*");
//        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
//        log.info("进入onAccessDenied拦截器流程");
//        this.saveRequestAndRedirectToLogin(request, response);
//        return false;
//    }
}
