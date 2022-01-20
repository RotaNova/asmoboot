package com.rotanava.boot.system.module.system.controller.announcement;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysAnnouncementService;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.AnnCategory;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.system.dto.AddAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAnnouncementItemPageDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementItemVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @description: 通知消息
 * @author: jintengzhou
 * @date: 2021-03-12 16:30
 */
@Api(tags = "通知消息")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/sysAnnouncementNotic")
public class SysAnnouncementNoticController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private SysAnnouncementService sysAnnouncementService;

    /**
     * 功能: 新增通告
     * 作者: zjt
     * 日期: 2021/3/11 10:17
     * 版本: 1.0
     */
    @PostMapping("addAnnouncement")
    @AutoLog(value = "新增通告", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData addAnnouncement(@RequestBody @Validated AddAnnouncementDTO announcementDTO) {
        announcementDTO.setAnnConfigId(1);
        sysAnnouncementService.addAnnouncement(announcementDTO, SysUtil.getCurrentReqUserId(), AnnCategory.NOTIFICATION_MESSAGE);
        return RetData.ok();
    }

    /**
     * 功能: 编辑通告
     * 作者: zjt
     * 日期: 2021/3/11 10:18
     * 版本: 1.0
     */
    @PostMapping("updateAnnouncement")
    @AutoLog(value = "编辑通告", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateAnnouncement(@RequestBody @Validated UpdateAnnouncementDTO updateAnnouncementDTO) {
        sysAnnouncementService.updateAnnouncement(updateAnnouncementDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 获取通告详情
     * 作者: zjt
     * 日期: 2021/3/11 13:49
     * 版本: 1.0
     */
    @GetMapping("getAnnouncementInfo")
    @AutoLog(value = "获取通告详情", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<AnnouncementInfoVO> getAnnouncementInfo(@NotNull Integer sysAnnoId) {
        final AnnouncementInfoVO announcementInfo = sysAnnouncementService.getAnnouncementInfo(sysAnnoId);
        return RetData.ok(announcementInfo);
    }

    /**
     * 功能: 分页获取通告
     * 作者: zjt
     * 日期: 2021/3/11 10:18
     * 版本: 1.0
     */
    @PostMapping("getAnnouncementItemPage")
    @AutoLog(value = "分页获取通告", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<AnnouncementItemVO>> getAnnouncementItemPage(@RequestBody GetAnnouncementItemPageDTO announcementItemPageDTO) {
        announcementItemPageDTO.setAnnCategory(AnnCategory.NOTIFICATION_MESSAGE.getType());
        final IPage<AnnouncementItemVO> announcementItemPage = sysAnnouncementService.getAnnouncementItemPage(announcementItemPageDTO);
        return RetData.ok(announcementItemPage);
    }

    /**
     * 功能: 获取用户分页列表
     * 作者: zjt
     * 日期: 2021/3/4 18:14
     * 版本: 1.0
     */
    @PostMapping("getListSysUser")
    @AutoLog(value = "获取用户分页列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getListSysUser(@RequestBody GetListSysUserDTO sysUserDTO) {
        sysUserDTO.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
        final IPage<SysUserItemVO> listSysUser = sysUserService.getListSysUser(sysUserDTO);
        return RetData.ok(listSysUser);
    }

    /**
     * 功能: 获取部门列表
     * 作者: zjt
     * 日期: 2021/3/11 16:31
     * 版本: 1.0
     */
    @GetMapping("getDeptDepartmentList")
    @AutoLog(value = "部门角色获取部门列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getDeptRoleDepartmentList() {
        final List<Tree<Integer>> departmentList = sysDepartmentService.getDepartmentList(null);
        return RetData.ok(departmentList);
    }

    /**
     * 功能: 发布通告
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    @PostMapping("publishAnnouncement")
    @AutoLog(value = "发布通告", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData publishAnnouncement(@RequestBody @NotEmpty(message = "sysAnnoIdList为空!") Collection<Integer> sysAnnoIdList) {
        sysAnnouncementService.publishAnnouncement(sysAnnoIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 撤销通告
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    @PostMapping("revokeAnnouncement")
    @AutoLog(value = "撤销通告", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData revokeAnnouncement(@RequestBody @NotEmpty(message = "sysAnnoIdList为空!") Collection<Integer> sysAnnoIdList) {
        sysAnnouncementService.revokeAnnouncement(sysAnnoIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 删除通告
     * 作者: zjt
     * 日期: 2021/3/11 10:19
     * 版本: 1.0
     */
    @PostMapping("deleteAnnouncement")
    @AutoLog(value = "删除通告", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteAnnouncement(@RequestBody @NotEmpty(message = "sysAnnoIdList为空!") Collection<Integer> sysAnnoIdList) {
        sysAnnouncementService.deleteAnnouncement(sysAnnoIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

}