package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.OpenAppService;
import com.rotanava.boot.system.api.module.constant.PageShow;
import com.rotanava.boot.system.api.module.system.bo.OpenApp;
import com.rotanava.boot.system.api.module.system.bo.SysPageModuleType;
import com.rotanava.boot.system.api.module.system.dto.CreateApplicationDTO;
import com.rotanava.boot.system.api.module.system.dto.EnableAppDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateApplicationDTO;
import com.rotanava.boot.system.api.module.system.vo.OpenAppItemVO;
import com.rotanava.boot.system.api.module.system.vo.OpenAppVO;
import com.rotanava.boot.system.module.dao.OpenAppMapper;
import com.rotanava.boot.system.module.dao.SysPageModuleTypeMapper;
import com.rotanava.boot.system.module.dao.SysPagePermissionMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 15:09
 */
@Service
@DubboService
public class OpenAppImpl implements OpenAppService {

    @Autowired
    private OpenAppMapper openAppMapper;

    @Autowired
    private SysPageModuleTypeMapper sysPageModuleTypeMapper;

    @Autowired
    private SysPagePermissionMapper sysPagePermissionMapper;


    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createOpenApp(CreateApplicationDTO createApplication, int userId) {
        final OpenApp openApp = new OpenApp();
        openApp.setAppKey(Convert.toStr(BaseUtil.getSnowflakeId()));
        final String appName = createApplication.getAppName();

        if (openAppMapper.countByAppName(appName) > 0) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_58);
        }

        openApp.setAppSecret(BaseUtil.getUId());
        openApp.setAppName(appName);
        openApp.setCreateBy(userId);
        openApp.setRemark(createApplication.getRemark());
        openApp.setCreateTime(new Date());
        openApp.setAgentId(Convert.toStr(BaseUtil.getSnowflakeId()));
        openAppMapper.insert(openApp);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateOpenApp(UpdateApplicationDTO updateApplication, byte[] imageByte) {
        final Integer openAppId = updateApplication.getOpenAppId();
        final String appName = updateApplication.getAppName();
        final OpenApp openApp = openAppMapper.selectById(openAppId);
        if (openApp == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        } else {
            if (!openApp.getAppName().equals(appName) && openAppMapper.countByAppName(appName) > 0) {
                throw new CommonException(ParamErrorCode.PARAM_ERROR_58);
            }

            if (imageByte != null) {
                final UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.OPEN_API_BUCKET, new ByteArrayInputStream(imageByte), "");
                final String bucketName = uploadResultBean.getBucketName();
                final String objName = uploadResultBean.getObjName();
                openApp.setLogoBucketName(bucketName);
                openApp.setLogoObjectName(objName);
            }

            openApp.setAppName(updateApplication.getAppName());
            openApp.setChargePerson(updateApplication.getChargePerson());
            openApp.setContactPhone(updateApplication.getContactPhone());
            openApp.setRemark(updateApplication.getRemark());

            if (openApp.getModuleId() != null) {
                final SysPageModuleType sysPageModuleType = new SysPageModuleType();
                sysPageModuleType.setId(openApp.getModuleId());
                sysPageModuleType.setName(openApp.getAppName());
                sysPageModuleTypeMapper.updateById(sysPageModuleType);
            }

            openAppMapper.updateById(openApp);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteOpenApp(int openAppId) {
        final OpenApp openApp = openAppMapper.selectById(openAppId);
        if (openApp.getModuleId() != null) {
            sysPageModuleTypeMapper.deleteById(openApp.getModuleId());
        }
        sysPagePermissionMapper.deleteBySysPageModuleTypeId(openApp.getModuleId());
        openAppMapper.deleteById(openAppId);
    }

    @Override
    public OpenAppVO getOpenApp(int openAppId) {
        final OpenApp openApp = openAppMapper.selectById(openAppId);
        if (openApp == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        } else {
            final OpenAppVO openAppVO = new OpenAppVO();
            openAppVO.setOpenAppId(openApp.getId());
            openAppVO.setAgentId(openApp.getAgentId());
            openAppVO.setAppKey(openApp.getAppKey());
            openAppVO.setAppSecret(openApp.getAppSecret());
            openAppVO.setChargePerson(openApp.getChargePerson());
            openAppVO.setAppName(openApp.getAppName());
            openAppVO.setContactPhone(openApp.getContactPhone());
            openAppVO.setRemark(openApp.getRemark());

            //设置图片
            if (StringUtils.isNoneEmpty(openApp.getLogoBucketName(), openApp.getLogoObjectName())) {
                openAppVO.setAppImageUrl(fileUploadUtil.getObjUrl(openApp.getLogoBucketName(), openApp.getLogoObjectName(), 10));
            }

            return openAppVO;
        }
    }

    @Override
    public IPage<OpenAppItemVO> getOpenAppPageList(BaseDTO baseDTO) {
        QueryWrapper<OpenApp> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        final IPage<OpenApp> openAppIPage = openAppMapper.selectPage(PageUtils.startPage(baseDTO), queryWrapper);

        final List<OpenAppItemVO> sysRoleItemVOList = openAppIPage.getRecords().stream().map(openApp -> {
            final OpenAppItemVO openAppItemVO = new OpenAppItemVO();
            openAppItemVO.setOpenAppId(openApp.getId());
            openAppItemVO.setAppName(openApp.getAppName());
            openAppItemVO.setRemark(openApp.getRemark());
            openAppItemVO.setChargePerson(openApp.getChargePerson());
            openAppItemVO.setContactPhone(openApp.getContactPhone());

            final String logoBucketName = openApp.getLogoBucketName();
            final String logoObjectName = openApp.getLogoObjectName();
            if (StringUtils.isNoneEmpty(logoBucketName, logoObjectName)) {
                openAppItemVO.setAppImageUrl(fileUploadUtil.getObjUrl(logoBucketName, logoObjectName, 10));
            }
            openAppItemVO.setIsSwitch(openApp.getIsSwitch() != null && openApp.getIsSwitch() == 1);
            return openAppItemVO;
        }).collect(Collectors.toList());

        return PageUtils.conversionIpageRecords(openAppIPage, OpenAppItemVO.class, sysRoleItemVOList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void enableApp(EnableAppDTO enableAppDTO) {

        final Integer openAppId = enableAppDTO.getOpenAppId();
        final OpenApp openApp = openAppMapper.selectById(openAppId);
        if (openApp == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }

        if (enableAppDTO.getIsSwitch()) {
            final SysPageModuleType sysPageModuleType = new SysPageModuleType();
            if(openApp.getModuleId() == null){
                sysPageModuleType.setName(openApp.getAppName());
                sysPageModuleTypeMapper.insert(sysPageModuleType);
                openApp.setModuleId(sysPageModuleType.getId());
            }
            openApp.setIsSwitch(1);
            openAppMapper.updateById(openApp);
        } else {
            openAppMapper.updateSetIsSwitch(openAppId);
//            if (openApp.getModuleId() != null) {
//                sysPageModuleTypeMapper.deleteById(openApp.getModuleId());
//            }
//            sysPagePermissionMapper.deleteBySysPageModuleTypeId(openApp.getModuleId());
        }
    }


    @Override
    public OpenApp getAppByKeyAndSecret(String appKey,String appSecret) {
        QueryWrapper<OpenApp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_key", appKey);
        queryWrapper.eq("app_secret", appSecret);
                OpenApp openApp = openAppMapper.selectOne(queryWrapper);
        return openApp;

    }
}