package com.rotanava.boot.system.module.system.impl.sysuser;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysUserExcelService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.DeptManageStatus;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.constant.UserSex;
import com.rotanava.boot.system.api.module.constant.UserSysRole;
import com.rotanava.boot.system.api.module.system.bean.SysUserExcel;
import com.rotanava.boot.system.api.module.system.bean.SysUserExcelExport;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysRole;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.bo.SysUserDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysUserRole;
import com.rotanava.boot.system.api.module.system.dto.AddSysUserDTO;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysRoleMapper;
import com.rotanava.boot.system.module.dao.SysUserDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.boot.system.module.dao.SysUserRoleMapper;
import com.rotanava.framework.common.constant.enums.UserStatus;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-05-21 9:55
 */
@Slf4j
@Service
public class SysUserExcelServiceImpl implements SysUserExcelService {

    public static final String BIRTHDAY_PARTTER = "yyyy/MM/dd";
    public static final String VALIDTIME_PARTTER = "yyyy/MM/dd HH:mm:ss";

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysAnnouncementSenderService sysAnnouncementSenderService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserExcelService sysUserExcelService;

    /**
     * ??????: Excel????????????????????????
     * ??????: zjt
     * ??????: 2021/5/19 17:32
     * ??????: 1.0
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void batchImportSysUser(MultipartFile file, int userId) throws Exception {
        final String originalFilename = file.getOriginalFilename();
        //??????????????????
        final List<SysUserExcel> errorSysUserExcelList = Lists.newArrayList();
        List<SysUserExcel> sysUserExcelList;
        try {
            sysUserExcelList = ExcelUtils.parse(file.getInputStream(), SysUserExcel.class);
        } catch (Exception e) {
            log.error("excel??????????????????", e);
            //??????????????????
            throw new CommonException(ParamErrorCode.PARAM_ERROR_60);
        }

        for (SysUserExcel sysUserExcel : sysUserExcelList) {
            sysUserExcelService.parseSysUserExcel(sysUserExcel, errorSysUserExcelList, userId);
        }

        if (errorSysUserExcelList.size() > 0) {
            //????????????
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExcelUtils.export(byteArrayOutputStream, errorSysUserExcelList, SysUserExcel.class);
            //?????????
            String objName = String.format("??????????????????_%s.xlsx", Date8Util.format(new Date(), "yyyy-MM-dd(HH-mm-ss)"));
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.ERROR_FACE_BUCKET, objName, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), ".xlsx");
            String objUrl = fileUploadUtil.getObjUrl(BucketNamePool.ERROR_FACE_BUCKET, uploadResultBean.getObjName());
            String richText = "<h1 style=\"font-size: 18px;\">????????????????????????" + errorSysUserExcelList.size() + "???????????????????????????????????????<a style=\"color: #3598db;\" href=\"" + objUrl + "\" download=\"" + objName + "\">????????????</a></h1>";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "????????????????????????", String.format("??????????????????%s?????????????????????????????????????????????", errorSysUserExcelList.size()), richText, Lists.newArrayList(userId), AnnPriorityType.MIDDLE);
        } else {
            //????????????
            String msg = originalFilename + "????????????" + sysUserExcelList.size() + "???";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, msg, msg, msg, Lists.newArrayList(userId), AnnPriorityType.MIDDLE);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<SysUserExcelExport> getSysUserExcelList() {
        List<SysUserExcelExport> sysUserExcelList = Lists.newArrayList();

        final List<SysUser> sysUserList = sysUserMapper.selectList(null);
        final Map<Integer, String> sysDepartmentMap = sysDepartmentMapper.selectList(null).stream().collect(Collectors.toMap(SysDepartment::getId, SysDepartment::getDeptName));
        final Map<Integer, String> sysRoleMap = sysRoleMapper.selectList(null).stream().collect(Collectors.toMap(SysRole::getId, SysRole::getRoleName));
        final Map<Integer, List<SysUserDepartment>> sysUserDepartmentMap = sysUserDepartmentMapper.selectList(null).stream().collect(Collectors.groupingBy(SysUserDepartment::getSysUserId, Collectors.toList()));
        final Map<Integer, List<Integer>> sysUserRoleMap = sysUserRoleMapper.selectList(null).stream().collect(Collectors.groupingBy(SysUserRole::getSysUserId, Collectors.mapping(SysUserRole::getSysRoleId, Collectors.toList())));


        for (SysUser sysUser : sysUserList) {
            final SysUserExcelExport sysUserExcelExport = new SysUserExcelExport();

            final Integer sysUserId = sysUser.getId();

            final List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMap.getOrDefault(sysUserId, Lists.newArrayList());

            sysUserExcelExport.setResponsibleDeptList(getStrName(sysUserDepartmentList.stream()
                    .filter(sysDepartment -> sysDepartment.getDeptManage().equals(DeptManageStatus.ADMINISTRATOR.getStatus()))
                    .map(SysUserDepartment::getSysDepartmentId).collect(Collectors.toList()), sysDepartmentMap));
            sysUserExcelExport.setStatus(UserStatus.getDescByStatus(sysUser.getUserStatus()));
            sysUserExcelExport.setSysDepartmentIdList(getStrName(sysUserDepartmentList.stream().map(SysUserDepartment::getSysDepartmentId).collect(Collectors.toList()), sysDepartmentMap));
            sysUserExcelExport.setSysRoleIdList(getStrName(sysUserRoleMap.get(sysUserId), sysRoleMap));
            sysUserExcelExport.setUserAccountName(sysUser.getUserAccountName());
            sysUserExcelExport.setUserAddress(sysUser.getUserAddress());
            sysUserExcelExport.setUserCode(sysUser.getUserCode());
            sysUserExcelExport.setUserName(sysUser.getUserName());
            sysUserExcelExport.setUserOccupation(sysUser.getUserOccupation());
            sysUserExcelExport.setUserPhone(sysUser.getUserPhone());
            sysUserExcelExport.setUserBirthday(Optional.ofNullable(sysUser.getUserBirthday()).map(time -> DateUtil.format(time, BIRTHDAY_PARTTER)).orElse(null));
            sysUserExcelExport.setUserSafeEmail(sysUser.getUserSafeEmail());
            sysUserExcelExport.setUserSex(UserSex.getDescByStatus(sysUser.getUserSex()));
            sysUserExcelExport.setUserSysrole(UserSysRole.getDescByStatus(sysUser.getUserSysrole()));
            sysUserExcelExport.setUserValidTime(Optional.ofNullable(sysUser.getUserValidTime()).map(time -> DateUtil.format(time, VALIDTIME_PARTTER)).orElse(null));
            sysUserExcelList.add(sysUserExcelExport);
        }

        return sysUserExcelList;
    }

    @Override
    public String getImportExcelFile() {
        return fileUploadUtil.getObjUrl(BucketNamePool.IMPORT_EXCEL_FILE_BUCKET, "??????????????????.xlsx", 6);
    }


    /**
     * ??????: ??????
     * ??????: zjt
     * ??????: 2021/5/21 10:38
     * ??????: 1.0
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public void parseSysUserExcel(SysUserExcel sysUserExcel, List<SysUserExcel> errorSysUserExcelList, int userId) {
        final AddSysUserDTO addSysUserDTO = new AddSysUserDTO();

        if (StringUtils.isEmpty(sysUserExcel.getUserName())) {
            sysUserExcel.setFailReason("???????????????");
            errorSysUserExcelList.add(sysUserExcel);
            return;
        } else {
            addSysUserDTO.setUserName(sysUserExcel.getUserName());
        }

        if (StringUtils.isEmpty(sysUserExcel.getUserAccountName())) {
            sysUserExcel.setFailReason("??????????????????");
            errorSysUserExcelList.add(sysUserExcel);
            return;
        } else {
            addSysUserDTO.setUserAccountName(sysUserExcel.getUserAccountName());
        }

        final String userPassword = sysUserExcel.getUserPassword();
        if (StringUtils.isEmpty(userPassword)) {
            //?????????????????????
            addSysUserDTO.setUserPassword(sysUserExcel.getUserAccountName());
        } else {
            addSysUserDTO.setUserPassword(sysUserExcel.getUserPassword());
        }

        try {
            addSysUserDTO.setSysDepartmentIdList(getDeptIdList(sysUserExcel.getSysDepartmentIdList().split(";")));
        } catch (UnsupportedOperationException unsupportedOperationException) {
            sysUserExcel.setFailReason("??????????????????:" + unsupportedOperationException.getMessage());
            errorSysUserExcelList.add(sysUserExcel);
            return;
        }

        final String userSysrole = sysUserExcel.getUserSysrole();
        if ("?????????".equals(userSysrole)) {
            addSysUserDTO.setUserSysrole(UserSysRole.ADMINISTRATOR.getType());
        } else if ("??????".equals(userSysrole)) {
            addSysUserDTO.setUserSysrole(UserSysRole.MEMBER.getType());
        } else {
            sysUserExcel.setFailReason(String.format("%s ?????????????????????,?????????", userSysrole));
            errorSysUserExcelList.add(sysUserExcel);
            return;
        }

        try {
            addSysUserDTO.setResponsibleDeptList(getDeptIdList(sysUserExcel.getResponsibleDeptList().split(";")));
        } catch (UnsupportedOperationException unsupportedOperationException) {
            sysUserExcel.setFailReason("????????????:" + unsupportedOperationException.getMessage());
            errorSysUserExcelList.add(sysUserExcel);
            return;
        }

        if (StringUtils.isEmpty(sysUserExcel.getUserCode())) {
            sysUserExcel.setFailReason("????????????");
            errorSysUserExcelList.add(sysUserExcel);
            return;
        } else {
            addSysUserDTO.setUserCode(sysUserExcel.getUserCode());
        }

        final String userValidTime = sysUserExcel.getUserValidTime();
        if (!StringUtils.isEmpty(userValidTime)) {
            try {
                addSysUserDTO.setUserValidTime(DateUtil.parse(userValidTime, VALIDTIME_PARTTER).getTime());
            } catch (Exception e) {
                sysUserExcel.setFailReason(String.format("%s ?????????????????????,?????????", userValidTime));
                errorSysUserExcelList.add(sysUserExcel);
                return;
            }
        } else {
            addSysUserDTO.setUserValidTime(4102329600000L);
        }

        final String userSex = sysUserExcel.getUserSex();
        if ("???".equals(userSex)) {
            addSysUserDTO.setUserSex(0);
        } else if ("???".equals(userSex)) {
            addSysUserDTO.setUserSex(1);
        } else if (StringUtils.isEmpty(userSex)) {
            addSysUserDTO.setUserSex(2);
        }

        final List<Integer> sysRoleIdList = Lists.newArrayList();
        for (String sysRoleName : sysUserExcel.getSysRoleIdList().split(";")) {
            final List<Integer> roleNameList = sysRoleMapper.findIdByRoleName(sysRoleName);
            if (roleNameList.isEmpty()) {
                sysUserExcel.setFailReason(String.format("%s ??????????????????????????????,?????????", sysRoleName));
                errorSysUserExcelList.add(sysUserExcel);
                return;
            }
            sysRoleIdList.add(roleNameList.get(0));
        }
        addSysUserDTO.setSysRoleIdList(sysRoleIdList);

        addSysUserDTO.setUserOccupation(sysUserExcel.getUserOccupation());
        addSysUserDTO.setUserPhone(sysUserExcel.getUserPhone());
        addSysUserDTO.setUserEmail(sysUserExcel.getUserSafeEmail());
        addSysUserDTO.setUserAddress(sysUserExcel.getUserAddress());

        final String userBirthday = sysUserExcel.getUserBirthday();
        if (!StringUtils.isEmpty(userBirthday)) {
            try {
                addSysUserDTO.setUserBirthday(DateUtil.parse(userBirthday, BIRTHDAY_PARTTER).getTime());
            } catch (Exception e) {
                sysUserExcel.setFailReason(String.format("%s ??????????????????,?????????", userValidTime));
                errorSysUserExcelList.add(sysUserExcel);
                return;
            }
        }

        try {
            sysUserService.addSysUser(addSysUserDTO, null, userId, false);
        } catch (Exception e) {
//            log.error("??????????????????", e);
            sysUserExcel.setFailReason(e.getMessage());
            errorSysUserExcelList.add(sysUserExcel);
        }
    }


    /**
     * ??????: ????????????id
     * ??????: zjt
     * ??????: 2021/5/20 15:12
     * ??????: 1.0
     */
    private List<Integer> getDeptIdList(String[] deptStr) {
        final List<Integer> sysDepartmentIdList = Lists.newArrayList();
        for (String deptName : deptStr) {
            final Integer deptId = sysDepartmentMapper.findIdByDeptName(deptName);
            if (deptId == null) {
                throw new UnsupportedOperationException(String.format("%s ????????????????????????,?????????", deptName));
            }
            sysDepartmentIdList.add(deptId);
        }
        return sysDepartmentIdList;
    }

    /**
     * ??????: ????????????
     * ??????: zjt
     * ??????: 2021/5/21 15:10
     * ??????: 1.0
     */
    private String getStrName(List<Integer> idList, Map<Integer, String> cacheMap) {
        if (CollectionUtil.isEmpty(idList)) {
            return "";
        }

        final List<String> strList = Lists.newArrayList();
        for (Integer id : idList) {
            final String str = cacheMap.get(id);
            strList.add(str);
        }

        return String.join(";", strList);
    }

}