package com.rotanava.boot.dbapi.service.impl;

import com.rotanava.boot.dbapi.api.DbApiService;
import com.rotanava.boot.system.api.module.system.bo.OpenDataSource;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.exception.code.DBErrorCode;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-11-12 16:53
 **/
@Log4j2
@Service
@DubboService
public class DbApiServiceImpl implements DbApiService {
    @Override
    public void connect(OpenDataSource openDataSource) {
        Connection connection = null;
        try {
            Class.forName(openDataSource.getDriver());
            connection = DriverManager.getConnection(openDataSource.getUrl(), openDataSource.getUserName(), openDataSource.getUserPassword());
            log.info("获取连接成功");
//            return RetData.ok("数据库连接成功");
        } catch (ClassNotFoundException e) {
            log.error(e);
            throw new CommonException(DBErrorCode.DB_ERROR_13);
        } catch (SQLException sqlException) {
            log.error(sqlException);
            throw new CommonException(DBErrorCode.DB_ERROR_14);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
