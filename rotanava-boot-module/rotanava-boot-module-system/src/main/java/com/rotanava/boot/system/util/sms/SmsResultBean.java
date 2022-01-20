package com.rotanava.boot.system.util.sms;

public class SmsResultBean {
    private String code;
    private String msg;
    public SmsResultBean(){

    }
    public SmsResultBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
