package cn.company.common.utils;

import cn.company.common.annotation.excel.ExcelTitle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author linjunpeng
 * @date 2018/9/13
 */
public class ExcelUtils {
    /**
     * 导出单表单sheet数据（导出的实体类的变量需加@ExcelTitle注解）
     *
     * @param response 返回
     * @param fileName 文件名
     * @param data 要导出的数据的实体类
     * @param clazz 实体类的类型
     * @param exportType 导出类型 0-xls 1-xlsx
     */
    public static void exportExcel(HttpServletResponse response, String fileName, List data, Class clazz, Integer exportType) throws IOException {
        Workbook wb;
        Sheet sheet;
        OutputStream outputStream = response.getOutputStream();

        if (exportType == 1) {
            fileName = fileName + "" + ".xlsx";
            wb = new XSSFWorkbook();
        } else {
            fileName = fileName + "" + ".xls";
            wb = new HSSFWorkbook();
        }
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

        sheet = wb.createSheet(fileName);
        writeExcel(wb, sheet, data, clazz);
        wb.write(outputStream);
        outputStream.close();
        wb.close();
    }

    /**
     * 单表多sheet 导出（导出的实体类的变量需加@ExcelTitle注解）
     *
     * @param response 返回
     * @param fileName 要导出的文件名
     * @param data <标签名称，List数据> 确保list数据的实体都来自同一个类
     * @param exportType 导出类型 0-xls 1-xlsx
     */
    public static void exportMoreSheetExcel(HttpServletResponse response, String fileName, Map<String, List> data, Integer exportType) throws IOException {
        Workbook wb;
        Sheet sheet;
        OutputStream outputStream = response.getOutputStream();

        if (exportType == 0) {
            fileName = fileName + "" + ".xls";
            wb = new HSSFWorkbook();
        } else {
            fileName = fileName + "" + ".xlsx";
            wb = new XSSFWorkbook();
        }
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

        for (Map.Entry<String, List> entry : data.entrySet()) {
            sheet = wb.createSheet(entry.getKey());
            /*获取实体类的类型*/
            writeExcel(wb, sheet, entry.getValue(), entry.getValue().get(0).getClass());
        }

        wb.write(outputStream);
        outputStream.close();
        wb.close();
    }

    /**
     * 获取导入excel 某个sheet的数据
     *
     * @param fileName 导入的文件名
     * @param excelInputStream 文件数据
     * @param sheetAt 要打开得sheet索引
     * @param rowIndex 从第几行开始读 索引
     * @param clazz 要将数据映射的类
     *
     * @return
     */
    public static Map<String, Object> importExcel(String fileName, InputStream excelInputStream, Integer sheetAt, Integer rowIndex, Class clazz) throws Exception {
        final String xlsxMatches = "^.+\\.(?i)(xlsx)$";
        List<Object> excelDataList = new ArrayList<>();
        Workbook workbook;

        if (fileName.matches(xlsxMatches)) {
            workbook = new XSSFWorkbook(excelInputStream);
        } else {
            workbook = new HSSFWorkbook(excelInputStream);
        }

        /*获取指定的标签页*/
        Sheet sheet = workbook.getSheetAt(sheetAt);
        /*从指定行数读取数据*/
        for (int i = rowIndex; i <= sheet.getLastRowNum(); i++) {
            /*空行数据标记*/
            Integer nullCount = 0;
            Row row = sheet.getRow(i);
            if (!ValidateUtils.isEmpty(row)) {
                Object object = clazz.newInstance();
                Field[] fields = object.getClass().getDeclaredFields();
                /*按顺序给对象赋值*/
                for (int c = 0; c < fields.length; c++) {
                    Field field = fields[c];
                    field.setAccessible(true);
                    Cell cell = row.getCell(c);
                    if (!ValidateUtils.isEmpty(cell)) {
                        String str;
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                /*Date类型则转化为Data格式*/
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    SimpleDateFormat sdf = null;
                                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                                        sdf = new SimpleDateFormat("HH:mm");
                                    } else {
                                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    }
                                    Date date = cell.getDateCellValue();
                                    str = sdf.format(date);
                                } else {
                                    DataFormatter dataFormatter = new DataFormatter();
                                    str = dataFormatter.formatCellValue(cell);
                                }
                                break;
                            case STRING:
                                str = cell.getRichStringCellValue().getString();
                                break;
                            case ERROR:
                            case BLANK:
                                str = "";
                                break;
                            case BOOLEAN:
                                str = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                str = String.valueOf(cell.getCellFormula());
                                break;
                            default:
                                str = "";
                                break;
                        }
                        if (str.getClass().equals(field.getType()) && !"".equals(str.trim())) {
                            field.set(object, str);
                            nullCount++;
                        }
                    }
                }
                /*存在非空数据可以创建对象*/
                if (nullCount > 0) {
                    excelDataList.add(object);
                }
            }
        }

        Map<String, Object> reMap = new LinkedHashMap<>(2);
        /*识别总行数*/
        reMap.put("rowTotal", sheet.getLastRowNum());
        /*有效行数(非空)*/
        reMap.put("rowNum", excelDataList.size());
        reMap.put("data", excelDataList);

        return reMap;
    }

    /**
     * 写入excel
     */
    private static <T> void writeExcel(Workbook workbook, Sheet sheet, List data, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> titleNameMap = new LinkedHashMap<>();

        /*将所有需要导出的字段加入到集合里*/
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelTitle.class)) {
                ExcelTitle excelTitle = field.getAnnotation(ExcelTitle.class);
                if (excelTitle.isInput()) {
                    /*方法名做键值 标题做value*/
                    titleNameMap.put(field.getName(), excelTitle.titleName());
                }
            }
        }

        writeTitleExcel(workbook, sheet, titleNameMap);
        writeRowExcel(workbook, sheet, data, titleNameMap);
        autoSizeColumns(sheet, titleNameMap.size() + 1);
    }

    /**
     * 写入excel标题
     */
    private static void writeTitleExcel(Workbook workbook, Sheet sheet, Map<String, String> titleNameMap) {
        /*创建标题字体格式*/
        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setColor(IndexedColors.BLACK.index);
        titleFont.setFontHeightInPoints((short) 11);
        titleFont.setBold(true);

        /*设置单元格格式*/
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        titleStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle);

        Row titleRow = sheet.createRow(0);
        titleRow.setHeight((short) (30 * 12));

        int colIndex = 0;
        for (Map.Entry entry : titleNameMap.entrySet()) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(String.valueOf(entry.getValue()));
            cell.setCellStyle(titleStyle);
            colIndex++;
        }
    }

    /**
     * 写入数据data
     */
    private static void writeRowExcel(Workbook workbook, Sheet sheet, List data, Map<String, String> titleNameMap) {
        int rowIndex = 1;
        int colIndex = 0;

        /*设置数据字体*/
        Font dataFont = workbook.createFont();
        dataFont.setFontName("simsun");
        dataFont.setColor(IndexedColors.BLACK.index);
        dataFont.setFontHeightInPoints((short) 11);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle);

        for (Object rowData : data) {
            Row dataRow = sheet.createRow(rowIndex);
            dataRow.setHeight((short) (35 * 10));
            colIndex = 0;
            Map<String, Object> rowDateMap = JsonUtils.jsonToMap(JsonUtils.objectToJson(rowData));

            Field[] fields = rowData.getClass().getDeclaredFields();
            for (Map.Entry entry : titleNameMap.entrySet()) {
                Cell cell = dataRow.createCell(colIndex);
                String key = String.valueOf(entry.getKey());
                Object value = rowDateMap.get(key);
                if (ValidateUtils.isNotEmpty(value)) {
                    cell.setCellValue(value.toString());
                } else {
                    for (Field field : fields) {
                        String fieldName = field.getName();
                        if (fieldName.equals(key)) {
                            if (field.isAnnotationPresent(ExcelTitle.class)) {
                                ExcelTitle excelTitle = field.getAnnotation(ExcelTitle.class);
                                String defaultValue = excelTitle.defaultValue();
                                cell.setCellValue(defaultValue);

                            }
                        }
                    }
                }
                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;

        }
    }

    private static void setBorder(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
    }

    /**
     * 自适应宽度
     */
    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (sheet.getColumnWidth(i) + 4800);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }
}
