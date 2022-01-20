package com.rotanava.framework.common.annotation.valid.impl;


import com.rotanava.framework.common.annotation.valid.CodeNotBlank;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author WeiQiangMiao
 * @date 2020/7/7
 */
public class CodeNotBlankValidator implements ConstraintValidator<CodeNotBlank, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(value)){
            return false;
        }
        String regex = "^[a-z0-9A-Z]+$";
        final Pattern compile = Pattern.compile(regex);
        return compile.matcher(value).matches();
    }

}
