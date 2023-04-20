package com.rotanava.boot.system.module.system.task;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysUserAnnouncementService;
import com.rotanava.boot.system.api.module.constant.AnnReadFlag;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementItemVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.util.JwtUtil;
import com.rotanava.framework.util.RedisUtil;
import com.rotanava.framework.util.socket.PcMessageUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 发送websocket事件
 *
 * @author weiqiangmiao
 * @date 2023/03/03
 */
@Log4j2
@Component
public class AnnouncementWindowsUnReadNumTask {

    @Autowired
    private SysUserAnnouncementService sysUserAnnouncementService;

    @Autowired
    private PcMessageUtil pcMessageUtil;

    @Autowired
    private RedisUtil redisUtil;

    @XxlJob(value = "announcementWindowsUnReadNumTask")
    public ReturnT<String> announcementWindowsUnReadNumTask(String data) {

        //获取在线用户token列表
        Set<String> tokens = redisUtil.listOnline();
        Set<Integer> sysUserIds = tokens.stream().map(JwtUtil::getUserId).collect(Collectors.toSet());
        for (Integer sysUserId : sysUserIds) {
            final BaseDTO baseDTO = new BaseDTO();
            baseDTO.setPageNum(1);
            baseDTO.setPageSize(1);
            final IPage<UserAnnouncementItemVO> userAnnouncementItemPage =
                    sysUserAnnouncementService.getUserAnnouncementItemPage(baseDTO, sysUserId, null, AnnReadFlag.UNREAD.getType());
            pcMessageUtil.sendMessageByUserId(sysUserId, "announcementWindowsUnReadNum", userAnnouncementItemPage.getTotal());
        }
        return ReturnT.SUCCESS;
    }
}
