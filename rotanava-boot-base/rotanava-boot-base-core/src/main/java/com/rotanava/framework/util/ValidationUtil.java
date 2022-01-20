package com.rotanava.framework.util;

import cn.hutool.core.exceptions.ValidateException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 验证工具类
 * @author: jintengzhou
 * @date: 2020-05-13 16:42
 */
public class ValidationUtil {

    /**
     * 使用hibernate的注解来进行验证
     */
    private static final Validator VALIDATOR = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    /**
     * 功能: 验证成功返回true
     * 作者: zjt
     * 日期: 2020/5/13 16:48
     * 版本: 1.0
     */
    public static <T> boolean isRightParams(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(obj);
        return constraintViolations.size() <= 0;
    }

    /**
     * 功能: 验证错误返回验证错误信息 正确返回空字符串
     * 作者: zjt
     * 日期: 2020/5/13 16:50
     * 版本: 1.0
     */
    public static <T> String validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(obj);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            return constraintViolations.iterator().next().getMessage();
        }
        return "";
    }

    /**
     * 验证错误返回验证错误信息 正确返回null
     *
     * @param obj obj
     * @return {@link String }
     * @author WeiQiangMiao
     * @date 2020-12-16
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static <T> String returnValidateError(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(obj);
        StringBuffer stringBuffer = new StringBuffer();
        if (constraintViolations.size() > 0) {
            constraintViolations.forEach(constraintViolation -> {
                stringBuffer.append(constraintViolation.getPropertyPath()).append(constraintViolation.getMessage()).append(",");
            });
            return stringBuffer.toString();
        } else {
            return null;
        }
    }

    /**
     * 功能: 验证错误抛出异常
     * 作者: zjt
     * 日期: 2020/5/13 16:54
     * 版本: 1.0
     */
    public static <T> void validateThrowException(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(obj);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            final String errorStr = constraintViolations.stream().map(constraintViolation -> constraintViolation.getPropertyPath() + constraintViolation.getMessage()).collect(Collectors.joining(", "));
            throw new ValidateException(String.format("参数校验失败:%s", errorStr));
        }
    }

}