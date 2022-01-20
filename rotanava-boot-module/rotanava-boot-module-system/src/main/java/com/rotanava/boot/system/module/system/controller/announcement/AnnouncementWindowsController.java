package com.rotanava.boot.system.module.system.controller.announcement;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysUserAnnouncementService;
import com.rotanava.boot.system.api.module.constant.AnnReadFlag;
import com.rotanava.boot.system.api.module.system.dto.GetAnnouncementWinItemPageDTO;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementItemVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @description: 消息窗口
 * @author: jintengzhou
 * @date: 2021-03-31 9:44
 */
@Api(tags = "消息窗口")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/announcementWindows")
public class AnnouncementWindowsController {

    @Autowired
    private SysUserAnnouncementService sysUserAnnouncementService;

    /**
     * 功能: 分页获取消息窗口消息
     * 作者: zjt
     * 日期: 2021/3/31 9:58
     * 版本: 1.0
     */
    @PostMapping("getAnnouncementNoticItemPage")
    @AutoLog(value = "分页获取消息窗口消息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<UserAnnouncementItemVO>> getAnnouncementWinItemPage(@RequestBody GetAnnouncementWinItemPageDTO baseDTO) {
        final IPage<UserAnnouncementItemVO> userAnnouncementItemPage =
                sysUserAnnouncementService.getUserAnnouncementItemPage(baseDTO, SysUtil.getCurrentReqUserId(), baseDTO.getAnnCategory(), AnnReadFlag.UNREAD.getType());
        return RetData.ok(userAnnouncementItemPage);
    }

    /**
     * 功能: 设置通知已读
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    @PostMapping("readAnnouncement")
    @AutoLog(value = "设置通知已读", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData readAnnouncement(@RequestBody Collection<Integer> sysAnnoIdList) {
        sysUserAnnouncementService.readAnnouncement(sysAnnoIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 获取通告详情
     * 作者: zjt
     * 日期: 2021/3/11 13:49
     * 版本: 1.0
     */
    @GetMapping("getUserAnnouncementInfo")
    @AutoLog(value = "获取通告详情", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<UserAnnouncementInfoVO> getUserAnnouncementInfo(@NotNull Integer sysUserAnnouncementId) {
        final UserAnnouncementInfoVO userAnnouncementInfo = sysUserAnnouncementService.getUserAnnouncementInfo(sysUserAnnouncementId);
        return RetData.ok(userAnnouncementInfo);
    }
}