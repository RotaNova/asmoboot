package com.rotanava.framework.common.annotation.valid;

import com.rotanava.framework.common.annotation.valid.impl.CodeNotBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * 代码
 *
 * @author weiqiangmiao
 * @date 2021/06/24
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodeNotBlankValidator.class)
public @interface CodeNotBlank {
    String message() default "输入英文和数字的编码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
