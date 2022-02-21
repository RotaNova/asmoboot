package com.rotanava.boot.dbapi.api.vo;

import com.rotanava.framework.code.RetData;
import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-01-11 17:10
 **/
@Data
public class ExecuteResult {

    public static final Integer LIST_TYPE = 1;

    public static final Integer UPDATE_TYPE = 2;

    public static final Integer ERROR_TYPE = 3;

    private Object data;

    private Integer updateCount;

    private String errorMsg;

    private Integer type;


    public static  ExecuteResult listResult(Object data) {
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setData(data);
        executeResult.setType(1);
        return executeResult;
    }

    public static  ExecuteResult updateResult(Integer updateCount) {
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setUpdateCount(updateCount);
        executeResult.setType(2);
        return executeResult;
    }

    public static  ExecuteResult errorResult(String errorMsg) {
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setErrorMsg(errorMsg);
        executeResult.setType(3);
        return executeResult;
    }
}
