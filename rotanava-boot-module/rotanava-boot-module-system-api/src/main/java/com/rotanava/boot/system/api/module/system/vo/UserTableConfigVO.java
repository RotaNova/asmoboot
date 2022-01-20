package com.rotanava.boot.system.api.module.system.vo;

import com.alibaba.fastjson.JSONObject;
import com.rotanava.boot.system.api.module.system.bo.SysTableField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-07-02 11:54
 **/
@Data
public class UserTableConfigVO {

    private List<SysTableField> fieldList;


    private JSONObject tableData;
}
