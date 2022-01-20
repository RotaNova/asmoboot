package com.rotanava.boot.system.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-06-03 15:33
 **/
public class AliTest {

    public static void main(String[] args) throws Exception {
        String accountAccessAK = "LTAI4FvtunJYHcm2CURv8F24";
        String accountAccessSK = "CVxJGD0SubZmsROLM37kI1VFXTBfrd";
        String popRegion = "cn-shanghai";
        String popProduct = "Chatbot";
        String popDomain = "chatbot.cn-shanghai.aliyuncs.com";
        DefaultProfile.addEndpoint(popRegion, popProduct, popDomain);
        IClientProfile profile = DefaultProfile.getProfile(popRegion, accountAccessAK, accountAccessSK);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        //固定入参
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysProduct("Chatbot");
        commonRequest.setSysMethod(MethodType.GET);
        //根据API会有变化
        commonRequest.setSysAction("Chat");
        commonRequest.setSysVersion("2017-10-11");
        commonRequest.putQueryParameter("Utterance", "阿里巴巴是什么");
        //机器人id
        commonRequest.putQueryParameter("InstanceId", "chatbot-cn-mz7mjbEZBe");
        CommonResponse commonResponse = client.getCommonResponse(commonRequest);
        System.out.println(commonResponse.getData());
        JSONObject jsonObject = JSONObject.parseObject(commonResponse.getData());
        String msg = jsonObject.getJSONArray("Messages").getJSONObject(0).getJSONObject("Text").getString("Content");
        System.out.println(msg);



    }
}
