package com.rotanava.boot.system.util.sms;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.global.GlobalClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SMSUtil {

    private final static Log log = LogFactory.getLog(SMSUtil.class);

    @Autowired
    private GlobalClass globalClass;

    /**
     * 阿里云发送短信
     * @param smsSendBean
     * @return
     */
    public SmsResultBean sendMsg(SmsSendBean smsSendBean) {
        SmsResultBean resultBean=new SmsResultBean();
        String regionId = globalClass.getMappingValue("regionId");
        String accessKeyId = globalClass.getMappingValue("accessKeyId");
        String accessSecret = globalClass.getMappingValue("accessSecret");

        if(StrUtil.isEmpty(regionId) || StrUtil.isEmpty(accessKeyId) || StrUtil.isEmpty(accessSecret) || StrUtil.isEmpty(smsSendBean.getTemplateId())){
            throw new CommonException(SysErrorCode.SYS_ERROR_11);
        }

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        try {
            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", smsSendBean.getPhoneNumbers());
            request.putQueryParameter("SignName", smsSendBean.getSmsSign());
            request.putQueryParameter("TemplateCode", smsSendBean.getTemplateId());

            request.putQueryParameter("TemplateParam", JSONObject.toJSONString(smsSendBean.getParams()));

            CommonResponse response = client.getCommonResponse(request);
            log.info("手机号:"+smsSendBean.getPhoneNumbers()+"的返回结果是"+response.getData());
            JSONObject result= JSONObject.parseObject(response.getData());
            if ( "OK".equals(result.getString("Code"))) {
                //发送成功
                resultBean.setCode("100");
                resultBean.setMsg("success");
            }else {
                resultBean.setCode("101");
                if (result.getString("Message").contains("触发天级流控")){
                    resultBean.setMsg("达到每天获取验证码的最大数量，请24小时后尝试");
                }else if (result.getString("Message").contains("触发分钟级流控")){
                    resultBean.setMsg("达到该分钟获取验证码的最大数量，请间隔1分钟后尝试");
                }
                else if (result.getString("Message").contains("触发小时级流控")){
                    resultBean.setMsg("达到每小时获取验证码的最大数量，请间隔1小时后尝试");
                }else {
                    resultBean.setMsg(result.getString("Message"));
                }
            }
        } catch (Exception e){
            resultBean.setCode("101");
            resultBean.setMsg(e.getMessage());
        }
        if (!"100".equals(resultBean.getCode())){
            throw new CommonException(new ErrorCode( SysErrorCode.SYS_ERROR_07.getCode(),resultBean.getMsg()));
        }
        return resultBean;
    }

    /*
        生成6位验证码
     */
    public static int random() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        int[] c = new int[6];
        for (int i = 0; i < 6; i++) {
            c[i] = r.nextInt(9) + 1;
            sb.append(c[i]);
        }
        return Integer.parseInt(sb.toString());

    }



}