package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.bean.SysDepartmentExcel;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentExport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-05-21 17:32
 */
public interface SysDepartmentExcelService {

    /**
     * 功能: 导出部门
     * 作者: zjt
     * 日期: 2021/5/21 17:32
     * 版本: 1.0
     */
    List<SysDepartmentExport> exportSysDepartment();


    /**
     * 功能: 导入部门
     * 作者: zjt
     * 日期: 2021/5/21 17:32
     * 版本: 1.0
     */
    void batchImportSysDepartment(MultipartFile file, int userId) throws Exception;

    /**
     * 功能: 获取导入部门模板
     * 作者: zjt
     * 日期: 2021/5/24 16:30
     * 版本: 1.0
     */
    String getImportExcelFile();


    /**
     * 功能: 解析部门导入
     * 作者: zjt
     * 日期: 2021/5/24 14:55
     * 版本: 1.0
     */
    void parseSysDepartmentExcel(SysDepartmentExcel sysDepartmentExcel, List<SysDepartmentExcel> errorSysDepartmentExcelList, int userId);

}
