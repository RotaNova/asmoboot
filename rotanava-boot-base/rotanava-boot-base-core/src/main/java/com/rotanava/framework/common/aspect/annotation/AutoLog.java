package com.rotanava.framework.common.aspect.annotation;

import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.constant.enums.ModuleType;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解
 *
 * @Author richenLi
 * @Date 2021年3月3日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {

    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";

    /**
     * 日志类型
     *
     * @return 0:登录日志;1:操作日志;2:定时任务;
     */
    int logType() default CommonConstant.LOG_TYPE_1;

    /**
     * 操作日志类型
     *
     * @return （1查询，2添加，3修改，4删除）
     */
    OperateTypeEnum operateType() default OperateTypeEnum.SELECT;

    /**
     * 模块类型 默认为common
     *
     * @return
     */
    ModuleType module() default ModuleType.COMMON;
}
