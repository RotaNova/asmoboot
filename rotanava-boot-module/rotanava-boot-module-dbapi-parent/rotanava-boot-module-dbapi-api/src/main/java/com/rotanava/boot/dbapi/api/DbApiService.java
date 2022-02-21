package com.rotanava.boot.dbapi.api;

import com.rotanava.boot.system.api.module.system.bo.OpenDataSource;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-11-12 16:52
 **/
public interface DbApiService {

    void connect(OpenDataSource openDataSource);
}
