package com.rotanava.boot.system.module.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.OpenAppApiService;
import com.rotanava.boot.system.api.OpenDatasourceService;
import com.rotanava.boot.system.api.module.constant.OpenApiParamType;
import com.rotanava.boot.system.api.module.constant.OpenApiStatus;
import com.rotanava.boot.system.api.module.system.bean.ApiParam;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import com.rotanava.boot.system.api.module.system.bo.OpenApiApp;
import com.rotanava.boot.system.api.module.system.bo.OpenApiParam;
import com.rotanava.boot.system.api.module.system.dto.GetOpenApiPageListDTO;
import com.rotanava.boot.system.api.module.system.dto.RegisteredOpenAppApiDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveSqlConfigurationDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateOpenAppApiDTO;
import com.rotanava.boot.system.api.module.system.vo.OpenApiItemVO;
import com.rotanava.boot.system.api.module.system.vo.SqlConfigurationVO;
import com.rotanava.boot.system.module.dao.OpenApiAppMapper;
import com.rotanava.boot.system.module.dao.OpenApiMapper;
import com.rotanava.boot.system.module.dao.OpenApiParamMapper;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.util.PageUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: api管理
 * @author: jintengzhou
 * @date: 2021-04-19 15:09
 */
@Service
@DubboService
public class OpenAppApiImpl implements OpenAppApiService {

    @Autowired
    private OpenApiMapper openApiMapper;

    @Autowired
    private OpenApiParamMapper openApiParamMapper;

    @Autowired
    private OpenApiAppMapper openApiAppMapper;

    @Autowired
    private OpenDatasourceService openDatasourceService;


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void registeredOpenAppApi(RegisteredOpenAppApiDTO registeredOpenAppApi, int userId) {
        final String apiName = registeredOpenAppApi.getApiName();
        final String apiPath = registeredOpenAppApi.getApiPath();
        final String chargePerson = registeredOpenAppApi.getChargePerson();
        final String remark = registeredOpenAppApi.getRemark();
        final Integer requestMethod = registeredOpenAppApi.getRequestMethod();
        final Integer resultType = registeredOpenAppApi.getResultType();

        final Integer countByApiPath = openApiMapper.countByApiPath(apiPath);
        if (countByApiPath > 0) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_59);
        }

        final OpenApi openApi = new OpenApi();
        openApi.setApiName(apiName);
        openApi.setApiPath(apiPath);
        openApi.setChargePerson(chargePerson);
        openApi.setCreateBy(userId);
        openApi.setCreateTime(new Date());
        openApi.setRemark(remark);
        openApi.setRequestMethod(requestMethod);
        openApi.setResultType(resultType);
        openApi.setStatus(OpenApiStatus.NORMAL.getStatus());
        openApiMapper.insert(openApi);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateOpenAppApi(UpdateOpenAppApiDTO updateOpenAppApiDTO) {
        final Integer openApiId = updateOpenAppApiDTO.getOpenApiId();
        final String apiPath = updateOpenAppApiDTO.getApiPath();
        final String apiName = updateOpenAppApiDTO.getApiName();
        final String chargePerson = updateOpenAppApiDTO.getChargePerson();
        final String remark = updateOpenAppApiDTO.getRemark();
        final Integer requestMethod = updateOpenAppApiDTO.getRequestMethod();
        final Integer resultType = updateOpenAppApiDTO.getResultType();


        final OpenApi openApi = openApiMapper.selectById(openApiId);
        if (openApi == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        } else {
            openApi.setApiPath(apiPath);
            openApi.setApiName(apiName);
            openApi.setChargePerson(chargePerson);
            openApi.setRemark(remark);
            openApi.setRequestMethod(requestMethod);
            openApi.setResultType(resultType);
            openApiMapper.updateById(openApi);
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void removeOpenAppApi(int openApiId) {
        openApiMapper.deleteById(openApiId);
        openApiAppMapper.deleteByApiId(openApiId);
        openApiParamMapper.deleteByApiId(openApiId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void lockOpenAppApi(int openApiId) {
        final OpenApi openApi = new OpenApi();
        openApi.setId(openApiId);
        openApi.setStatus(OpenApiStatus.LOCK.getStatus());
        openApiMapper.updateById(openApi);
    }

    @Override
    public void unLockOpenAppApi(int openApiId) {
        final OpenApi openApi = new OpenApi();
        openApi.setId(openApiId);
        openApi.setStatus(OpenApiStatus.NORMAL.getStatus());
        openApiMapper.updateById(openApi);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveSqlConfiguration(SaveSqlConfigurationDTO saveSqlConfigurationDTO, int userId) {
        final Integer openApiId = saveSqlConfigurationDTO.getOpenApiId();
        final String sqlText = saveSqlConfigurationDTO.getSqlText();
        final Integer dataSourceId = saveSqlConfigurationDTO.getDatasourceId();

        openApiMapper.updateSqlTextById(sqlText, openApiId);
        openApiMapper.updateDataSourceById(dataSourceId, openApiId);
        openApiParamMapper.deleteByApiId(openApiId);

        final List<ApiParam> requestApiParam = saveSqlConfigurationDTO.getRequestApiParam();
        doSaveSqlConfiguration(OpenApiParamType.REQUEST_PARAMETERS, openApiId, userId, requestApiParam);
        final List<ApiParam> returnApiParam = saveSqlConfigurationDTO.getReturnApiParam();
        doSaveSqlConfiguration(OpenApiParamType.RETURN_PARAMETERS, openApiId, userId, returnApiParam);

    }

    private void doSaveSqlConfiguration(OpenApiParamType openApiParamType, int openApiId, int userId, List<ApiParam> apiParam) {
        for (ApiParam apiParamDTO : apiParam) {
            final OpenApiParam openApiParam = new OpenApiParam();
            openApiParam.setApiId(openApiId);
            openApiParam.setCodeAlias(apiParamDTO.getCodeAlias());
            openApiParam.setCreateTime(new Date());
            openApiParam.setCreateBy(userId);
            openApiParam.setIsFill(apiParamDTO.getIsFill());
            openApiParam.setParamCode(apiParamDTO.getParamCode());
            openApiParam.setParamType(apiParamDTO.getParamType());
            openApiParam.setRemark(apiParamDTO.getRemark());
            openApiParam.setType(openApiParamType.getType());
            openApiParamMapper.insert(openApiParam);
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public SqlConfigurationVO getSqlConfiguration(int openApiId) {
        final OpenApi openApi = openApiMapper.selectById(openApiId);
        if (openApi == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        } else {
            final SqlConfigurationVO sqlConfigurationVO = new SqlConfigurationVO();
            sqlConfigurationVO.setOpenApiId(openApi.getId());
            sqlConfigurationVO.setSqlText(openApi.getSqlText());
            sqlConfigurationVO.setRequestApiParam(getApiParam(OpenApiParamType.REQUEST_PARAMETERS, openApiId));
            sqlConfigurationVO.setReturnApiParam(getApiParam(OpenApiParamType.RETURN_PARAMETERS, openApiId));
            sqlConfigurationVO.setDatasourceId(openApi.getDatasourceId());
            sqlConfigurationVO.setDataSourceItems(openDatasourceService.getDataSourceItem());
            return sqlConfigurationVO;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public IPage<OpenApiItemVO> getOpenApiPageList(GetOpenApiPageListDTO getOpenApiPageList) {
        final QueryWrapper<OpenApi> queryWrapper = QueryGenerator.initQueryWrapper(getOpenApiPageList);

        if (getOpenApiPageList.getOpenAppId() != null) {
            queryWrapper.eq("b.app_id", getOpenApiPageList.getOpenAppId());
        }

        queryWrapper.groupBy("a.id");

        final IPage<OpenApi> openApiIPage = openApiMapper.queryPage(queryWrapper, PageUtils.startPage(getOpenApiPageList));

        final List<OpenApiItemVO> openApiItemVOList = openApiIPage.getRecords().stream().map(openApi -> {
            final OpenApiItemVO openApiItemVO = new OpenApiItemVO();
            openApiItemVO.setOpenApiId(openApi.getId());
            openApiItemVO.setApiName(openApi.getApiName());
            openApiItemVO.setRequestMethod(openApi.getRequestMethod());
            openApiItemVO.setApiPath(openApi.getApiPath());
            openApiItemVO.setRemark(openApi.getRemark());
            openApiItemVO.setChargePerson(openApi.getChargePerson());
            openApiItemVO.setCreateTime(openApi.getCreateTime().getTime());
            openApiItemVO.setStatus(openApi.getStatus());
            openApiItemVO.setResultType(openApi.getResultType());
            return openApiItemVO;
        }).collect(Collectors.toList());

        return PageUtils.conversionIpageRecords(openApiIPage, OpenApiItemVO.class, openApiItemVOList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void apiBindApp(Collection<Integer> openApiIdList, int openAppId, int userId) {
        for (Integer openApiId : openApiIdList) {
            final Integer countByAppIdAndApiId = openApiAppMapper.countByAppIdAndApiId(openAppId, openApiId);
            if (countByAppIdAndApiId == 0) {
                final OpenApiApp openApiApp = new OpenApiApp();
                openApiApp.setApiId(openApiId);
                openApiApp.setAppId(openAppId);
                openApiApp.setCreateTime(new Date());
                openApiApp.setCreateBy(userId);
                openApiAppMapper.insert(openApiApp);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void apiUnBindApp(Collection<Integer> openApiIdList, int openAppId) {
        openApiAppMapper.deleteByApiIdInAndAppId(openApiIdList, openAppId);


    }

    /**
     * 功能: 获取apiParam
     * 作者: zjt
     * 日期: 2021/4/20 10:43
     * 版本: 1.0
     */
    private List<ApiParam> getApiParam(OpenApiParamType openApiParamType, int openApiId) {
        final List<OpenApiParam> openApiParamList = openApiParamMapper.findByApiIdAndType(openApiId, openApiParamType.getType());
        List<ApiParam> apiParamList = Lists.newArrayList();

        for (OpenApiParam openApiParam : openApiParamList) {
            final ApiParam apiParam = new ApiParam();
            apiParam.setApiId(openApiId);
            apiParam.setApiParamId(openApiParam.getId());
            apiParam.setCodeAlias(openApiParam.getCodeAlias());
            apiParam.setIsFill(openApiParam.getIsFill());
            apiParam.setParamCode(openApiParam.getParamCode());
            apiParam.setParamType(openApiParam.getParamType());
            apiParam.setRemark(openApiParam.getRemark());
            apiParamList.add(apiParam);
        }

        return apiParamList;
    }

    @Override
    public OpenApi getApiByRequest(String path, Integer appId, Integer requestMethod) {
        return openApiMapper.getApiByRequest(path, appId, requestMethod);
    }

    @Override
    public List<OpenApiParam> getParamByApiId(Integer apiId) {
        QueryWrapper<OpenApiParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("api_id", apiId);
        List<OpenApiParam> openApiParams = openApiParamMapper.selectList(queryWrapper);
        return openApiParams;
    }
}