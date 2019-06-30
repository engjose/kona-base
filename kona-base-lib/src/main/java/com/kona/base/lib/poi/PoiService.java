package com.kona.base.lib.poi;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
public interface PoiService {

    /**
     * 生成Excel-Workbook
     *
     * @param list 数据集合
     * @param <T> 泛型
     * @return 返回工作簿
     */
    <T> Workbook getWorkbook(List<T> list, Class clazz, String title) throws Exception;

    /**
     * 创建sheet
     *
     * @param workbook 工作簿
     * @param headers 表头
     * @param sheetName sheet名称
     * @param title 表头
     * @return 返回HssSheet对象
     */
    HSSFSheet getHSSFSheet(HSSFWorkbook workbook, List<String> headers, String sheetName, String title);

    /**
     * 将数据写入到表格中
     *
     * @param sheet sheet
     * @param list 数据列表
     * @param <T> 数据
     * @throws Exception 异常
     */
    <T> void write2HSSFSheet(HSSFSheet sheet, List<T> list) throws Exception;

    /**
     * 获取表头
     *
     * @param clazz 元素
     * @return 返回表头
     */
    List<String> getSheetHeaders(Class clazz);

    /**
     * 导出Excel
     *
     * @param response 返回
     * @param dataList 数据
     */
    <T> void exportExcel(HttpServletResponse response, List<T> dataList, Class clazz);
}
