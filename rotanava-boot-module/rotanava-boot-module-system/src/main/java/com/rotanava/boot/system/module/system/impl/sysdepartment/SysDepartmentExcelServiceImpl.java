package com.rotanava.boot.system.module.system.impl.sysdepartment;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysDepartmentExcelService;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.DeptType;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentExcel;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentExport;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.bo.SysUserDepartment;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.AddDepartmentDTO;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.framework.code.BaseException;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.ali.SortUtils;
import com.rotanava.framework.util.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description: 部门导入导出
 * @author: jintengzhou
 * @date: 2021-05-21 17:31
 */
@Slf4j
@Service
public class SysDepartmentExcelServiceImpl implements SysDepartmentExcelService {

    @Autowired
    private SysDepartmentExcelService sysDepartmentExcelService;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private SysAnnouncementSenderService sysAnnouncementSenderService;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<SysDepartmentExport> exportSysDepartment() {
        List<SysDepartmentExport> sysDepartmentExportList = Lists.newArrayList();
        final List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectList(null).stream().sorted(Comparator.comparing(SysDepartment::getParentDeptId)).collect(Collectors.toList());
        final Map<Integer, String> sysDepartmentMap = sysDepartmentList.stream().collect(Collectors.toMap(SysDepartment::getId, SysDepartment::getDeptName));
        final Map<Integer, List<Integer>> sysUserDepartmentMap = sysUserDepartmentMapper.selectList(null).stream().collect(Collectors.groupingBy(SysUserDepartment::getSysDepartmentId, Collectors.mapping(SysUserDepartment::getSysUserId, Collectors.toList())));
        final Map<Integer, String> sysUserMap = sysUserMapper.selectList(null).stream().collect(Collectors.toMap(SysUser::getId, SysUser::getUserName));


        for (SysDepartment sysDepartment : sysDepartmentList) {
            final SysDepartmentExport sysDepartmentExport = new SysDepartmentExport();
            sysDepartmentExport.setDeptAddress(sysDepartment.getDeptAddress());
            sysDepartmentExport.setDeptCode(sysDepartment.getDeptCode());
            sysDepartmentExport.setDeptDescription(sysDepartment.getDeptDescription());
            sysDepartmentExport.setDeptFax(sysDepartment.getDeptFax());
            final List<Integer> sysUserIdList = sysUserDepartmentMap.getOrDefault(sysDepartment.getId(), Lists.newArrayList());
            sysDepartmentExport.setDeptManager(sysUserIdList.stream().map(sysUserMap::get).collect(Collectors.joining(";")));
            sysDepartmentExport.setDeptManagerPhone(sysDepartment.getDeptManagerPhone());
            sysDepartmentExport.setDeptName(sysDepartment.getDeptName());
            sysDepartmentExport.setDeptOrder(Convert.toStr(sysDepartment.getDeptOrder()));
            sysDepartmentExport.setDeptValidTime(Optional.ofNullable(sysDepartment.getDeptValidTime()).map(time -> DateUtil.format(time, Date8Util.DATE_TIME)).orElse(null));
            sysDepartmentExport.setParentDeptName(sysDepartmentMap.get(sysDepartment.getParentDeptId()));
            sysDepartmentExport.setId(sysDepartment.getId());
            sysDepartmentExport.setPid(sysDepartment.getParentDeptId());
            sysDepartmentExportList.add(sysDepartmentExport);
        }

        return SortUtils.sortByParent(sysDepartmentExportList, 0, "id", "pid", "deptOrder");

    }

    @Override
    public void batchImportSysDepartment(MultipartFile file, int userId) throws Exception {
        final String originalFilename = file.getOriginalFilename();
        //错误返回内容
        final List<SysDepartmentExcel> errorSysDepartmentExcelList = Lists.newArrayList();
        List<SysDepartmentExcel> sysDepartmentExcelList;
        try {
            sysDepartmentExcelList = ExcelUtils.parse(file.getInputStream(), SysDepartmentExcel.class);
        } catch (Exception e) {
            log.error("excel文件解析错误", e);
            //文件解析错误
            throw new CommonException(ParamErrorCode.PARAM_ERROR_60);
        }

        for (SysDepartmentExcel sysDepartmentExcel : sysDepartmentExcelList) {
            sysDepartmentExcelService.parseSysDepartmentExcel(sysDepartmentExcel, errorSysDepartmentExcelList, userId);
        }

        if (errorSysDepartmentExcelList.size() > 0) {
            //导入出错
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExcelUtils.export(byteArrayOutputStream, errorSysDepartmentExcelList, SysDepartmentExcel.class);
            //富文本
            String objName = String.format("导入部门失败_%s", Date8Util.format(new Date(), "yyyy-MM-dd(HH-mm-ss)"));
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.ERROR_FACE_BUCKET, objName, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), ".xlsx");
            String objUrl = fileUploadUtil.getObjUrl(BucketNamePool.ERROR_FACE_BUCKET, uploadResultBean.getObjName());
            String richText = "<h1 style=\"font-size: 18px;\">批量导入部门失败" + errorSysDepartmentExcelList.size() + "条，下载表格查看详细信息。<a style=\"color: #3598db;\" href=\"" + objUrl + "\" download=\"" + objName + "\">点击下载</a></h1>";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "导入部门结果通知", String.format("批量导入失败%s条。", errorSysDepartmentExcelList.size()), richText, Lists.newArrayList(userId), AnnPriorityType.MIDDLE);
        } else {
            //导入成功
            String msg = originalFilename + "导入成功" + sysDepartmentExcelList.size() + "条";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "导入部门结果通知", msg, msg, Lists.newArrayList(userId), AnnPriorityType.MIDDLE);
        }
    }

    @Override
    public String getImportExcelFile() {
        return fileUploadUtil.getObjUrl(BucketNamePool.IMPORT_EXCEL_FILE_BUCKET, "部门导入模板.xlsx", 6);
    }

    /**
     * 功能: 解析部门导入
     * 作者: zjt
     * 日期: 2021/5/24 14:55
     * 版本: 1.0
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public void parseSysDepartmentExcel(SysDepartmentExcel sysDepartmentExcel, List<SysDepartmentExcel> errorSysDepartmentExcelList, int userId) {

        final AddDepartmentDTO addDepartmentDTO = new AddDepartmentDTO();

        if (StringUtils.isEmpty(sysDepartmentExcel.getDeptName())) {
            sysDepartmentExcel.setFailReason("部门名称为空");
            errorSysDepartmentExcelList.add(sysDepartmentExcel);
            return;
        } else {
            addDepartmentDTO.setDeptName(sysDepartmentExcel.getDeptName());
        }

        if (StringUtils.isEmpty(sysDepartmentExcel.getDeptCode())) {
            sysDepartmentExcel.setFailReason("部门编号为空");
            errorSysDepartmentExcelList.add(sysDepartmentExcel);
            return;
        } else {
            addDepartmentDTO.setDeptCode(sysDepartmentExcel.getDeptCode());
        }

        addDepartmentDTO.setDeptAddress(sysDepartmentExcel.getDeptAddress());
        addDepartmentDTO.setDeptDescription(sysDepartmentExcel.getDeptDescription());
        addDepartmentDTO.setDeptFax(sysDepartmentExcel.getDeptFax());
        addDepartmentDTO.setDeptManagerPhone(sysDepartmentExcel.getDeptManagerPhone());
        addDepartmentDTO.setDeptOrder(Convert.toInt(sysDepartmentExcel.getDeptOrder()));

        final String deptValidTime = sysDepartmentExcel.getDeptValidTime();
        if (StringUtils.isEmpty(deptValidTime)) {
            addDepartmentDTO.setDeptValidTime(4102329600000L);
        } else {
            try {
                addDepartmentDTO.setDeptValidTime(DateUtil.parse(deptValidTime, Date8Util.DATE_TIME).getTime());
            } catch (Exception e) {
                sysDepartmentExcel.setFailReason(String.format("%s 有效期格式有误,请修改", deptValidTime));
                errorSysDepartmentExcelList.add(sysDepartmentExcel);
                return;
            }
        }

        final String parentDeptName = sysDepartmentExcel.getParentDeptName();
        if (StringUtils.isEmpty(parentDeptName)) {
            addDepartmentDTO.setDeptType(DeptType.FIRST_LEVEL_DEPARTMENT.getType());
        } else {
            final Integer parentId = sysDepartmentMapper.findIdByDeptName(parentDeptName);
            if (parentId == null) {
                sysDepartmentExcel.setFailReason(String.format("%s 不存在该上级部门,请修改", parentDeptName));
                errorSysDepartmentExcelList.add(sysDepartmentExcel);
                return;
            } else {
                addDepartmentDTO.setParentDeptId(parentId);
                addDepartmentDTO.setDeptType(DeptType.SUB_DEPARTMENT.getType());
            }
        }

        final String deptManager = sysDepartmentExcel.getDeptManager();
        if (StringUtils.isNotEmpty(deptManager)) {
            final List<Integer> sysUserIdList = Lists.newArrayList();
            for (String deptManagerName : deptManager.split(";")) {
                final List<Integer> userIdList = sysUserMapper.findIdByUserName(deptManagerName);
                if (userIdList.isEmpty()) {
                    sysDepartmentExcel.setFailReason(String.format("%s 不存在该部门负责人,请修改", deptManagerName));
                    errorSysDepartmentExcelList.add(sysDepartmentExcel);
                    return;
                } else {
                    sysUserIdList.add(userIdList.get(0));
                }
            }
            addDepartmentDTO.setSysUserIdList(sysUserIdList);
        }


        try {
            sysDepartmentService.addDepartment(addDepartmentDTO, userId);
        } catch (Exception e) {
            String failReason = "";
            if (e instanceof BaseException) {
                final BaseException baseException = (BaseException) e;
                failReason = baseException.getErrorCode().getMsg();
            } else {
                failReason = String.format("%s 添加失败", e.getMessage());
                log.error("部门导入添加失败", e);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            sysDepartmentExcel.setFailReason(failReason);
            errorSysDepartmentExcelList.add(sysDepartmentExcel);
        }
    }


}