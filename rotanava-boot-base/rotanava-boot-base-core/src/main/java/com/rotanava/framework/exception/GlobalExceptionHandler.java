package com.rotanava.framework.exception;


import cn.hutool.core.exceptions.ValidateException;
import com.google.common.collect.Lists;
import com.rotanava.framework.code.BaseException;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.util.BuildUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private final static Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, ValidateException.class})
    public Object handleHttpMessageNotReadableException(Exception e) {
        if (e instanceof BindException) {
            final BindException bindException = (BindException) e;
            final BindingResult bindingResult = bindException.getBindingResult();
            final String errorStr = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return BuildUtil.buildError(400, errorStr);
        }
        if (e instanceof ValidateException) {
            final ValidateException validateException = (ValidateException) e;
            return BuildUtil.buildError(400, validateException.getMessage());
        } else {
            log.error(String.format("参数解析失败 %s", e.getMessage()), e);
            return BuildUtil.buildError(400, "参数解析失败,请检查传参");
        }
    }


    /**
     * 407 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Object handleConstraintViolationException(Exception e) {
        log.error(String.format("参数解析失败 %s", e.getMessage()));
        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
        List<String> msgList = Lists.newArrayList();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            final Path propertyPath = constraintViolation.getPropertyPath();
            String params = propertyPath.toString();
            final int indexOf = params.indexOf(".");
            if (indexOf != -1) {
                params = params.substring(indexOf + 1);
            }
            msgList.add(params + constraintViolation.getMessage());
        }
        String messages = String.join(",", msgList);
        return BuildUtil.buildError(407, messages);
    }

    /**
     * 实体类解析失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleBindException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error(fieldError.getDefaultMessage());
        return BuildUtil.buildParamError(fieldError.getField() + fieldError.getDefaultMessage());
    }


    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return BuildUtil.buildError(405, "不支持当前请求方法");
    }

    /**
     * 其他全局异常在此捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Object handleException(Throwable e) {


        log.error("服务运行异常", e);

        return BuildUtil.buildError(500, "服务运行异常");
    }


    /**
     * 数据库异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Object sqlException(DataIntegrityViolationException e) {
        log.error("数据库操作异常", e);
        return BuildUtil.buildErrorTextResult("数据库操作异常");
    }


    /**
     * token异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CommonException.class)
    public Object commonException(BaseException e) {
        if (e.getErrorCode() != null) {
            return BuildUtil.buildErrorResult(e.getErrorCode());
        }
        return null;
    }


}