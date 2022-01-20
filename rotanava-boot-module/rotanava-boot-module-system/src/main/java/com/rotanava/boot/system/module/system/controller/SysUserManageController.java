package com.rotanava.boot.system.module.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.SysRoleManagementService;
import com.rotanava.boot.system.api.SysUserExcelService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.system.bean.SysRoleLabel;
import com.rotanava.boot.system.api.module.system.bean.SysUserExcelExport;
import com.rotanava.boot.system.api.module.system.dto.AddSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.RestPasswordDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysUserDTO;
import com.rotanava.boot.system.api.module.system.vo.SysUserInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.util.SysUtil;
import com.rotanava.framework.util.excel.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @description: 用户管理
 * @author: jintengzhou
 * @date: 2021-03-06 15:40
 */
@Api(tags = "用户管理")
@Validated
@RestController
@RequestMapping("/v1/sysUserManage")
public class SysUserManageController {
    
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private SysDepartmentService sysDepartmentService;
    
    @Autowired
    private SysRoleManagementService sysRoleManagementService;
    
    @Autowired
    private SysUserExcelService sysUserExcelService;
    
    /**
     * 功能: 添加系统用户
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @PostMapping("addSysUser")
    @AutoLog(value = "添加系统用户", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @ApiOperation(value = "添加系统用户")
    public RetData addSysUser(@Validated AddSysUserDTO addSysUserDTO, @RequestParam(name = "photoFile", required = false) MultipartFile photoFile) throws Exception {
        byte[] bytes = null;
        if (photoFile != null) {
            bytes = photoFile.getBytes();
        }
        sysUserService.addSysUser(addSysUserDTO, bytes, SysUtil.getCurrentReqUserId(), true);
        return RetData.ok();
    }
    
    /**
     * 功能: 修改用户
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @PutMapping("updateSysUser")
    @AutoLog(value = "修改用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateSysUser(@Validated UpdateSysUserDTO updateSysUserDTO, @RequestParam(name = "photoFile", required = false) MultipartFile photoFile) throws IOException {
        byte[] bytes = null;
        if (photoFile != null) {
            bytes = photoFile.getBytes();
        }
        sysUserService.updateSysUser(updateSysUserDTO, bytes, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    
    /**
     * 功能: 导入用户
     * 作者: zjt
     * 日期: 2021/5/20 15:25
     * 版本: 1.0
     */
    @PostMapping("batchImportSysUser")
    @AutoLog(value = "导入用户", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData batchImportSysUser(@NotNull(message = "缺少文件") @RequestParam(name = "photoFile", required = false) MultipartFile photoFile) throws Exception {
        sysUserExcelService.batchImportSysUser(photoFile, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    /**
     * 功能: 导出用户
     * 作者: zjt
     * 日期: 2021/5/21 17:13
     * 版本: 1.0
     */
    @PostMapping("batchExportSysUser")
    @AutoLog(value = "导出用户", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public void batchExportSysUser(HttpServletResponse response) throws Exception {
        String fileName = "系统用户.xls";
        final String fileNameRes = URLEncoder.encode(fileName, "utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileNameRes + "\"");
        response.addHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("fileName", fileNameRes);
        response.addHeader("Access-Control-Expose-Headers", "fileName");
        final List<SysUserExcelExport> sysUserExcelList = sysUserExcelService.getSysUserExcelList();
        ExcelUtils.export(response.getOutputStream(), sysUserExcelList, SysUserExcelExport.class);
    }
    
    
    /**
     * 功能: 获取用户导入模板链接
     * 作者: zjt
     * 日期: 2021/5/21 17:13
     * 版本: 1.0
     */
    @GetMapping("getImportExcelFile")
    @AutoLog(value = "获取用户导入模板链接", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<String> getImportExcelFile(HttpServletResponse response) throws Exception {
        return RetData.ok(sysUserExcelService.getImportExcelFile());
    }
    
    /**
     * 功能: 获取用户信息
     * 作者: zjt
     * 日期: 2021/3/5 10:13
     * 版本: 1.0
     */
    @GetMapping("getSysUserInfo")
    @AutoLog(value = "获取用户信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<SysUserInfoVO> getSysUserInfo(@NotNull Integer sysUserId, Integer deptId) {
        final SysUserInfoVO sysUserInfo = sysUserService.getSysUserInfo(sysUserId, deptId);
        return RetData.ok(sysUserInfo);
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
     * 功能: 删除用户
     * 作者: zjt
     * 日期: 2021/3/4 17:57
     * 版本: 1.0
     */
    @PostMapping("deleteSysUser")
    @AutoLog(value = "删除用户", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteSysUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.deleteSysUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    /**
     * 功能: 彻底删除用户
     * 作者: zjt
     * 日期: 2021/3/24 14:17
     * 版本: 1.0
     */
    @PostMapping("thoroughDeleteSysUser")
    @AutoLog(value = "彻底删除用户", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData thoroughDeleteSysUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.thoroughDeleteSysUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    /**
     * 功能: 还原用户
     * 作者: zjt
     * 日期: 2021/3/24 14:18
     * 版本: 1.0
     */
    @PostMapping("restoreUser")
    @AutoLog(value = "还原用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData restoreUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.restoreUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    /**
     * 功能: 获取回收站用户分页列表
     * 作者: zjt
     * 日期: 2021/3/4 18:14
     * 版本: 1.0
     */
    @PostMapping("getCycleBinListSysUser")
    @AutoLog(value = "获取回收站用户分页列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<IPage<SysUserItemVO>> getCycleBinListSysUser(@RequestBody GetListSysUserDTO sysUserDTO) {
        sysUserDTO.setUserDeleteStatus(UserDeleteStatus.DELETED.getStatus());
        final IPage<SysUserItemVO> listSysUser = sysUserService.getListSysUser(sysUserDTO);
        return RetData.ok(listSysUser);
    }
    
    
    /**
     * 功能: 冻结用户
     * 作者: zjt
     * 日期: 2021/3/4 17:51
     * 版本: 1.0
     */
    @PutMapping("freezeSysUser")
    @AutoLog(value = "冻结用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData freezeSysUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.freezeSysUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    /**
     * 功能: 解冻用户
     * 作者: zjt
     * 日期: 2021/3/4 17:51
     * 版本: 1.0
     */
    @PutMapping("unfreezeSysUser")
    @AutoLog(value = "解冻用户", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData unfreezeSysUser(@RequestBody List<Integer> sysUserIdList) {
        sysUserService.unfreezeSysUser(sysUserIdList, SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    /**
     * 功能: 用户密码重置
     * 作者: zjt
     * 日期: 2021/3/4 18:19
     * 版本: 1.0
     */
    @PutMapping("restPassword")
    @AutoLog(value = "用户密码重置", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData restPassword(@Validated @RequestBody RestPasswordDTO restPasswordDTO) {
        sysUserService.restPassword(restPasswordDTO.getSysUserId(), restPasswordDTO.getPassword(), SysUtil.getCurrentReqUserId());
        return RetData.ok();
    }
    
    /**
     * 功能: 获取用户所属部门列表
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @GetMapping("getDepartmentList")
    @AutoLog(value = "获取部门列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<Tree<Integer>>> getDepartmentList() {
        final List<Tree<Integer>> departmentList = sysDepartmentService.getDepartmentList(null);
        return RetData.ok(departmentList);
    }
    
    /**
     * 功能: 获取全部用户角色
     * 作者: zjt
     * 日期: 2021/3/6 15:14
     * 版本: 1.0
     */
    @GetMapping("getSysRoleList")
    @AutoLog(value = "获取全部用户角色", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<SysRoleLabel>> getSysRoleList() {
        final List<SysRoleLabel> sysRoleList = sysRoleManagementService.getSysRoleList();
        return RetData.ok(sysRoleList);
    }
    
    /**
     * 功能: 解绑邮箱
     * 作者: zjt
     * 日期: 2021/3/5 10:13
     * 版本: 1.0
     */
    @GetMapping("unbindEmail")
    @AutoLog(value = "解绑邮箱", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData unbindEmail(int sysUserId) {
        sysUserService.unbindEmail(sysUserId);
        return RetData.ok();
    }
    
    /**
     * 功能: 解绑手机号
     * 作者: zjt
     * 日期: 2021/3/5 10:13
     * 版本: 1.0
     */
    @GetMapping("unbindPhone")
    @AutoLog(value = "解绑手机号", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData unbindPhone(int sysUserId) {
        sysUserService.unbindPhone(sysUserId);
        return RetData.ok();
    }
    
}