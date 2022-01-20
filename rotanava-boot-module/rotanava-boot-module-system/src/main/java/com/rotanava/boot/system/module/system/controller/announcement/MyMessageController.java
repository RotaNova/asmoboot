package com.rotanava.boot.system.module.system.controller.announcement;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysUserAnnouncementService;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementItemVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseDTO;
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
 * @description: 我的消息
 * @author: jintengzhou
 * @date: 2021-03-16 10:12
 */
@Api(tags = "我的消息")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/myMessage")
public class MyMessageController {

    @Autowired
    private SysUserAnnouncementService sysUserAnnouncementService;

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

    /**
     * 功能: 分页获取用户通告
     * 作者: zjt
     * 日期: 2021/3/11 10:18
     * 版本: 1.0
     *
     * @return
     */
    @PostMapping("getUserAnnouncementItemPage")
    @AutoLog(value = "分页获取用户通告", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<UserAnnouncementItemVO>> getUserAnnouncementItemPage(@RequestBody BaseDTO baseDTO) {
        final IPage<UserAnnouncementItemVO> announcementItemPage = sysUserAnnouncementService.getUserAnnouncementItemPage(baseDTO, SysUtil.getCurrentReqUserId(), null, null);
        return RetData.ok(announcementItemPage);
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
     * 功能: 设置通知全部已读
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    @PostMapping("readAllAnnouncement")
    @AutoLog(value = "设置通知全部已读", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData readAllAnnouncement() {
        sysUserAnnouncementService.readAllAnnouncement(SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

}