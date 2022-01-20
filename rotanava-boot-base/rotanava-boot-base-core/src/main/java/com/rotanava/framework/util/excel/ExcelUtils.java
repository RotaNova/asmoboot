package com.rotanava.framework.util.excel;

import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import com.rotanava.framework.util.excel.annotation.ExcelTag;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelUtils {


    /**
     * 解析数据，将inputStream转为List
     *
     * @param excel
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> parse(InputStream excel, Class<T> clazz) throws Exception {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excel);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        XSSFRow titleCell = xssfSheet.getRow(0);
        List<T> dataList = new ArrayList<>(xssfSheet.getLastRowNum());
        T datum;
        final ExcelField excelField = getExcelField(clazz);
        for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i);
            datum = clazz.newInstance();
            int minCell = xssfRow.getFirstCellNum();
            int maxCell = xssfRow.getLastCellNum();
            for (int cellNum = minCell; cellNum <= maxCell; cellNum++) {
                XSSFCell title = titleCell.getCell(cellNum);
                if (title == null) {
                    continue;
                }
                String tag = title.getStringCellValue();
                Field field = getField(excelField, tag, cellNum);
                if (field == null) {
                    continue;
                }
                Class<?> type = field.getType();
                Object value = null;
                XSSFCell cell = xssfRow.getCell(cellNum);
                if (cell == null) {
                    continue;
                }
                if (type.equals(String.class)) {
                    cell.setCellType(CellType.STRING);
                    value = cell.getStringCellValue();
                } else if (type.equals(Date.class)) {
                    PropertyUtils.setProperty(datum, field.getName(), value);
                    value = cell.getDateCellValue();
                } else if (type.equals(Integer.class)) {
                    cell.setCellType(CellType.STRING);
                    value = Convert.toInt(cell.getStringCellValue());
                }
                PropertyUtils.setProperty(datum, field.getName(), value);
            }
            dataList.add(datum);
        }
        return dataList;
    }

    private static Field getField(ExcelField excelField, String tag, int index) {
        Field field = excelField.getFieldList().get(index);
        if (field != null) {
            return field;
        }

        field = excelField.getFieldMap().get(tag);
        return field;
    }

    private static ExcelField getExcelField(Class clazz) {
        final ExcelField excelField = new ExcelField();

        Field[] fields = FieldUtils.getFieldsWithAnnotation(clazz, ExcelTag.class);
        final Field[] fieldsArray = new Field[fields.length];

        Map<String, Field> fieldMap = new HashMap<>(fields.length / 3 * 4);
        for (Field field : fields) {
            ExcelTag excelTag = field.getAnnotation(ExcelTag.class);
            fieldMap.put(excelTag.tag(), field);

            final int index = excelTag.index();
            if (index >= 0) {
                fieldsArray[index] = field;
            }
        }
        excelField.setFieldMap(fieldMap);
        excelField.setFieldList(Lists.newArrayList(fieldsArray));
        return excelField;
    }

    /**
     * 导出数据到outputStream
     *
     * @param outputStream
     * @param dataList
     * @param clazz
     * @param <T>
     * @throws Exception
     */
    public static <T> void export(OutputStream outputStream, List<T> dataList, Class<T> clazz) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        final XSSFCellStyle cellStyle = wb.createCellStyle();
        ExcelTag annotation = clazz.getAnnotation(ExcelTag.class);
        String tag = annotation.tag();
        //工作簿
        XSSFSheet sheet = wb.createSheet(tag);
        Field[] fields = FieldUtils.getFieldsWithAnnotation(clazz, ExcelTag.class);
        //表头
        XSSFRow headers = sheet.createRow(0);
        for (int index = 0; index < fields.length; index++) {
            Field field = fields[index];
            CellType type;
            if (String.class.equals(field.getType())) {
                type = CellType.STRING;
            }
            if (double.class.equals(field.getType())) {
                type = CellType.STRING;
            }
            if (long.class.equals(field.getType())) {
                type = CellType.STRING;
            }
            if (Integer.class.equals(field.getType())) {
                type = CellType.STRING;
            }
            if (int.class.equals(field.getType())) {
                type = CellType.STRING;
            }
           /* if (Integer.class.equals(field.getType())) {
               type =Cell.CELL_TYPE_NUMERIC;
            }*/
            ExcelTag excelTag = field.getAnnotation(ExcelTag.class);
            XSSFCell cell = headers.createCell(index, CellType.STRING);
            XSSFFont font = wb.createFont();
            font.setColor(excelTag.fontColor().getIndex());
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
            tag = excelTag.tag();
            cell.setCellValue(tag);
            sheet.setColumnWidth(index, excelTag.width());
        }
        //插入数据
        XSSFRow row;
        Field field;
        for (int i = 0; i < dataList.size(); i++) {
            T datum = dataList.get(i);
            row = sheet.createRow(i + 1);
            for (int index = 0; index < fields.length; index++) {
                field = fields[index];
                XSSFCell cell = row.createCell(index, CellType.NUMERIC);
                cell.setCellStyle(cellStyle);
                Object property = PropertyUtils.getProperty(datum, field.getName());
                if (property == null) {
                    cell.setCellValue("");
                } else {
                    if (String.class.equals(field.getType())) {
                        cell.setCellValue((String) property);
                    }
                    if (double.class.equals(field.getType())) {
                        cell.setCellValue(String.valueOf(property));
                    }
                    if (long.class.equals(field.getType())) {
                        cell.setCellValue(String.valueOf(property));
                    } else if (Date.class.equals(field.getType())) {
                        cell.setCellValue((Date) property);
                    } else if (Long.class.equals(field.getType())) {
                        cell.setCellValue((Long) property);
                    } else if (Integer.class.equals(field.getType())) {
                        cell.setCellValue((Integer) property);
                    }
                }
            }
        }
        //生成文档
        wb.write(outputStream);
    }

    /**
     * 导出数据到outputStream
     *
     * @param outputStream
     * @param dataList
     * @throws Exception
     */
    public static <T> void export(OutputStream outputStream, List<Map<String, String>> dataList, Map<String, String> header) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        //工作簿
        XSSFSheet sheet = wb.createSheet("报表");
        //表头
        XSSFRow headers = sheet.createRow(0);

        //表头
        final Iterator<Map.Entry<String, String>> entryIterator = header.entrySet().iterator();
        int column = 0;
        while (entryIterator.hasNext()) {
            final Map.Entry<String, String> entry = entryIterator.next();
            final String value = entry.getValue();
            XSSFCell cell = headers.createCell(column, CellType.STRING);
            XSSFCellStyle style = wb.createCellStyle();
            XSSFFont font = wb.createFont();
            font.setColor(IndexedColors.BLACK.getIndex());
            style.setFont(font);
            cell.setCellStyle(style);
            cell.setCellValue(value);
            column++;
        }

        //插入数据
        XSSFRow row;
        final XSSFCellStyle cellStyle = wb.createCellStyle();
        for (int i = 0; i < dataList.size(); i++) {
            final Map<String, String> datum = dataList.get(i);
            final List<String> values = Lists.newArrayList(datum.values());
            row = sheet.createRow(i + 1);
            for (int index = 0; index < header.size(); index++) {
                XSSFCell cell = row.createCell(index, CellType.NUMERIC);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(values.get(index));
            }
        }
        //生成文档
        wb.write(outputStream);
    }

    /**
     * 只生成第一行的excel文件
     */
    public static <T> List<T> parseo(InputStream excel, Class<T> clazz) throws Exception {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excel);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        XSSFRow titleCell = xssfSheet.getRow(0);
        List<T> dataList = new ArrayList<>(xssfSheet.getLastRowNum());
        T datum;
        final ExcelField excelField = getExcelField(clazz);
        for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i);
            datum = clazz.newInstance();
            int minCell = xssfRow.getFirstCellNum();
            int maxCell = xssfRow.getLastCellNum();
            for (int cellNum = minCell; cellNum <= maxCell; cellNum++) {
                XSSFCell title = titleCell.getCell(cellNum);
                if (title == null) {
                    continue;
                }
                String tag = title.getStringCellValue();
                Field field = getField(excelField, tag, cellNum);
                if (field == null) {
                    continue;
                }
                Class<?> type = field.getType();
                Object value = null;
                XSSFCell cell = xssfRow.getCell(cellNum);
                if (cell == null) {
                    continue;
                }
                if (type.equals(String.class)) {
                    cell.setCellType(CellType.STRING);
                    value = cell.getStringCellValue();
                } else if (type.equals(Date.class)) {
                    value = cell.getDateCellValue();
                }
                PropertyUtils.setProperty(datum, field.getName(), value);
            }
            dataList.add(datum);
        }
        return dataList;
    }

}


@Data
class ExcelField implements Serializable {

    private Map<String, Field> fieldMap;

    private List<Field> fieldList;
}