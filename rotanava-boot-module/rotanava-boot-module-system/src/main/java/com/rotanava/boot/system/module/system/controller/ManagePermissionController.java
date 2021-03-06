package com.rotanava.boot.system.module.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.ManagePermissionService;
import com.rotanava.boot.system.api.SysPageModuleTypeService;
import com.rotanava.boot.system.api.SysSearchConfigService;
import com.rotanava.boot.system.api.module.system.dto.AddSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.AddSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.DeleteSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.DeleteSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.vo.SysApiPermissionVO;
import com.rotanava.boot.system.api.module.system.vo.SysPageModuleTypeVO;
import com.rotanava.boot.system.api.module.system.vo.SysPagePermissionVO;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.model.SearchDTO;
import com.rotanava.framework.model.bo.SysSearchConfig;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.PageUtils;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * ?????????????????????
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Api(tags = "????????????")
@RestController
@RequestMapping("/v1/managePermission")
public class ManagePermissionController {

    @Autowired
    private ManagePermissionService managePermissionService;

    @Autowired
    private SysSearchConfigService sysSearchConfigService;

    @Autowired
    private SysPageModuleTypeService sysPageModuleTypeService;

    @AutoLog(value = "????????????????????????", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/saveSysPagePermission")
    public RetData<Void> saveSysPagePermission(@RequestBody AddSysPagePermissionDTO addSysPagePermissionDTO) {
        managePermissionService.saveSysPagePermission(addSysPagePermissionDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    @AutoLog(value = "????????????????????????", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    @PostMapping("/deleteSysPagePermission")
    public RetData<Void> deleteSysPagePermission(@RequestBody DeleteSysPagePermissionDTO deleteSysPagePermissionDTO) {
        managePermissionService.deleteSysPagePermission(deleteSysPagePermissionDTO);
        return RetData.ok();
    }

    @AutoLog(value = "????????????????????????", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateSysPagePermission")
    public RetData<Void> updateSysPagePermission(@RequestBody UpdateSysPagePermissionDTO updateSysPagePermissionDTO) {

        managePermissionService.updateSysPagePermission(updateSysPagePermissionDTO);
        return RetData.ok();
    }

    @AutoLog("????????????????????????")
    @AdviceResponseBody
    @GetMapping("/getSysPagePermission")
    public RetData<BaseVO<SysPagePermissionVO>> getSysPagePermission(Integer sysPageId) {

        return RetData.ok(managePermissionService.getSysPagePermission(sysPageId));
    }

    @AutoLog("??????????????????????????????")
    @AdviceResponseBody
    @PostMapping("/listSysPagePermissions")
    public RetData<BaseVO<SysPagePermissionVO>> listSysPagePermissions(@RequestBody SearchDTO searchDTO) {
        return RetData.ok(managePermissionService.listSysPagePermissions(searchDTO));
    }


    @AutoLog(value = "????????????????????????", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/saveSysApiPermission")
    public RetData<Void> saveSysApiPermission(@RequestBody AddSysApiPermissionDTO addSysApiPermissionDTO) {
        managePermissionService.saveSysApiPermission(addSysApiPermissionDTO, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }

    @AutoLog(value = "????????????????????????", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    @PostMapping("/deleteSysApiPermission")
    public RetData<Void> deleteSysApiPermission(@RequestBody DeleteSysApiPermissionDTO deleteSysApiPermissionDTO) {

        managePermissionService.deleteSysApiPermission(deleteSysApiPermissionDTO);
        return RetData.ok();
    }

    @AutoLog(value = "????????????????????????", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateSysApiPermission")
    public RetData<Void> updateSysApiPermission(@RequestBody UpdateSysApiPermissionDTO updateSysApiPermissionDTO) {

        managePermissionService.updateSysApiPermission(updateSysApiPermissionDTO);
        return RetData.ok();
    }

    @AutoLog("????????????????????????")
    @AdviceResponseBody
    @GetMapping("/getSysApiPermission")
    public RetData<BaseVO<SysApiPermissionVO>> getSysApiPermission(Integer sysApiId) {

        return RetData.ok(managePermissionService.getSysApiPermission(sysApiId));
    }

    @AutoLog("??????????????????????????????")
    @AdviceResponseBody
    @PostMapping("/listSysApiPermissions")
    public RetData<BaseVO<SysApiPermissionVO>> listSysApiPermissions(@RequestBody BaseDTO baseDTO) {
        return RetData.ok(managePermissionService.listSysApiPermissions(baseDTO));
    }

    @AutoLog("????????????:??????????????????????????????")
    @AdviceResponseBody
    @PostMapping("/listSysPagePermissionsUseApi")
    public RetData<BaseVO<SysPagePermissionVO>> listSysPagePermissionsUseApi(@RequestParam("sysPageModuleTypeId") Integer sysPageModuleTypeId) {
        return RetData.ok(managePermissionService.listSysPagePermissionsUseApi( sysPageModuleTypeId));
    }

    @AutoLog("?????????????????????????????????")
    @AdviceResponseBody
    @PostMapping("/listSysPagePermissionsNavigationBar")
    public RetData<List<Tree<Integer>>> listSysPagePermissionsNavigationBar(@RequestParam("sysPageModuleTypeId") Integer sysPageModuleTypeId) {
        return RetData.ok(managePermissionService.listSysPagePermissionsNavigationBar(sysPageModuleTypeId));
    }


    @AutoLog(value = "??????????????????", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/addSearchConfig")
    public RetData addSearchConfig(@RequestBody SysSearchConfig sysSearchConfig) {

        sysSearchConfigService.save(sysSearchConfig);
        return BuildUtil.buildSuccessResult();
    }


    @AutoLog(value = "??????????????????", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/updateSearchConfig")
    public RetData updateSearchConfig(@RequestBody SysSearchConfig sysSearchConfig) {
        sysSearchConfigService.updateById(sysSearchConfig);
        return BuildUtil.buildSuccessResult();
    }


    @AutoLog(value = "??????????????????", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    @GetMapping("/deleteSearchConfig")
    public RetData deleteSearchConfig(Integer id) {
        sysSearchConfigService.removeById(id);
        return BuildUtil.buildSuccessResult();
    }

    @AutoLog(value = "????????????????????????", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getSearchConfigList")
    public RetData<BaseVO> getSearchConfigList(Integer pageId) {
        QueryWrapper<SysSearchConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("page_id", pageId);
        IPage<SysSearchConfig> page = sysSearchConfigService.page(PageUtils.startPage(), queryWrapper);
        return new RetData<>(BuildUtil.buildListVO(page.getTotal(), page.getRecords()));
    }

    @AutoLog(value = "??????????????????????????????", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/listSysPageModuleType")
    public RetData<List<SysPageModuleTypeVO>> listSysPageModuleType() {

        return RetData.ok(sysPageModuleTypeService.getSysPageModuleTypeVO());
    }

    @AutoLog("??????????????????json")
    @AdviceResponseBody
    @GetMapping("/getPageJson")
    public RetData<String> getPageJson(Integer sysPageId) {

        return RetData.ok(managePermissionService.getPageJson(sysPageId));
    }

    @AdviceResponseBody
    @PostMapping("/importExcel")
    @AutoLog(value = "??????Excel", operateType = OperateTypeEnum.SELECT)
    public RetData<Void> importExcel(@RequestParam(value = "file") MultipartFile file) throws Exception {
        managePermissionService.importExcel( file);
        return RetData.ok();
    }

    @AdviceResponseBody
    @PostMapping("/exportExcel")
    @AutoLog(value = "??????Excel", operateType = OperateTypeEnum.SELECT)
    public RetData<Void> exportExcel() {
        managePermissionService.exportExcel();
        return RetData.ok();
    }

    @AdviceResponseBody
    @GetMapping("/getMould")
    @AutoLog(value = "????????????", operateType = OperateTypeEnum.SELECT)
    public RetData<String> getMould() {

        return RetData.ok(managePermissionService.getMould());
    }

    @AdviceResponseBody
    @PostMapping("/batchExportExcel")
    @AutoLog(value = "????????????Excel", operateType = OperateTypeEnum.SELECT)
    public RetData<Void> batchExportExcel(@RequestBody List<Integer> sysPageIds) {
        managePermissionService.batchExportExcel(sysPageIds);
        return RetData.ok();
    }

}
