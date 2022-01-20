package com.rotanava.boot.dbapi.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.rotanava.boot.system.api.module.system.bo.OpenDataSource;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.DBErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: api
 * @description:
 * @author: jiangqiang
 * @create: 2020-12-11 10:51
 **/
@Slf4j
public class PoolManager {

    private static Lock lock = new ReentrantLock();

    private static Lock deleteLock = new ReentrantLock();

    //所有数据源的连接池存在map里
    static Map<Integer, DruidDataSource> map = new HashMap<>();

    public static DruidDataSource getJdbcConnectionPool(OpenDataSource ds) {
        if (map.containsKey(ds.getId())) {
            return map.get(ds.getId());
        } else {
            lock.lock();
            try {
                log.info(Thread.currentThread().getName() + "获取锁");
                if (!map.containsKey(ds.getId())) {
                    DruidDataSource druidDataSource = new DruidDataSource();
                    druidDataSource.setName(ds.getUserName());
                    druidDataSource.setUrl(ds.getUrl());
                    druidDataSource.setUsername(ds.getUserName());
                    druidDataSource.setPassword(ds.getUserPassword());
                    druidDataSource.setDriverClassName(ds.getDriver());
                    druidDataSource.setConnectionErrorRetryAttempts(2);       //失败后重连次数
                    druidDataSource.setBreakAfterAcquireFailure(true);
                    druidDataSource.setLoginTimeout(3);

                    map.put(ds.getId(), druidDataSource);
                    log.info("创建Druid连接池成功：{}", ds.getDatasourceName());
                }
                return map.get(ds.getId());
            } catch (Exception e) {
                return null;
            } finally {
                lock.unlock();
            }
        }
    }

    //删除数据库连接池
    public static void removeJdbcConnectionPool(Integer id) {
        deleteLock.lock();
        try {
            DruidDataSource druidDataSource = map.get(id);
            if (druidDataSource != null) {
                druidDataSource.close();
                map.remove(id);
            }
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            deleteLock.unlock();
        }

    }

    public static DruidPooledConnection getPooledConnection(OpenDataSource ds)  {
        try {
            DruidDataSource pool = PoolManager.getJdbcConnectionPool(ds);
            DruidPooledConnection connection = pool.getConnection(2000);
            log.info("获取连接成功");
            return connection;
        }catch (Exception e){
            log.error("连接数据库失败",e);
            PoolManager.removeJdbcConnectionPool(ds.getId());
            throw new CommonException(DBErrorCode.DB_ERROR_14);
        }
    }
}
