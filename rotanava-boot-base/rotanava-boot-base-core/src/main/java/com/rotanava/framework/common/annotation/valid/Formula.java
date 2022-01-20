package com.rotanava.framework.common.annotation.valid;

import com.rotanava.framework.common.annotation.valid.impl.FormulaValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FormulaValidator.class)
public @interface Formula {

    String message() default "计算语句错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
