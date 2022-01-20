package com.rotanava.framework.common.constant.enums;


public enum OperateTypeEnum {
    SELECT(1,"查询"),
    ADD(2,"添加"),
    UPDATE(3,"更新"),
    DELETE(4,"删除"),
    ;

    private Integer type;

    private String name;

    OperateTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
