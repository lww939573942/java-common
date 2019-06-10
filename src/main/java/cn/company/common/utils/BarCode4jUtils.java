package cn.company.common.utils;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.*;

public class BarCode4jUtils {
    private final static Logger logger = LoggerFactory.getLogger(BarCode4jUtils.class);

    public static String generateBarcode(Long barcode, File outputFile) {
        Code128Bean bean = new Code128Bean();
        final int dpi = 300;

        // 条码两端是否加空白
        bean.doQuietZone(true);
        bean.setFontName("Helvetica");
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            String format = "image/png";
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(bos, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, barcode + "");
            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            logger.error("生成条码异常",e);
        } finally {
            try {
                if(out != null)
                out.close();
            } catch (IOException e) {
                logger.error("输出流关闭异常",e);
            }
        }
        return null;
    }
}