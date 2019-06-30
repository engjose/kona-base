package com.kona.base.lib.poi;
import com.kona.base.model.anno.ExcelColumnAnno;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
public class PoiServiceImpl implements PoiService {
    /** 从第一行开始 */
    private Integer rowNumber = 0;

    private static final Integer EXCEL_MAX_EXPORT_NUM = 60000;

    @Override
    public <T> Workbook getWorkbook(List<T> list, Class clazz, String title) throws Exception {
        int divide = list.size() / EXCEL_MAX_EXPORT_NUM;
        int mode = list.size() % EXCEL_MAX_EXPORT_NUM;
        int page = mode != 0 ? divide + 1 : divide;
        page = Math.max(1, page);

        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        List<String> headers = getSheetHeaders(clazz);
        for (int i = 1; i <= page; i++) {
            String sheetName = "第" + i + "页";
            HSSFSheet sheet = getHSSFSheet(workbook, headers, sheetName, title);
            int toIndex = Math.min(list.size(), Math.min(i * EXCEL_MAX_EXPORT_NUM, list.size()));
            List<T> sheetData = list.subList((i - 1) * EXCEL_MAX_EXPORT_NUM, toIndex);
            write2HSSFSheet(sheet, sheetData);
        }

        return workbook;
    }

    /**
     * 获取一个带有标题和表头的sheet
     *
     * @param workbook
     * @param headers
     * @param sheetName
     * @return
     */
    @Override
    public HSSFSheet getHSSFSheet(HSSFWorkbook workbook, List<String> headers, String sheetName, String title) {
        HSSFSheet sheet = workbook.createSheet(sheetName);
        setHSSFSheetHeaderStyle(sheet, headers.size(), title);

        //设置标题
        if (StringUtils.isNotBlank(title)) {
            HSSFRow titleRow = sheet.createRow(rowNumber);
            HSSFCell titleCell = titleRow.createCell(rowNumber);
            HSSFCellStyle titleCellStyle = getCellStyle(workbook, (short) 16, true);
            titleCell.setCellStyle(titleCellStyle);
            titleCell.setCellValue(title);
            ++rowNumber;
        }

        //设置表头
        HSSFRow headerRow = sheet.createRow(rowNumber);
        for (int loop = 0; loop < headers.size(); loop++) {
            HSSFCellStyle headerCellStyle = getCellStyle(workbook, (short) 14, false);
            HSSFCell headerCell = headerRow.createCell(loop);
            headerCell.setCellStyle(headerCellStyle);
            headerCell.setCellValue(headers.get(loop));
        }

        return sheet;
    }

    /**
     * 将数据写入指定的sheet中
     *
     * @param sheet
     * @param list
     * @param <T>
     */
    @Override
    public <T> void write2HSSFSheet(HSSFSheet sheet, List<T> list) throws Exception{
        if (!CollectionUtils.isEmpty(list)) {
            for (T instance : list) {
                HSSFRow dataRow = sheet.createRow(++rowNumber);
                Class<?> clazz = instance.getClass();
                Field[] fields = clazz.getDeclaredFields();
                List<HSSFCell> cells = getHSSFCell(dataRow, getExcelColumns(clazz).size());

                for (Field field : fields) {
                    if (field.isAnnotationPresent(ExcelColumnAnno.class)) {
                        ExcelColumnAnno annotation = field.getAnnotation(ExcelColumnAnno.class);
                        int index = annotation.index();
                        String name = field.getName();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                        Method method = clazz.getMethod("get" + name);
                        //目前只支持String,后续需要进行扩展
                        Object invoke = method.invoke(instance);
                        if (invoke != null) {
                            String value = invoke.toString();
                            cells.get(index).setCellValue(value);
                        }
                    }
                }
            }
        }

        //写完一个sheet,变量初始化
        this.rowNumber = 0;
    }

    @Override
    public List<String> getSheetHeaders(Class clazz) {
        if (clazz == null) {
            return null;
        }

        Field[] fields = clazz.getDeclaredFields();
        List<String> headers = Arrays.asList(new String[fields.length]);
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumnAnno.class)) {
                ExcelColumnAnno annotation = field.getAnnotation(ExcelColumnAnno.class);
                int index = annotation.index();
                String name = annotation.name();
                headers.set(index, name);
            }
        }
        return headers;
    }

    @Override
    public <T> void exportExcel(HttpServletResponse response, List<T> dataList, Class clazz) {
        String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS") + ".xls";
        response.setContentType("application/msexcel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 下载文件流输出
        OutputStream os = null;
        try {
            Workbook workbook = getWorkbook(dataList, clazz, null);
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException("下载文件异常");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    /**
     * 获取一行中的Cell数并返回
     *
     * @param row
     * @param length
     * @return
     */
    private List<HSSFCell> getHSSFCell(HSSFRow row, Integer length) {
        List<HSSFCell> cells = new ArrayList<>();
        for (int loop = 0; loop < length; loop++) {
            HSSFCell cell = row.createCell(loop);
            cells.add(cell);
        }
        return cells;
    }

    /**
     * 获取输出Excel的Header
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<String> getExcelColumns(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> columns = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumnAnno.class)) {
                ExcelColumnAnno annotation = field.getAnnotation(ExcelColumnAnno.class);
                int index = annotation.index();
                String name = annotation.name();
                columns.add(index, name);
            }
        }

        return columns;
    }

    /**
     * 设置sheet的样式: 合并第一行Title,并进行冻结sheet标题和表头
     *
     * @param sheet
     * @param headerSize
     */
    private void setHSSFSheetHeaderStyle(HSSFSheet sheet, Integer headerSize, String title) {
        for (int loop = 0; loop < headerSize; loop++) {
            sheet.setColumnWidth(loop, 30 * 256);
        }

        int rowSplit = 1;
        int topRow = 1;
        if (StringUtils.isNotBlank(title)) {
            CellRangeAddress cra =new CellRangeAddress(0, 0, 0, headerSize - 1);
            sheet.addMergedRegion(cra);
            rowSplit = 2;
            topRow = 2;
        }
        sheet.createFreezePane(0, rowSplit, 0, topRow);
    }

    /**
     * 构造Cell样式:主要针对字体,和是否加粗
     *
     * @param workbook
     * @param fontSize
     * @param isBold
     * @return
     */
    private HSSFCellStyle getCellStyle(HSSFWorkbook workbook, short fontSize, Boolean isBold) {
        Font titleFont = workbook.createFont();
        titleFont.setBold(isBold);
        titleFont.setFontHeightInPoints(fontSize);

        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return titleStyle;
    }
}
