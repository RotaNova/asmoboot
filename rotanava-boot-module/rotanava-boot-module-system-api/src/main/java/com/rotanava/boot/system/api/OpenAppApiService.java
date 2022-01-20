package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import com.rotanava.boot.system.api.module.system.bo.OpenApiParam;
import com.rotanava.boot.system.api.module.system.dto.GetOpenApiPageListDTO;
import com.rotanava.boot.system.api.module.system.dto.RegisteredOpenAppApiDTO;
import com.rotanava.boot.system.api.module.system.dto.SaveSqlConfigurationDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateOpenAppApiDTO;
import com.rotanava.boot.system.api.module.system.vo.OpenApiItemVO;
import com.rotanava.boot.system.api.module.system.vo.SqlConfigurationVO;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

/**
 * @description: api注册
 * @author: jintengzhou
 * @date: 2021-04-19 15:05
 */
@Validated
public interface OpenAppApiService {

    /**
     * 功能: 注册api
     * 作者: zjt
     * 日期: 2021/4/19 15:06
     * 版本: 1.0
     */
    void registeredOpenAppApi(RegisteredOpenAppApiDTO registeredOpenAppApi, int userId);

    /**
     * 功能: 修改api
     * 作者: zjt
     * 日期: 2021/4/19 15:06
     * 版本: 1.0
     */
    void updateOpenAppApi(UpdateOpenAppApiDTO updateOpenAppApiDTO);

    /**
     * 功能: 移除api
     * 作者: zjt
     * 日期: 2021/4/20 9:40
     * 版本: 1.0
     */
    void removeOpenAppApi(int openApiId);

    /**
     * 功能: 锁定api
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    void lockOpenAppApi(int openApiId);

    /**
     * 功能: 解锁api
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    void unLockOpenAppApi(int openApiId);

    /**
     * 功能: 保存sql配置
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    void saveSqlConfiguration(SaveSqlConfigurationDTO saveSqlConfigurationDTO, int userId);

    /**
     * 功能: 获取sql配置
     * 作者: zjt
     * 日期: 2021/4/20 9:41
     * 版本: 1.0
     */
    SqlConfigurationVO getSqlConfiguration(int openApiId);

    /**
     * 功能:分页获取api列表
     * 作者: zjt
     * 日期: 2021/4/20 10:50
     * 版本: 1.0
     *
     * @param getOpenApiPageList
     */
    IPage<OpenApiItemVO> getOpenApiPageList(GetOpenApiPageListDTO getOpenApiPageList);

    /**
     * 功能: api绑定应用
     * 作者: zjt
     * 日期: 2021/4/20 10:57
     * 版本: 1.0
     */
    void apiBindApp(Collection<Integer> openApiIdList, int openAppId, int userId);

    /**
     * 功能: 移除api绑定的应用
     * 作者: zjt
     * 日期: 2021/4/20 11:06
     * 版本: 1.0
     */
    void apiUnBindApp(Collection<Integer> openApiIdList, int openAppId);


    OpenApi getApiByRequest(String path
            , Integer appId
            ,  Integer requestMethod);


    List<OpenApiParam> getParamByApiId(Integer apiId);
}
