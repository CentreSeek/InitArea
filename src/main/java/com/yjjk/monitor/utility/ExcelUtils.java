/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ExcelUtils
 * Author:   CentreS
 * Date:     2019/7/3 15:10
 * Description: Excel工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author CentreS
 * @Description: Excel工具类
 * @create 2019/7/3
 */
public class ExcelUtils {
    /**
     * 是否是2003的excel，返回true是2003
     *
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        return true;
    }

    public static <T> void exportExcel(HttpServletResponse response, List<T> excelData, String fileName, String[] cellsName) throws IOException {
        exportExcel(response, excelData, "sheet1", fileName, cellsName, 15);
    }

    /**
     * Excel表格导出
     *
     * @param response    HttpServletResponse对象
     * @param excelData   Excel表格的数据
     * @param sheetName   sheet的名字
     * @param fileName    导出Excel的文件名
     * @param columnWidth Excel表格的宽度，建议为15
     * @param cellsName   Excel首行名称
     * @throws IOException 抛IO异常
     */
    public static <T> void exportExcel(HttpServletResponse response,
                                       List<T> excelData,
                                       String sheetName,
                                       String fileName,
                                       String[] cellsName,
                                       int columnWidth) throws IOException {

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //设置表格列宽度
        sheet.setDefaultColumnWidth(columnWidth);

        int rowIndex = 0;
        T object = excelData.get(0);
        Field[] fields = object.getClass().getDeclaredFields();
        // 创建标题行
        HSSFRow row = sheet.createRow(rowIndex++);
        if (cellsName != null) {
            for (int i = 0; i < cellsName.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(cellsName[i]);
                cell.setCellValue(text);
            }
        } else {
            for (int i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                fields[i].setAccessible(true);
                HSSFRichTextString text = new HSSFRichTextString(fields[i].getName());
                cell.setCellValue(text);
            }
        }
        // 填充数据
        for (T data : excelData) {
            //创建一个row行，然后自增1
            row = sheet.createRow(rowIndex++);
            Field[] fieldsList = data.getClass().getDeclaredFields();
            //遍历添加本行数据
            for (int i = 0; i < fieldsList.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = null;
                fieldsList[i].setAccessible(true);
                try {
                    Object o = fieldsList[i].get(data);
                    String s = "";
                    if (o != null) {
                        s = o.toString();
                    }
                    text = new HSSFRichTextString(s == null ? "" : s);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                cell.setCellValue(text);
            }
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream;charset=utf-8");
        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());
        //关闭workbook
        workbook.close();
    }

    /**
     * 将数据写入文本文件
     *
     * @param list
     * @param path
     */
    public static void writeToTxt(List list, String path) {
        File parent = new File(path);
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        String enter = "\r\n";
        StringBuffer write;
        try {
            Date date = new Date();
            outSTr = new FileOutputStream(new File(path+"/"+date.getTime()+".txt"));
            Buff = new BufferedOutputStream(outSTr);
            for (int i = 0; i < list.size(); i++) {
                write = new StringBuffer();
                write.append(list.get(i));
                write.append(enter);
                Buff.write(write.toString().getBytes("UTF-8"));
            }
            Buff.flush();
            Buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Buff.close();
                outSTr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
