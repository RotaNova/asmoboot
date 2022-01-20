package com.rotanava.boot.system.api;

import cn.hutool.core.lang.tree.Tree;
import com.rotanava.boot.system.api.module.system.dto.AddAnnouncementConfigDTO;
import com.rotanava.boot.system.api.module.system.dto.EditAnnouncementConfigDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveAnnouncementConfigDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveAnnouncementReceiveConfigDTO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementConfigInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementConfigVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementReceiveConfigVO;
import com.rotanava.framework.model.BaseDTO;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @description: 系统通知配置相关
 * @author: jintengzhou
 * @date: 2021-04-01 16:54
 */
public interface SysAnnouncementConfigService {
    /**
     * 功能: 获取消息通知配置
     * 作者: zjt
     * 日期: 2021/4/1 16:55
     * 版本: 1.0
     */
    List<AnnouncementConfigVO> getAnnouncementConfigList(BaseDTO baseDTO);

    /**
     * 功能: 获取通知配置树形列表
     * 作者: zjt
     * 日期: 2021/7/30 11:06
     * 版本: 1.0
     *
     * @return
     */
    List<Tree<String>> getAnnouncementTree();

    /**
     * 功能: 保存消息通知配置
     * 作者: zjt
     * 日期: 2021/4/1 17:30
     * 版本: 1.0
     */
    void saveAnnouncementConfig(List<SaveAnnouncementConfigDTO> setAnnouncementConfigDTOList);

    /**
     * 功能: 获取消息接收配置
     * 作者: zjt
     * 日期: 2021/4/1 18:06
     * 版本: 1.0
     */
    List<AnnouncementReceiveConfigVO> getAnnouncementReceiveConfigList(BaseDTO baseDTO, int userId);

    /**
     * 功能: 保存消息接收配置
     * 作者: zjt
     * 日期: 2021/4/1 18:07
     * 版本: 1.0
     */
    void saveAnnouncementReceiveConfig(List<SaveAnnouncementReceiveConfigDTO> saveAnnouncementReceiveConfigDTOList, int userId);

    /**
     * 功能: 恢复默认
     * 作者: zjt
     * 日期: 2021/4/2 15:33
     * 版本: 1.0
     */
    void resetReceiveConfig(Integer currentReqUserId);

    /**
     * 功能: 批量删除消息通知配置
     * 作者: zjt
     * 日期: 2021/7/7 17:59
     * 版本: 1.0
     */
    void deleteAnnouncementConfig(Set<Integer> sysAnnConfigIdList);

    /**
     * 功能: 编辑消息通知配置
     * 作者: zjt
     * 日期: 2021/7/7 18:01
     * 版本: 1.0
     */
    void editAnnouncementConfig(EditAnnouncementConfigDTO editAnnouncementConfigDTO);

    /**
     * 功能: 添加通知配置
     * 作者: zjt
     * 日期: 2021/7/8 10:08
     * 版本: 1.0
     */
    void addAnnouncementConfig(AddAnnouncementConfigDTO addAnnouncementConfigDTO);


    /**
     * 功能: 获取配置详情
     * 作者: zjt
     * 日期: 2021/8/5 15:40
     * 版本: 1.0
     */
    AnnouncementConfigInfoVO getAnnouncementConfigInfoVO(@NotNull Integer sysAnnConfigId);
}
