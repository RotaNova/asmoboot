package com.rotanava.boot.system.util.sms;

import java.util.HashMap;

public class SmsSendBean {
   private String phoneNumbers;
    private HashMap<String,Object> params;
    private String templateId;
    private String smsSign;

    public SmsSendBean(String phoneNumbers, HashMap<String,Object> params, String templateId, String smsSign) {
        this.phoneNumbers = phoneNumbers;
        this.params = params;
        this.templateId = templateId;
        this.smsSign = smsSign;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public HashMap<String,Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String,Object> params) {
        this.params = params;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign;
    }
}
