package com.rotanava.boot.system.module.system.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.rotanava.boot.system.api.DeviceAuthService;
import com.rotanava.boot.system.api.module.constant.AuthDeviceInfoBean;
import com.rotanava.boot.system.api.module.system.bean.SystemAuthBean;
import com.rotanava.boot.system.api.module.system.bean.SystemTemporaryBean;
import com.rotanava.boot.system.module.system.mqtt.MqttGateway;
import com.rotanava.boot.system.util.RSAUtils;
import com.rotanava.framework.async.AsyncUtil;
import com.rotanava.framework.async.SyncFuture;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.exception.code.ServiceErrorCode;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.global.GlobalClass;
import com.rotanava.framework.model.SysMappingInfo;
import com.rotanava.framework.module.dao.BaseCommonMapper;
import com.rotanava.framework.util.*;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-06-30 17:19
 **/
@Service
@Log4j2
@DubboService
public class DeviceAuthServiceImpl implements DeviceAuthService, CommandLineRunner {
    @Autowired
    MqttGateway mqttGateway;

    @Value("${spring.profiles.active}")
    private String profiles;

    private String  privateKey;

    private static SystemAuthBean authBean;

    private static SystemTemporaryBean systemTemporaryBean;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    BaseCommonMapper baseCommonMapper;

    private static Long systemStartTime =System.currentTimeMillis();

    private static final String verifyAuthKey = "test0591";

    private static boolean expiration = false;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void systemVerifyTask() {

        log.info("开始执行校验系统鉴权是否失效");
        Date nowDate = new Date();
        if (authBean!=null){
            //校验是否过期
            Date authEndDate = authBean.getAuthEndDate();
            if ((nowDate.getTime()-authEndDate.getTime())>=0){
                //过期
                expiration = true;
            }
        }else{
            AuthDeviceInfoBean deviceInfo = getDeviceInfo();
            if (deviceInfo==null){
                //找不到设备指纹信息，直接授权失效
                expiration = true;
                return;
            }
            String key = SecureUtil.md5(JSONObject.toJSONString(deviceInfo)+"rotanova");
            SysMappingInfo sysMappingInfo = baseCommonMapper.getSysMappingInfoByKey(verifyAuthKey);
            if (sysMappingInfo!=null){
                try {
                    //解密过期时间和次数
                    String verifyAuthValue = sysMappingInfo.getValue();
                    String decryptInfo = DESUtil.Decrypt(verifyAuthValue, key);
                    SystemTemporaryBean systemTemporaryBean = JSONObject.parseObject(decryptInfo).toJavaObject(SystemTemporaryBean.class);
                    Date authEndDate = systemTemporaryBean.getAuthEndTime();
                    if ((nowDate.getTime() - authEndDate.getTime()) >= 0) {
                        //过期
                        expiration = true;
                    } else {
                        expiration = false;
                    }
                    DeviceAuthServiceImpl.systemTemporaryBean = systemTemporaryBean;
                }catch (Exception e){
                    log.error("数据库里鉴权数据解密失败",e);
                    expiration = false;
                }
            }else {
                //添加临时授权信息
                SystemTemporaryBean systemTemporaryBean = new SystemTemporaryBean();
                systemTemporaryBean.setStartTime(nowDate);
                Date endDate = Date8Util.addHour(nowDate, 24L);
                systemTemporaryBean.setAuthEndTime(endDate);
                systemTemporaryBean.setNum(1);
                String encryptInfo = DESUtil.Encrypt(JSONObject.toJSONString(systemTemporaryBean), key);
                sysMappingInfo = new SysMappingInfo();
                sysMappingInfo.setCreateTime(nowDate);
                sysMappingInfo.setKey(verifyAuthKey);
                sysMappingInfo.setValue(encryptInfo);
                baseCommonMapper.insert(sysMappingInfo);
                DeviceAuthServiceImpl.systemTemporaryBean = systemTemporaryBean;
            }
        }


    }


    @Override
    public void run(String... args) throws Exception {
        try {
            InputStream objectStream = fileUploadUtil.getObjectStream(BucketNamePool.COMMON_BUCKET, "auth.rn");
            if (objectStream != null) {
                String str = FileUtil.readInputStream(objectStream);
                JSONObject jsonObject = JSONObject.parseObject(str);
                String privateKey = jsonObject.getString("privateKey");
                String rsaSecretKey = jsonObject.getString("rsaSecretKey");
                if (!StringUtil.isNullOrEmpty(privateKey)) {
                    this.privateKey = privateKey;
                }
                if (!StringUtil.isNullOrEmpty(rsaSecretKey)) {
                    decodeAuth(rsaSecretKey);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        systemVerifyTask();
    }



    @Override
    public AuthDeviceInfoBean getDeviceInfo(){
        String rnMsgId = BaseUtil.getUId();
        try {

            JSONObject json = new JSONObject();
            json.put("rnMsgId",rnMsgId);
            mqttGateway.sendToMqtt("/rotanava/device/getDeviceInfo",0,json.toJSONString());

            SyncFuture syncFuture = AsyncUtil.creatMessage(rnMsgId);
            JSONObject result = (JSONObject) syncFuture.get(10,TimeUnit.SECONDS);
            AuthDeviceInfoBean deviceInfo = result.getJSONObject("deviceInfo").toJavaObject(AuthDeviceInfoBean.class);
            log.info("获取设备指纹信息{}",deviceInfo);
            return deviceInfo;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            AsyncUtil.removeThread(rnMsgId);
        }
        return null;
    }


    @Override
    public String getSignKey(){

            String deviceBase = getDeviceInfoBase64();
            if (deviceBase==null){
                throw new CommonException(SysErrorCode.SYS_ERROR_20);
            }
        try {
            Map<String, String> keyMap = RSAUtils.createKeys(1024);
            String publicKey = keyMap.get("publicKey");
            String privateKey = keyMap.get("privateKey");
            this.privateKey= privateKey;
            JSONObject json = new JSONObject();
            json.put("deviceInfo", deviceBase);
            json.put("publicKey", publicKey);



            log.info("获取加密信息{}",json);
            String base64 = Base64.getEncoder().encodeToString(json.toJSONString().getBytes("utf-8"));
            return base64;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getDeviceInfoBase64(){
        try {
            AuthDeviceInfoBean deviceInfo = getDeviceInfo();
            String deviceBase = Base64.getEncoder().encodeToString(JSONObject.toJSONString(deviceInfo).getBytes("utf-8"));
            return deviceBase;
        }catch (Exception e){

            log.error("获取设备base64编码失败",e);
            return null;
        }
    }

    public SystemAuthBean getAuthBean(){
        if (this.authBean==null){
            return null;
        }
        SystemAuthBean systemAuthBean = new SystemAuthBean();
        BeanUtils.copyProperties(this.authBean,systemAuthBean);
        return systemAuthBean;
    }


    public void decodeAuth(String rsaSecretKey){

            String rsaKey = new String(rsaSecretKey);
            log.info("---------------------------开始解密指纹------------------------------");
            String md5 = rsaSecretKey.substring(rsaSecretKey.length()-32);
            rsaSecretKey = rsaSecretKey.substring(0,rsaSecretKey.length()-32);
            String nowMd5 = SecureUtil.md5(rsaSecretKey+"rotanova");

            boolean md5Flag = nowMd5.equals(md5);
            log.info("md5校验结果{}",md5Flag);
            if (!md5Flag){
                throw new CommonException(SysErrorCode.SYS_ERROR_16);
            }
            String decodedData = null;
            try {
                decodedData = RSAUtils.privateDecrypt(rsaSecretKey, RSAUtils.getPrivateKey(this.privateKey));
            }catch (Exception e){
                log.error("加解密rsa异常",e);
                throw new CommonException(SysErrorCode.SYS_ERROR_17);
            }
            JSONObject result = JSONObject.parseObject(decodedData);
            String deviceBase64 = result.getString("deviceBase64");


            String deviceMd5 =  SecureUtil.md5(deviceBase64);
            String authInfo = DESUtil.Decrypt(result.getString("encrypt"),deviceMd5);
            JSONObject aiAuthDevice = JSONObject.parseObject(authInfo);
            log.info("ai信息="+aiAuthDevice.toJSONString());




            String nowDeviceBase = getDeviceInfoBase64();

            boolean deviceFlag = nowDeviceBase.equals(deviceBase64);

            log.info("设备信息比对{}",deviceFlag);
            if (!deviceFlag){
                throw new CommonException(SysErrorCode.SYS_ERROR_15);
            }


        Date authTime = aiAuthDevice.getDate("authTime");
        //授权天数
        int authPeriod = aiAuthDevice.getIntValue("authPeriod");
        long hour = authPeriod*24;
        Date authEndDate = Date8Util.addHour(authTime, hour);


        SystemAuthBean systemAuthBean = new SystemAuthBean();
        systemAuthBean.setDeviceInfo(nowDeviceBase);
        systemAuthBean.setAiQuota(aiAuthDevice.getInteger("aiQuota"));
        systemAuthBean.setAuthEndDate(authEndDate);
        systemAuthBean.setAiSecretKey(aiAuthDevice.getString("aiSecretKey"));
        systemAuthBean.setAuthPeriod(aiAuthDevice.getInteger("authPeriod"));
        systemAuthBean.setPrivateKey(this.privateKey);
        this.authBean = systemAuthBean;

        JSONObject json = new JSONObject();
        json.put("privateKey",this.privateKey);
        json.put("rsaSecretKey",rsaKey);

        DeviceAuthServiceImpl.expiration = false;

//        String authFileName = String.format("%s_auth.rn");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
        fileUploadUtil.rmObject(BucketNamePool.COMMON_BUCKET,"auth.rn");
        fileUploadUtil.uploadFile(BucketNamePool.COMMON_BUCKET,"auth.rn",byteArrayInputStream);

    }


    public boolean isExpiration(){
        return expiration;
    }


    public Date getAuthEndTime(){
        if (authBean!=null){
            return authBean.getAuthEndDate();
        }else if (DeviceAuthServiceImpl.systemTemporaryBean!=null){
            return DeviceAuthServiceImpl.systemTemporaryBean.getAuthEndTime();
        }
        return null;
    }


    public SystemTemporaryBean getSystemTemporaryBean(){
        return DeviceAuthServiceImpl.systemTemporaryBean;
    }





}
