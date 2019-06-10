package cn.company.common.utils;

import cn.company.common.annotation.pdf.PdfTitle;
import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lww
 * @Date: 2018/10/25 14:24
 */
public class PdfUtils {
    /**
     * 导出PDF表格
     * @param response
     * @param data
     * @param clazz
     */
    public static void exportPdfForm(HttpServletResponse response, List data, Class clazz, String fileName) throws Exception{
        response.setHeader("content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8")+".pdf");
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        Document document = new Document();
        OutputStream outputStream = response.getOutputStream();
        PdfWriter.getInstance(document,outputStream);
        document.open();

        /*获取该实体类的变量数组*/
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> titleNameMap = new LinkedHashMap<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PdfTitle.class)) {
                PdfTitle pdfTitle = field.getAnnotation(PdfTitle.class);
                if (pdfTitle.isInput()) {
                    titleNameMap.put(field.getName(), pdfTitle.titleName());
                }
            }
        }
        //写入title
        PdfPTable titleTable = new PdfPTable(titleNameMap.size());
        for (Map.Entry entry : titleNameMap.entrySet()) {
            PdfPCell titleCell = new PdfPCell();
            String titleKey = String.valueOf(entry.getKey());
            Object titleValue = titleNameMap.get(titleKey);
            titleCell.setPhrase(new Paragraph(titleValue.toString(),FontChinese));
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(titleCell);
            document.add(titleTable);
        }

        //写入每行表单数据
        for (Object rowData : data) {
            Map<String, Object> map = JsonUtils.jsonToMap(JsonUtils.objectToJson(rowData));
            PdfPTable rowTable = new PdfPTable(map.size());
            for (Map.Entry entry : map.entrySet()) {
                PdfPCell rowCell = new PdfPCell();
                Object value = map.get(String.valueOf(entry.getKey()));
                rowCell.setPhrase(new Paragraph(value.toString(),FontChinese));
                rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                rowTable.addCell(rowCell);
                document.add(rowTable);
            }
        }
        document.close();
        outputStream.close();
    }

    /**
     * 导出PDF文字
     * @param response
     * @param data
     * @param fileName
     */
    public static void exportPdfText(HttpServletResponse response, String data, String fileName) throws Exception{
        response.setHeader("content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8")+".pdf");
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        Document document = new Document();
        OutputStream outputStream = response.getOutputStream();
        PdfWriter.getInstance(document,outputStream);
        document.open();
        Paragraph paragraph = new Paragraph(data,FontChinese);
        document.add(paragraph);
        document.close();
        outputStream.close();
    }

    /**
     * HTML 转 PDF
     * @param response
     * @param htmlPath HTML文件路径
     * @param fileName 生成的PDF文件名
     * @throws Exception
     */
    public static void htmlToPdf(HttpServletResponse response, String htmlPath, String fileName) throws Exception{
        response.setHeader("content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8")+".pdf");
        Document document = new Document();
        StyleSheet st = new StyleSheet();
        st.loadTagStyle("body", "leading", "16,0");
        OutputStream outputStream = response.getOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        List p = HTMLWorker.parseToList(new FileReader(htmlPath), st);
        for(int k = 0; k < p.size(); ++k) {
            document.add((Element)p.get(k));
        }
        document.close();
        outputStream.close();
    }

    /**
     * 图片转PDF
     * @param response
     * @param fileName PDF文件名字
     * @param imgPath 图片路径
     * @throws Exception
     */
    public static void imgToPdf(HttpServletResponse response, String fileName, String imgPath) throws Exception{
        response.setHeader("content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8")+".pdf");
        Document document = new Document();
        OutputStream outputStream = response.getOutputStream();
        PdfWriter.getInstance(document,outputStream);
        document.open();
        Image image = Image.getInstance(imgPath);
        document.add(image);
        document.close();
        outputStream.close();
    }
}
