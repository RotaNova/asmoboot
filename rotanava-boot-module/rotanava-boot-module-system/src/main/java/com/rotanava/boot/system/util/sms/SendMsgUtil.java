package com.rotanava.boot.system.util.sms;

import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.global.GlobalClass;
import com.rotanava.framework.util.RedisUtil;
import com.rotanava.framework.util.SpringContextUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Log4j2
@Component
public class SendMsgUtil {

    @Autowired
    private SMSUtil smsUtil;


    /**
     * 人脸复核拒绝发送短信
     *
     * @param phoneNum
     * @return
     */
    public SmsResultBean sendCheckPassMsg(String phoneNum, String name) {
        // 短信模板ID
        String templateId = "SMS_203672170";
        HashMap<String, Object> param = new HashMap<>();
        param.put("name", name);
        SmsSendBean smsSendBean = new SmsSendBean(phoneNum, param, templateId, CommonConstant.APPLET_SMS_SIGN);
        //调用发送短信工具类
        SmsResultBean resultBean = smsUtil.sendMsg(smsSendBean);
        return resultBean;
    }

    /**
     * 输入密码前发送短信
     *
     * @param templateId 模板
     * @param phoneNum   手机号
     * @param code       验证码
     */
    public  SmsResultBean sendPersonManageMsg(String templateId, String phoneNum, String code) {
        // 短信模板ID
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);
        //存入redis
        SmsSendBean smsSendBean = new SmsSendBean(phoneNum, param, templateId, CommonConstant.APPLET_SMS_SIGN);
        //调用发送短信工具类
        SmsResultBean resultBean = smsUtil.sendMsg(smsSendBean);
        return resultBean;
    }

    /**
     * 发送手机验证码
     *
     * @param templateId 模板
     * @param phoneNum   手机号
     */
    public  SmsResultBean send(String templateId, String phoneNum, String cache) {
        phoneNum = phoneNum.replaceAll("\\s*", "");
        String key = String.format("%s:%s", cache, phoneNum);

        long date = CommonConstant.VERIFICATION_CODE_DATE - CommonConstant.VERIFICATION_CODE_EXPIRE_DATE;
        RedisUtil redisUtil = SpringContextUtils.getBean("redisUtil", RedisUtil.class);
        long expire = redisUtil.getExpire(key);
        if (expire > date) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_29);
        }

        // 短信模板ID
        String code = Integer.toString(SMSUtil.random());
        HashMap<String, Object> param = new HashMap<>(1);
        param.put("code", code);

        SmsSendBean smsSendBean = new SmsSendBean(phoneNum, param, templateId, CommonConstant.APPLET_SMS_SIGN);
        //调用发送短信工具类

        SmsResultBean smsResultBean = smsUtil.sendMsg(smsSendBean);
        //存入redis
        redisUtil.set(key, code, CommonConstant.VERIFICATION_CODE_DATE);
        return smsResultBean;
    }

    /**
     * 功能: 发送消息通知短信
     * 作者: zjt
     * 日期: 2021/4/6 16:11
     * 版本: 1.0
     */
//    public static  SmsResultBean send(String templateId, String phoneNum, String cache) {
//        phoneNum = phoneNum.replaceAll("\\s*", "");
//        String key = String.format("%s:%s", cache, phoneNum);
//
//        long date = CommonConstant.VERIFICATION_CODE_DATE - CommonConstant.VERIFICATION_CODE_EXPIRE_DATE;
//        RedisUtil redisUtil = SpringContextUtils.getBean("redisUtil", RedisUtil.class);
//        long expire = redisUtil.getExpire(key);
//        if (expire > date) {
//            throw new CommonException(CommonErrorCode.COMMON_ERROR_29);
//        }
//
//        // 短信模板ID
//        String code = Integer.toString(SMSUtil.random());
//        HashMap<String, Object> param = new HashMap<>(1);
//        param.put("code", code);
//
//        SmsSendBean smsSendBean = new SmsSendBean(phoneNum, param, templateId, CommonConstant.APPLET_SMS_SIGN);
//        //调用发送短信工具类
//        SmsResultBean smsResultBean = SMSUtil.sendMsg(smsSendBean);
//        //存入redis
//        redisUtil.set(key, code, CommonConstant.VERIFICATION_CODE_DATE);
//        return smsResultBean;
//    }


}
