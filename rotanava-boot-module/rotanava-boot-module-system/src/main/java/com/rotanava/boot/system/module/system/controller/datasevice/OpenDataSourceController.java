package com.rotanava.boot.system.module.system.controller.datasevice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.dbapi.api.DbApiService;
import com.rotanava.boot.system.api.OpenAppApiService;
import com.rotanava.boot.system.api.OpenDatasourceService;
import com.rotanava.boot.system.api.module.system.bo.OpenDataSource;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.OpenApiItemVO;
import com.rotanava.boot.system.api.module.system.vo.SqlConfigurationVO;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.util.SysUtil;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


@Api(tags = "数据源管理")
@Log4j2
@Validated
@RestController
@RequestMapping("/v1/openDatasource")
public class OpenDataSourceController {

    @Autowired
    private OpenDatasourceService openDatasourceService;

    @DubboReference
    private DbApiService dbApiService;


    @PostMapping("/createDatasource")
    @AutoLog(value = "添加数据源", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData createOpenApp(@RequestBody OpenDataSource openDataSource) {
        openDatasourceService.addDataSource(openDataSource);
        return RetData.ok();
    }


    @PostMapping("/updateDatasource")
    @AutoLog(value = "修改数据源", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData updateDatasource(@RequestBody OpenDataSource openDataSource) {
        openDatasourceService.updateDataSource(openDataSource);
        return RetData.ok();
    }


    @PostMapping("/deleteDatasource")
    @AutoLog(value = "删除数据源", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData deleteDatasource(@RequestBody List<Integer> list) {
        for (Integer id : list) {
            openDatasourceService.deleteDataSource(id);
        }
        return RetData.ok();
    }


    @PostMapping("/findDataSourceList")
    @AutoLog(value = "查找数据源列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData findDataSourceList(@RequestBody BaseDTO baseDTO) {
        return RetData.ok(openDatasourceService.findDataSourceList(baseDTO));
    }


    @PostMapping("/connect")
    @AutoLog(value = "数据源测试连接", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData connect(@RequestBody OpenDataSource openDataSource) {
//        Connection connection = null;
//        try {
//            Class.forName(openDataSource.getDriver());
//            connection = DriverManager.getConnection(openDataSource.getUrl(), openDataSource.getUserName(), openDataSource.getUserPassword());
//            log.info("获取连接成功");
//            return RetData.ok("数据库连接成功");
//        } catch (ClassNotFoundException e) {
//            log.error(e);
//            throw new CommonException(DBErrorCode.DB_ERROR_13);
//        } catch (SQLException sqlException) {
//            log.error(sqlException);
//            throw new CommonException(DBErrorCode.DB_ERROR_14);
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        dbApiService.connect(openDataSource);
        return RetData.ok();
    }

}