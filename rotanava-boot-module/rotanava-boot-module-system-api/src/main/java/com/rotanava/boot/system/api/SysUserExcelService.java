package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.bean.SysUserExcel;
import com.rotanava.boot.system.api.module.system.bean.SysUserExcelExport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-05-21 10:04
 */
public interface SysUserExcelService {

    /**
     * 功能: 导入用户
     * 作者: zjt
     * 日期: 2021/5/21 10:06
     * 版本: 1.0
     */
    void batchImportSysUser(MultipartFile file, int userId) throws Exception;

    /**
     * 功能: 批量导出
     * 作者: zjt
     * 日期: 2021/5/21 14:41
     * 版本: 1.0
     *
     * @return
     */
    List<SysUserExcelExport> getSysUserExcelList();

    /**
     * 功能: 获取用户导入模板
     * 作者: zjt
     * 日期: 2021/5/24 16:30
     * 版本: 1.0
     */
    String getImportExcelFile();

    /**
     * 功能: 解析
     * 作者: zjt
     * 日期: 2021/5/21 10:38
     * 版本: 1.0
     */
    void parseSysUserExcel(SysUserExcel sysUserExcel, List<SysUserExcel> errorSysUserExcelList, int userId);
}
