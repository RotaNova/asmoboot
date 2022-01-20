package com.rotanava.framework.common.constant.enums;

import com.rotanava.framework.util.StringUtil;

/**
 * Query 规则 常量
 *
 * @author richenli
 * @date 2021-03-17
 */
public enum QueryRuleEnum {
    //Query 规则 常量

    /**
     * 大于
     */
    GT(">", "gt", "大于"),
    /**
     * 大于等于
     */
    GE(">=", "ge", "大于等于"),
    /**
     * 小于
     */
    LT("<", "lt", "小于"),
    /**
     * 小于等于
     */
    LE("<=", "le", "小于等于"),
    /**
     * 等于
     */
    EQ("=", "eq", "等于"),
    /**
     * 不等于
     */
    NE("!=", "ne", "不等于"),
    /**
     * 包含
     */
    IN("IN", "in", "包含"),
    /**
     * 全模糊
     */
    LIKE("LIKE", "like", "全模糊"),
    /**
     * 左模糊
     */
    LEFT_LIKE("LEFT_LIKE", "left_like", "左模糊"),
    /**
     * 右模糊
     */
    RIGHT_LIKE("RIGHT_LIKE", "right_like", "右模糊"),
    /**
     * 自定义SQL片段
     */
    SQL_RULES("USE_SQL_RULES", "ext", "自定义SQL片段");

    private String value;

    private String condition;

    private String msg;

    QueryRuleEnum(String value, String condition, String msg) {
        this.value = value;
        this.condition = condition;
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public static QueryRuleEnum getByValue(String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return null;
        }
        for (QueryRuleEnum val : values()) {
            if (val.getValue().equals(value) || val.getCondition().equals(value)) {
                return val;
            }
        }
        return null;
    }
}
