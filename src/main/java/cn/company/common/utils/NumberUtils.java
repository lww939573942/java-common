package cn.company.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author donaldhan
 */
public class NumberUtils {

    /**
     * 除法 保留两位小数点  进一位
     *
     * @param a
     * @param b
     *
     * @return
     */
    public static BigDecimal divideKeepTwoDecimalUp(BigDecimal a, BigDecimal b) {
        return a.divide(b, 2, RoundingMode.UP);
    }

    /**
     * 除法 保留两位小数点 四舍五入
     *
     * @param a
     * @param b
     *
     * @return
     */
    public static BigDecimal divideKeepTwoDecimalHalfEven(BigDecimal a, BigDecimal b) {
        return a.divide(b, 2, RoundingMode.HALF_EVEN);
    }

    /**
     * 除法取整
     *
     * @param a
     * @param b
     *
     * @return
     */
    public static BigDecimal divideForInt(BigDecimal a, BigDecimal b) {
        return new BigDecimal((a.divide(b)).intValue());
    }

    public static BigDecimal dealDecimalSubDecimal(BigDecimal d, int decimal) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(decimal);
        decimalFormat.setGroupingSize(0);
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return new BigDecimal(String.valueOf(decimalFormat.format(d)));
    }

    public static BigDecimal dealDecimalSubDecimal(BigDecimal d) {
        return dealDecimalSubDecimal(d, 2);
    }

    /**
     * 保留两位的四舍五入
     *
     * @param d
     *
     * @return
     */
    public static BigDecimal keepTwoDecimalHalfEven(BigDecimal d) {
        return d.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * 多个数值累加。
     *
     * @param args 要累加的数
     *
     * @return Double 结果
     */
    public static BigDecimal addAll(BigDecimal... args) {
        if (args == null || args.length == 0) {
            throw new RuntimeException("加法运算参数不能为空！");
        }
        BigDecimal result = new BigDecimal(0);
        for (int i = 0; i < args.length; i++) {
            result = result.add(args[i]);
        }
        return result;
    }

    /**
     * 去掉末尾的0
     *
     * @param number 金额
     *
     * @return BigDecimal
     */
    public static BigDecimal stripTrailingZeros(BigDecimal number) {
        return number.stripTrailingZeros();
    }
}
