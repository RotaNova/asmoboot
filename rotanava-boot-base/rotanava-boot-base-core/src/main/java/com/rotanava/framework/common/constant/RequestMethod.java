package com.rotanava.framework.common.constant;

/**
 * 请求方式
 *
 * @author WeiQiangMiao
 * @date 2020/8/14
 */
public enum RequestMethod {
    //请求方法 1get 2post 3put 4delete
    /**
     * 获取请求
     */
    GET(1,"GET"),

    /**
     * 发布请求
     */
    POST(2,"POST"),

    /**
     * 修改请求
     */
    PUT(3,"PUT"),

    /**
     * 删除请求
     */
    DELETE(4,"DELETE");
    private Integer requestMethod;

    private String methodName;


    public static RequestMethod getRequestType(String methodName){
        methodName = methodName.toUpperCase();
        for (RequestMethod value : RequestMethod.values()) {
            if (value.getMethodName().equals(methodName)){
                return value;
            }
        }
        return null;
    }

    /**
     * 得到结果枚举类型
     *
     * @param requestMethod RequestMethod
     * @return {@link RequestMethod }
     * @author WeiQiangMiao
     * @date 2020-12-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static RequestMethod getEnumByRequestMethod(int requestMethod){
        //获取到枚举
        RequestMethod[] values = RequestMethod.values();
        for(RequestMethod value : values){
            if(value.getRequestMethod() == requestMethod){
                return value;
            }
        }
        return null;
    }

    RequestMethod(Integer requestMethod,String methodName) {
        this.requestMethod = requestMethod;
        this.methodName = methodName;
    }

    public Integer getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(Integer requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
