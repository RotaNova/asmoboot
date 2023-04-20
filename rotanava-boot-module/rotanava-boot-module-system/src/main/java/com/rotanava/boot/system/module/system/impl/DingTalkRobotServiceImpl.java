package com.rotanava.boot.system.module.system.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.github.houbb.html2md.util.Html2MdHelper;
import com.rotanava.boot.system.api.DingTalkRobotService;
import com.rotanava.boot.system.api.module.constant.AnnCategory;
import com.rotanava.boot.system.api.module.system.bo.DingTalkRobot;
import com.rotanava.boot.system.api.module.system.bo.DingTalkRobotMsg;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncementConfig;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.DingTalkRobotVO;
import com.rotanava.boot.system.module.dao.DingTalkRobotMapper;
import com.rotanava.boot.system.module.dao.DingTalkRobotMsgMapper;
import com.rotanava.boot.system.module.dao.SysAnnouncementConfigMapper;
import com.rotanava.dingding.rebot.DingtalkChatbotClient;
import com.rotanava.dingding.rebot.SendResult;
import com.rotanava.dingding.rebot.TextMessage;
import com.rotanava.framework.async.ThreadPoolUtil;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.exception.code.SysErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
@DubboService
public class DingTalkRobotServiceImpl implements DingTalkRobotService {

    @Autowired
    private DingTalkRobotMapper dingTalkRobotMapper;

    @Autowired
    private SysAnnouncementConfigMapper sysAnnouncementConfigMapper;

    @Autowired
    DingTalkRobotMsgMapper dingTalkRobotMsgMapper;

    @Override
    public void addDingTalkRobot(AddDingTalkRobotDTO addDingTalkRobotDTO) {
        QueryWrapper<DingTalkRobot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("webhook",addDingTalkRobotDTO.getWebhook());
        Integer count = dingTalkRobotMapper.selectCount(queryWrapper);
        if (count>0){
            throw new CommonException(SysErrorCode.SYS_ERROR_21);
        }
        DingTalkRobot dingTalkRobot = new DingTalkRobot();
        dingTalkRobot.setRobotName(addDingTalkRobotDTO.getRobotName());
        dingTalkRobot.setWebhook(addDingTalkRobotDTO.getWebhook());
        dingTalkRobot.setKey(addDingTalkRobotDTO.getKey());
        dingTalkRobotMapper.insert(dingTalkRobot);

        String updatedAnnDingTalkIds = JSONUtil.toJsonStr(new ArrayList<>(dingTalkRobot.getId()));
        SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(addDingTalkRobotDTO.getSysAnnConfigId());
        if (sysAnnouncementConfig != null ) {
            List<Integer> dingTalkIds = new ArrayList<>();
            if(!StringUtils.isEmpty(sysAnnouncementConfig.getAnnDingTalkIds()) && JSONUtil.isJsonArray(sysAnnouncementConfig.getAnnDingTalkIds())){
                dingTalkIds = JSONUtil.parseArray(sysAnnouncementConfig.getAnnDingTalkIds()).toList(Integer.class);
            }
            dingTalkIds.add(dingTalkRobot.getId());
            updatedAnnDingTalkIds = JSONUtil.toJsonStr(dingTalkIds);

        }
        sysAnnouncementConfigMapper.updateAnnDingTalkIdsById(updatedAnnDingTalkIds, addDingTalkRobotDTO.getSysAnnConfigId());
    }

    @Override
    public void deleteDingTalkRobot(DeleteDingTalkRobotDTO deleteDingTalkRobotDTO) {

        SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(deleteDingTalkRobotDTO.getSysAnnConfigId());
        if (sysAnnouncementConfig != null && !StringUtils.isEmpty(sysAnnouncementConfig.getAnnDingTalkIds()) && JSONUtil.isJsonArray(sysAnnouncementConfig.getAnnDingTalkIds())) {
            List<Integer> dingTalkIds = JSONUtil.parseArray(sysAnnouncementConfig.getAnnDingTalkIds()).toList(Integer.class);
            if (!CollectionUtils.isEmpty(dingTalkIds)) {
                if (dingTalkIds.contains(deleteDingTalkRobotDTO.getDingTalkRobotId())) {
                    dingTalkIds.removeIf(id -> id.equals(deleteDingTalkRobotDTO.getDingTalkRobotId()));
                    String updatedAnnDingTalkIds = JSONUtil.toJsonStr(dingTalkIds);
                    sysAnnouncementConfigMapper.updateAnnDingTalkIdsById(updatedAnnDingTalkIds, deleteDingTalkRobotDTO.getSysAnnConfigId());
                    dingTalkRobotMapper.deleteById(deleteDingTalkRobotDTO.getSysAnnConfigId());
                }
            }
        }

    }

    @Override
    public void updateDingTalkRobot(UpdateDingTalkRobotDTO updateDingTalkRobotDTO) {
        QueryWrapper<DingTalkRobot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("webhook",updateDingTalkRobotDTO.getWebhook());

        DingTalkRobot dbRobotId = dingTalkRobotMapper.selectOne(queryWrapper);
        if (!dbRobotId.getId().equals(updateDingTalkRobotDTO.getDingTalkRobotId())){
            throw new CommonException(SysErrorCode.SYS_ERROR_21);
        }


        SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(updateDingTalkRobotDTO.getSysAnnConfigId());
        if (sysAnnouncementConfig != null && !StringUtils.isEmpty(sysAnnouncementConfig.getAnnDingTalkIds()) && JSONUtil.isJsonArray(sysAnnouncementConfig.getAnnDingTalkIds())) {
            List<Integer> dingTalkIds = JSONUtil.parseArray(sysAnnouncementConfig.getAnnDingTalkIds()).toList(Integer.class);
            if (dingTalkIds.contains(updateDingTalkRobotDTO.getDingTalkRobotId())) {
                DingTalkRobot dingTalkRobot = new DingTalkRobot();
                dingTalkRobot.setRobotName(updateDingTalkRobotDTO.getRobotName());
                dingTalkRobot.setWebhook(updateDingTalkRobotDTO.getWebhook());
                dingTalkRobot.setKey(updateDingTalkRobotDTO.getKey());
                dingTalkRobot.setId(updateDingTalkRobotDTO.getDingTalkRobotId());
                dingTalkRobotMapper.updateById(dingTalkRobot);
            }

        }

    }

    @Override
    public List<DingTalkRobotVO> listDingTalkRobot(Integer sysAnnConfigId) {
        List<DingTalkRobotVO> dingTalkRobotVOList = new ArrayList<>();
        SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(sysAnnConfigId);
        String annDingTalkIds = sysAnnouncementConfig.getAnnDingTalkIds();
        if (sysAnnouncementConfig != null && !StringUtils.isEmpty(annDingTalkIds) && JSONUtil.isJsonArray(annDingTalkIds)) {
            List<Integer> dingTalkIds = JSONUtil.parseArray(annDingTalkIds).toList(Integer.class);
            if (!CollectionUtils.isEmpty(dingTalkIds)) {
                List<DingTalkRobot> dingTalkRobots = dingTalkRobotMapper.selectBatchIds(dingTalkIds);
                for (DingTalkRobot dingTalkRobot : dingTalkRobots) {
                    DingTalkRobotVO dingTalkRobotVO = new DingTalkRobotVO();
                    dingTalkRobotVO.setDingTalkRobotId(dingTalkRobot.getId());
                    dingTalkRobotVO.setRobotName(dingTalkRobot.getRobotName());
                    dingTalkRobotVO.setWebhook(dingTalkRobot.getWebhook());
                    dingTalkRobotVO.setKey(dingTalkRobot.getKey());
                    dingTalkRobotVOList.add(dingTalkRobotVO);
                }
            }

        }
        return dingTalkRobotVOList;
    }

    @Override
    public void testDingTalk(TestDingTalkRobotDTO testDingTalkRobotDTO) {

        SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(testDingTalkRobotDTO.getSysAnnConfigId());
        if (sysAnnouncementConfig != null && !StringUtils.isEmpty(sysAnnouncementConfig.getAnnDingTalkIds()) && JSONUtil.isJsonArray(sysAnnouncementConfig.getAnnDingTalkIds())) {
            List<Integer> dingTalkIds = JSONUtil.parseArray(sysAnnouncementConfig.getAnnDingTalkIds()).toList(Integer.class);
            if (!CollectionUtils.isEmpty(dingTalkIds)) {
                if (dingTalkIds.contains(testDingTalkRobotDTO.getDingTalkRobotId())) {
                    DingTalkRobot dingTalkRobot = dingTalkRobotMapper.selectById(testDingTalkRobotDTO.getDingTalkRobotId());
                    if(dingTalkRobot != null ){
                        String text = String.format("机器人名字：%s\n内容：这是一条测试消息",dingTalkRobot.getRobotName());
                        SendResult send = DingtalkChatbotClient.send(dingTalkRobot.getWebhook(), dingTalkRobot.getKey(), new TextMessage(text));
                        if (!send.isSuccess()) {
                            log.info("SendResult = {}",send);
                            throw new CommonException(CommonErrorCode.COMMON_ERROR_52);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void  sendDdingTalkByMsg(String webHook,String key,String title,String text){
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
                markdown.setText(text);
                markdown.setTitle(title);
                OapiRobotSendRequest request = DingtalkChatbotClient.sendMarkdown(markdown);
                DingtalkChatbotClient.send(webHook,key,request);
            }
        });

    }
    @Override
    public void senDdingTalk(SendDingTalkDTO sendDingTalkDTO) {



        SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(sendDingTalkDTO.getSysAnnConfigId());
        if (sysAnnouncementConfig != null && !StringUtils.isEmpty(sysAnnouncementConfig.getAnnDingTalkIds()) && JSONUtil.isJsonArray(sysAnnouncementConfig.getAnnDingTalkIds())) {
            List<Integer> dingTalkIds = JSONUtil.parseArray(sysAnnouncementConfig.getAnnDingTalkIds()).toList(Integer.class);
            if (!CollectionUtils.isEmpty(dingTalkIds)) {
                    List<DingTalkRobot> dingTalkRobots = dingTalkRobotMapper.selectBatchIds(dingTalkIds);
                    for (DingTalkRobot dingTalkRobot : dingTalkRobots) {
                        AnnCategory annCategory = sendDingTalkDTO.getAnnCategory();
                        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
                        String text = String.format(
                                        "<p>类型：%s</p>" +
                                        "<p>标题：%s</p>" +
                                        "<p>摘要：%s</p>" +
                                        "<p>内容：%s</p>"
                                ,AnnCategory.getName(annCategory.getType()), sendDingTalkDTO.getTitle(), sendDingTalkDTO.getAbstractContent(),sendDingTalkDTO.getContent());
                        String convert = Html2MdHelper.convert(text);
                        markdown.setText(convert);
                        markdown.setTitle( sendDingTalkDTO.getAbstractContent());
                        OapiRobotSendRequest request = DingtalkChatbotClient.sendMarkdown(markdown);
                        DingtalkChatbotClient.send(dingTalkRobot.getWebhook(),dingTalkRobot.getKey(),request);

//                        DingTalkRobotMsg dingTalkRobotMsg = new DingTalkRobotMsg();
//                        dingTalkRobotMsg.setCreateTime(new Date());
//                        dingTalkRobotMsg.setRobotId(dingTalkRobot.getId());
//                        dingTalkRobotMsg.setMsgText(convert);
//                        dingTalkRobotMsg.setMsgTitle(sendDingTalkDTO.getAbstractContent());
//                        dingTalkRobotMsgMapper.insert(dingTalkRobotMsg);
//                        if (!send.isSuccess()){
//                            log.info("钉钉消息{}  发送失败  {}",text,send);
//                        }
                    }
            }
        }




    }

    public static void main(String[] args) {
        String text =
                "<p>类型：告警消息</p>" +
                "<p>标题：F101检测告警结果</p>" +
                "<p>摘要：识别到汽车违规作业,可信度76.32%</p>" +
                "<p>内容：识别到汽车违规作业,可信度76.32%</p>" +
                "<p><img src=\"http://xh.rotanova.top:9000/rn-platform-errorface-resources/c4e73257-f84a-4dac-a7e6-19b3d7775283.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20221020%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20221020T085801Z&X-Amz-Expires=172800&X-Amz-SignedHeaders=host&X-Amz-Signature=da6e1634210a636c8c83f2b61a998877db3daad2c2c01626a43af581cd66c16a\" alt=\"事件图\"></img></p>" +
                "<p>背景图：<a href=\"http://xh.rotanova.top:9000/rn-platform-errorface-resources/d4acfd0b-abdc-4600-b64d-6c0ad1d05a5e.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20221020%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20221020T085801Z&X-Amz-Expires=172800&X-Amz-SignedHeaders=host&X-Amz-Signature=9c9cbf67ede0225e8c64256c81c1da43ffac70f5a336c0af83bece39113f9543\">链接</a></p>" +
                "<p>事件时间：2022-10-20 16:58:01</p>";

//        String url = "http://xh.rotanova.top:9000/rn-platform-errorface-resources/aca8a5a8-40dd-4b07-9ecb-81f6030b9d6d.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20221020%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20221020T071811Z&X-Amz-Expires=172800&X-Amz-SignedHeaders=host&X-Amz-Signature=5382cef5ebccde70aab3c39a136def65bf346f53aeaa631fa1ef5042ba7f75d7";
//        String text = String.format("<img src=\"%s\" alt=\"事件图\" >--------------<br>------------ ",url);

        String webHook = "https://oapi.dingtalk.com/robot/send?access_token=f8cc2e3eeae1c266a51dd3e29a912d1e2ef571561b2ce069d3d77f3dae6c810e";
        String key = "SEC560774b15052b5a9ece1574a5472a431816c988d20c979e844b25dc414156a0c";


        String convert = Html2MdHelper.convert(text);

        System.out.println("convert = " + convert);
//        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
//        markdown.setText(convert);
//        markdown.setTitle("识别到汽车违规作业,可信度76.32%");
//        OapiRobotSendRequest request = DingtalkChatbotClient.sendMarkdown(markdown);
//        DingtalkChatbotClient.send(webHook,key,request);



    }
}
