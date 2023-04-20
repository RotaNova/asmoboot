package com.rotanava.boot.system.module.system.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.rotanava.boot.system.api.module.system.bo.DingTalkRobot;
import com.rotanava.boot.system.api.module.system.bo.DingTalkRobotMsg;
import com.rotanava.boot.system.module.dao.DingTalkRobotMapper;
import com.rotanava.boot.system.module.dao.DingTalkRobotMsgMapper;
import com.rotanava.dingding.rebot.DingtalkChatbotClient;
import com.rotanava.framework.async.ThreadPoolUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 钉钉机器人消息通知任务
 * @author: richenLi
 * @create: 2022-10-24 16:22
 **/
@Log4j2
@Component
public class DingTalkRobotMsgTask {
    @Autowired
    DingTalkRobotMsgMapper dingTalkRobotMsgMapper;

    @Autowired
    DingTalkRobotMapper dingTalkRobotMapper;


    @XxlJob(value = "dingTalkRobotMsgTask")
    public ReturnT<String> dingTalkRobotMsgTask(String data) {
        try {
            log.info("开始执行发送钉钉消息任务");
            HashMap<Integer, List<DingTalkRobotMsg>> messageMap = new HashMap<>();
            List<DingTalkRobotMsg> dingTalkRobotMsgs = dingTalkRobotMsgMapper.selectList(new QueryWrapper<>());
            for (DingTalkRobotMsg dingTalkRobotMsg : dingTalkRobotMsgs) {
                Integer robotId = dingTalkRobotMsg.getRobotId();
                if (!messageMap.containsKey(robotId)) {
                    List<DingTalkRobotMsg> list = new ArrayList<>();
                    list.add(dingTalkRobotMsg);
                    messageMap.put(robotId, list);
                } else {
                    List<DingTalkRobotMsg> list = messageMap.get(robotId);
                    list.add(dingTalkRobotMsg);
                    messageMap.put(robotId, list);
                }
            }

            for (Integer robotId : messageMap.keySet()) {
                ThreadPoolUtil.execute(() -> {
                    StringBuffer text = new StringBuffer();
                    List<DingTalkRobotMsg> list = messageMap.get(robotId);
                    DingTalkRobot dingTalkRobot = dingTalkRobotMapper.selectById(robotId);
                    if (dingTalkRobot == null) {
                        return;
                    }

                    List<Integer> idList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        DingTalkRobotMsg dingTalkRobotMsg = list.get(i);
                        idList.add(dingTalkRobotMsg.getId());
                        text.append(String.format("第%s条告警消息\n", i+1));
                        text.append(dingTalkRobotMsg.getMsgText());
                        text.append("\n\n");
                    }
                    OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
                    markdown.setText(text.toString());
                    markdown.setTitle(String.format("%s条告警消息", list.size()));
                    OapiRobotSendRequest request = DingtalkChatbotClient.sendMarkdown(markdown);
                    DingtalkChatbotClient.send(dingTalkRobot.getWebhook(), dingTalkRobot.getKey(), request);



                    if (idList.size()>0) {
                        dingTalkRobotMsgMapper.deleteBatchIds(idList);
                    }

                });

            }
        } catch (Exception e) {
            log.error("钉钉发送消息异常", e);
        }
        return ReturnT.SUCCESS;
    }
}
