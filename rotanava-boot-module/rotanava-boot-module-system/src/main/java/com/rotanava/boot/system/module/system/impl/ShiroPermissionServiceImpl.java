package com.rotanava.boot.system.module.system.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rotanava.boot.system.api.ShiroPermissionService;
import com.rotanava.boot.system.api.module.system.dto.CheckUrlResPerDTO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.config.shiro.JwtToken;
import com.rotanava.framework.config.shiro.UrlPermission;
import com.rotanava.framework.exception.code.AuthErrorCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


@Service
@Slf4j
@DubboService
public class ShiroPermissionServiceImpl implements ShiroPermissionService {
    @Autowired
    DefaultWebSecurityManager securityManager;

    @Override
    @SneakyThrows
    public RetData<Void> checkUrlResPerByToken(CheckUrlResPerDTO checkUrlResPerDTO) {

        try {
            ThreadContext.bind(securityManager);
            Subject subject = SecurityUtils.getSubject();
            JwtToken jwtToken = new JwtToken(checkUrlResPerDTO.getToken());
            subject.login(jwtToken);
            UrlPermission urlPermission = new UrlPermission(checkUrlResPerDTO.getUrl(),checkUrlResPerDTO.getRequestMethod());
            long begin=System.currentTimeMillis();
            subject.checkPermission(urlPermission);
            long end=System.currentTimeMillis();
            log.debug("shiro验权耗时{}ms",(end-begin));
            return RetData.ok();
        } catch (AuthorizationException e){
            e.printStackTrace();
            if (e instanceof UnauthorizedException) {
                return RetData.error(AuthErrorCode.AUTH_ERROR_04);
            }
            else if(e instanceof UnauthenticatedException){
                return RetData.error(AuthErrorCode.AUTH_ERROR_25);
            }else {
                return RetData.error();
            }
        } catch (Exception e){
            e.printStackTrace();
            return RetData.error().code(100500).message(e.getMessage());
        }
    }



}
