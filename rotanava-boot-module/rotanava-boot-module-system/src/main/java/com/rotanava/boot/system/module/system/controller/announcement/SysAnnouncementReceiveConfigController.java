package com.rotanava.boot.system.module.system.controller.announcement;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysAnnouncementConfigService;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.system.dto.AddAnnouncementConfigDTO;
import com.rotanava.boot.system.api.module.system.dto.EditAnnouncementConfigDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveAnnouncementConfigDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveAnnouncementReceiveConfigDTO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementConfigInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementConfigVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementReceiveConfigVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseDTO;
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
import java.util.List;
import java.util.Set;

/**
 * @description: 消息接收配置
 * @author: jintengzhou
 * @date: 2021-04-02 10:27
 */
@Api(tags = "消息接收配置")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/sysAnnouncementReceiveConfig")
public class SysAnnouncementReceiveConfigController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private SysAnnouncementConfigService sysAnnouncementConfigService;

    /**
     * 功能: 获取消息通知配置
     * 作者: zjt
     * 日期: 2021/4/1 16:55
     * 版本: 1.0
     */
    @PostMapping("getAnnouncementConfigList")
    @AutoLog(value = "获取消息通知配置", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<AnnouncementConfigVO>> getAnnouncementConfigList(@RequestBody BaseDTO baseDTO) {
        final List<AnnouncementConfigVO> announcementConfigList = sysAnnouncementConfigService.getAnnouncementConfigList(baseDTO);
        return RetData.ok(announcementConfigList);
    }

    /**
     * 功能: 保存消息通知配置
     * 作者: zjt
     * 日期: 2021/4/1 17:30
     * 版本: 1.0
     */
    @PostMapping("saveAnnouncementConfig")
    @AutoLog(value = "保存消息通知配置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData saveAnnouncementConfig(@RequestBody List<SaveAnnouncementConfigDTO> setAnnouncementConfigDTOList) {
        sysAnnouncementConfigService.saveAnnouncementConfig(setAnnouncementConfigDTOList);
        return RetData.ok();
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
     * 功能: 获取消息接收配置
     * 作者: zjt
     * 日期: 2021/4/1 18:06
     * 版本: 1.0
     */
    @PostMapping("getAnnouncementReceiveConfigList")
    @AutoLog(value = "获取消息接收配置", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<AnnouncementReceiveConfigVO>> getAnnouncementReceiveConfigList(@RequestBody BaseDTO baseDTO) {
        final List<AnnouncementReceiveConfigVO> announcementReceiveConfigList = sysAnnouncementConfigService.getAnnouncementReceiveConfigList(baseDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok(announcementReceiveConfigList);
    }

    /**
     * 功能: 保存消息接收配置
     * 作者: zjt
     * 日期: 2021/4/1 18:07
     * 版本: 1.0
     */
    @PostMapping("saveAnnouncementReceiveConfig")
    @AutoLog(value = "保存消息接收配置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData saveAnnouncementReceiveConfig(@RequestBody List<SaveAnnouncementReceiveConfigDTO> saveAnnouncementReceiveConfigDTOList) {
        sysAnnouncementConfigService.saveAnnouncementReceiveConfig(saveAnnouncementReceiveConfigDTOList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 恢复默认
     * 作者: zjt
     * 日期: 2021/4/2 15:32
     * 版本: 1.0
     */
    @PostMapping("resetReceiveConfig")
    @AutoLog(value = "恢复默认", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData resetReceiveConfig() {
        sysAnnouncementConfigService.resetReceiveConfig(SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    /**
     * 功能: 添加通知配置
     * 作者: zjt
     * 日期: 2021/4/2 15:32
     * 版本: 1.0
     */
    @PostMapping("addAnnouncementConfig")
    @AutoLog(value = "添加通知配置", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData addAnnouncementConfig(@RequestBody AddAnnouncementConfigDTO addAnnouncementConfigDTO) {
        sysAnnouncementConfigService.addAnnouncementConfig(addAnnouncementConfigDTO);
        return RetData.ok();
    }

    /**
     * 功能: 编辑消息通知配置
     * 作者: zjt
     * 日期: 2021/4/2 15:32
     * 版本: 1.0
     */
    @PostMapping("editAnnouncementConfig")
    @AutoLog(value = "编辑消息通知配置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData editAnnouncementConfig(@RequestBody EditAnnouncementConfigDTO editAnnouncementConfig) {
        sysAnnouncementConfigService.editAnnouncementConfig(editAnnouncementConfig);
        return RetData.ok();
    }

    /**
     * 功能: 批量删除消息通知配置
     * 作者: zjt
     * 日期: 2021/4/2 15:32
     * 版本: 1.0
     */
    @PostMapping("deleteAnnouncementConfig")
    @AutoLog(value = "批量删除消息通知配置", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteAnnouncementConfig(@RequestBody Set<Integer> sysAnnConfigIdList) {
        sysAnnouncementConfigService.deleteAnnouncementConfig(sysAnnConfigIdList);
        return RetData.ok();
    }

    /**
     * 功能: 获取通告配置详情
     * 作者: zjt
     * 日期: 2021/4/2 15:32
     * 版本: 1.0
     */
    @RequestMapping("getAnnouncementConfigInfo")
    @AutoLog(value = "获取通告配置详情", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<AnnouncementConfigInfoVO> getAnnouncementConfigInfoVO(@NotNull Integer sysAnnConfigId) {
        final AnnouncementConfigInfoVO announcementConfigInfoVO = sysAnnouncementConfigService.getAnnouncementConfigInfoVO(sysAnnConfigId);
        return RetData.ok(announcementConfigInfoVO);
    }

}