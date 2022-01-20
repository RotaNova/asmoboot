package com.rotanava.framework.util.excel;

import com.rotanava.framework.util.excel.annotation.ExcelTag;
import lombok.Data;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: excel测试类
 * @author: richenLi
 * @create: 2020-04-23 15:14
 **/
public class ExcelTest {
    public static void main(String[] args) throws Exception {

        OutputStream outputStream = new FileOutputStream("D://test.xls");
        List<ExcelDemoBean> list = new ArrayList<>();
        ExcelDemoBean excelDemoBean = new ExcelDemoBean();
        excelDemoBean.setName("c测试");
        excelDemoBean.setPersonId("测试学号");
        list.add(excelDemoBean);
        ExcelUtils.export(outputStream, list, ExcelDemoBean.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        //ExcelUtils.parseOneRow();

    }


    @Data
    @ExcelTag(tag = "test")
    public static class ExcelDemoBean {

        @ExcelTag(tag = "学号", fontColor = IndexedColors.RED)
        private String personId;

        @ExcelTag(tag = "名字", fontColor = IndexedColors.RED)
        private String name;
    }
}
