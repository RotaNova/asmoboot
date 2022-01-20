package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.framework.util.excel.annotation.ExcelTag;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 部门导出excel bean
 * @author: jintengzhou
 * @date: 2021-05-19 17:49
 */
@Data
@ExcelTag(tag = "部门导出")
public class SysDepartmentExcel implements Serializable {

    @ExcelTag(tag = "部门名称", width = 5000, index = 0)
    private String deptName;

    @ExcelTag(tag = "部门编号", width = 5000, index = 1)
    private String deptCode;

    @ExcelTag(tag = "上级部门", width = 5000, index = 2)
    private String parentDeptName;

    @ExcelTag(tag = "有效期", width = 5000, index = 3)
    private String deptValidTime;

    @ExcelTag(tag = "部门描述", width = 5000, index = 4)
    private String deptDescription;

    @ExcelTag(tag = "部门负责人", width = 10000, index = 5)
    private String deptManager;

    @ExcelTag(tag = "负责人联系方式", width = 5000, index = 6)
    private String deptManagerPhone;

    @ExcelTag(tag = "部门地址", width = 5000, index = 7)
    private String deptAddress;

    @ExcelTag(tag = "部门传真", width = 5000, index = 8)
    private String deptFax;

    @ExcelTag(tag = "指定排序", width = 5000, index = 9)
    private String deptOrder;

    @ExcelTag(tag = "导入失败原因", width = 20000, index = 10)
    private String failReason;
}