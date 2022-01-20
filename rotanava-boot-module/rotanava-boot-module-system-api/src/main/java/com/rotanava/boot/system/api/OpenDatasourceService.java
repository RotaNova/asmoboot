package com.rotanava.boot.system.api;


import com.rotanava.boot.system.api.module.system.bo.OpenDataSource;
import com.rotanava.boot.system.api.module.system.vo.OpenDataSourceItem;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface OpenDatasourceService {


    void addDataSource(OpenDataSource openDataSource);


    void updateDataSource(OpenDataSource openDataSource);


    void deleteDataSource(Integer id);


    BaseVO<OpenDataSource> findDataSourceList(BaseDTO baseDTO);


    OpenDataSource findDataSourceById(Integer id);


    List<OpenDataSourceItem> getDataSourceItem();
}
