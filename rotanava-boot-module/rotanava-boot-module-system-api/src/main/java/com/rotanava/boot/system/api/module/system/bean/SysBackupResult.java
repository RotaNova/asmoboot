package com.rotanava.boot.system.api.module.system.bean;

import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-06 11:05
 **/
@Data
public class SysBackupResult {

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件桶名
     */
    private String bucketName;

    /**
     * 文件名
     */
    private String objectName;


}
