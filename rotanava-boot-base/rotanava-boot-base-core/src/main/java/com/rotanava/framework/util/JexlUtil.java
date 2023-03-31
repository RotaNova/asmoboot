package com.rotanava.framework.util;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * jexl跑龙套
 *
 * @author weiqiangmiao
 * @date 2021/06/23
 */
public class JexlUtil {

    /**
     * 解析计算公式
     *
     * @param formula 公式
     * @param value   瓦尔
     * @return {@link Object }
     * @author weiqiangmiao
     * @date 2021/07/12
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static Object analyticalMathematicalFormula(String formula,String value) throws ScriptException {
        //替换中英文括号
        formula = formula.replace("（","(");
        formula = formula.replace("）",")");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.put("val",value);
        return engine.eval(formula);
    }

}
