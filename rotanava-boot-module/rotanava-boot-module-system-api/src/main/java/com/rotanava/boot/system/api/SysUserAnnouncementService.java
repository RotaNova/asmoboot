package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementItemVO;
import com.rotanava.framework.model.BaseDTO;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

/**
 * @description: 用户系统公告
 * @author: jintengzhou
 * @date: 2021-03-11 10:17
 */
@Validated
public interface SysUserAnnouncementService {

    /**
     * 功能: 获取通告详情
     * 作者: zjt
     * 日期: 2021/3/11 13:49
     * 版本: 1.0
     *
     * @param sysUserAnnouncementId
     * @return
     */
    @Nullable
    UserAnnouncementInfoVO getUserAnnouncementInfo(int sysUserAnnouncementId);

    /**
     * 功能: 分页获取用户通告
     * 作者: zjt
     * 日期: 2021/3/11 10:18
     * 版本: 1.0
     *
     * @return
     */
    IPage<UserAnnouncementItemVO> getUserAnnouncementItemPage(BaseDTO baseDTO, int userId, @Nullable Integer annCategory, @Nullable Integer annReadFlag);

    /**
     * 功能: 设置通知已读
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    void readAnnouncement(Collection<Integer> sysAnnoIdList, int userId);

    /**
     * 功能: 设置通知全部已读
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    void readAllAnnouncement(int userId);
}
