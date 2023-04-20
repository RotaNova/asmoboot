package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.constant.AnnCategory;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncement;
import com.rotanava.boot.system.api.module.system.dto.AddAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAnnouncementItemPageDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementItemVO;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

/**
 * @description: 系统公告
 * @author: jintengzhou
 * @date: 2021-03-11 10:17
 */
@Validated
public interface SysAnnouncementService {

    /**
     * 功能: 根据id获取通告消息
     * 版本: 1.0
     * @return
     */
    SysAnnouncement getSysAnnouncement(Integer id);

    /**
     * 功能: 新增通告
     * 作者: zjt
     * 日期: 2021/3/11 10:17
     * 版本: 1.0
     * @return
     */
    Integer addAnnouncement(@Validated AddAnnouncementDTO announcementDTO, int userId, AnnCategory annCategory);

    /**
     * 功能: 编辑通告
     * 作者: zjt
     * 日期: 2021/3/11 10:18
     * 版本: 1.0
     */
    void updateAnnouncement(@Validated UpdateAnnouncementDTO updateAnnouncementDTO, int userId);

    /**
     * 功能: 获取通告详情
     * 作者: zjt
     * 日期: 2021/3/11 13:49
     * 版本: 1.0
     */
    @Nullable
    AnnouncementInfoVO getAnnouncementInfo(int sysAnnoId);

    /**
     * 功能: 分页获取通告
     * 作者: zjt
     * 日期: 2021/3/11 10:18
     * 版本: 1.0
     */
    IPage<AnnouncementItemVO> getAnnouncementItemPage(GetAnnouncementItemPageDTO announcementItemPageDTO);

    /**
     * 功能: 发布通告
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    void publishAnnouncement(Collection<Integer> sysAnnoIdList, int userId);

    /**
     * 功能: 撤销通告
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    void revokeAnnouncement(Collection<Integer> sysAnnoIdList, int userId);

    /**
     * 功能: 删除通告
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    void deleteAnnouncement(Collection<Integer> sysAnnoIdList, int userId);
}
