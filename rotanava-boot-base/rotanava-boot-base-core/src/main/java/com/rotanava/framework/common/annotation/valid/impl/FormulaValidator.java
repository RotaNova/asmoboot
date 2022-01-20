package com.rotanava.framework.common.annotation.valid.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.common.annotation.valid.Formula;
import com.rotanava.framework.util.JexlUtil;
import org.springframework.util.StringUtils;

import javax.script.ScriptException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author WeiQiangMiao
 * @date 2020/7/7
 */
public class FormulaValidator implements ConstraintValidator<Formula, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        int count = StrUtil.count(value, "val");
        if (count <= 0) {
            return false;
        }

        if (StrUtil.containsAny(value, "=","//")) {
            return false;
        }


        try {
            Object val = JexlUtil.analyticalMathematicalFormula(value, "1");
            if (val == null) {
                return false;
            }
            if (!NumberUtil.isNumber(Convert.toStr(val))) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


}
