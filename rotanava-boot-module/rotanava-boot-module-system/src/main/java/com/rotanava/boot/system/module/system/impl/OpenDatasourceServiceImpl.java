package com.rotanava.boot.system.module.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.OpenDatasourceService;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import com.rotanava.boot.system.api.module.system.bo.OpenDataSource;
import com.rotanava.boot.system.api.module.system.vo.OpenDataSourceItem;
import com.rotanava.boot.system.module.dao.OpenApiMapper;
import com.rotanava.boot.system.module.dao.OpenDatasourceMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.PageUtils;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 数据源业务层
 * @author: richenLi
 * @create: 2021-11-10 14:59
 **/
@Service
@DubboService
@Log4j2
public class OpenDatasourceServiceImpl implements OpenDatasourceService {

    @Autowired
    OpenDatasourceMapper openDatasourceMapper;

    @Autowired
    OpenApiMapper openApiMapper;




    public void addDataSource(OpenDataSource openDataSource){
        openDatasourceMapper.insert(openDataSource);
    }


    public void updateDataSource(OpenDataSource openDataSource){
        openDatasourceMapper.updateById(openDataSource);
    }

    public void deleteDataSource(Integer id){
        QueryWrapper<OpenApi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("datasource_id",id);
        if (openApiMapper.selectCount(queryWrapper)>0){
            throw new CommonException(DBErrorCode.DB_ERROR_12);
        }
        openDatasourceMapper.deleteById(id);
    }


    @Override
    public BaseVO<OpenDataSource> findDataSourceList(BaseDTO baseDTO) {
        IPage<OpenDataSource> ipage = PageUtils.startPage(baseDTO);
        LambdaQueryWrapper<OpenDataSource> queryWrapper = QueryGenerator.initLambdaQueryWrapper(baseDTO);

        IPage<OpenDataSource> openDataSourceIPage = openDatasourceMapper.selectPage(ipage, queryWrapper);

        return BuildUtil.buildListVO(ipage.getTotal(),ipage.getRecords());
    }



    @Override
    public OpenDataSource findDataSourceById(Integer id) {
        OpenDataSource openDataSource = openDatasourceMapper.selectById(id);
        return openDataSource;
    }


    @Override
    public List<OpenDataSourceItem> getDataSourceItem(){
        QueryWrapper<OpenDataSource> queryWrapper = new QueryWrapper<>();
        List<OpenDataSource> openDataSources = openDatasourceMapper.selectList(queryWrapper);
        List<OpenDataSourceItem> list = new ArrayList<>();

        for (OpenDataSource openDataSource : openDataSources) {
            OpenDataSourceItem openDataSourceItem = new OpenDataSourceItem();
            BeanUtils.copyProperties(openDataSource,openDataSourceItem);
            list.add(openDataSourceItem);
        }
        return list;
    }
}
