package com.rotanava.boot.dbapi.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-11-12 11:09
 **/
@Configuration
public class DBConfig {

    /**
     * 自动识别使用的数据库类型
     * 在mapper.xml中databaseId的值就是跟这里对应，
     * 如果没有databaseId选择则说明该sql适用所有数据库
     * */
//    @Bean
//    public DatabaseIdProvider getDatabaseIdProvider(){
//        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
//        Properties properties = new Properties();
//        properties.setProperty("Oracle","oracle");
//        properties.setProperty("MySQL","mysql");
//        properties.setProperty("DB2","db2");
//        properties.setProperty("Derby","derby");
//        properties.setProperty("H2","h2");
//        properties.setProperty("HSQL","hsql");
//        properties.setProperty("Informix","informix");
//        properties.setProperty("MS-SQL","ms-sql");
//        properties.setProperty("PostgreSQL","postgresql");
//        properties.setProperty("Sybase","sybase");
//        properties.setProperty("Hana","hana");
//        databaseIdProvider.setProperties(properties);
//        return databaseIdProvider;
//    }

}
