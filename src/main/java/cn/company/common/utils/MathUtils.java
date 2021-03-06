package cn.company.common.utils;

import java.math.BigDecimal;

/**
 * @author donaldhan
 */
public class MathUtils {

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1 第一点经度
     * @param lat1 第一点纬度
     * @param long2 第二点经度
     * @param lat2 第二点纬度
     *
     * @return 返回距离 单位：米
     */
    public static Double distance(Double long1, Double lat1, Double long2, Double lat2) {
        if (null == long1 || null == lat1 || null == long2 || null == lat2) {
            return null;
        }
        double a, b, R;
        // 地球半径
        R = 6378137;
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));

        return d;
    }


    /**
     * 功能：将字符串转换为BigDecimal，一般用于数字运算时。
     *
     * @param str 字符串
     *
     * @return BigDecimal, str为empty时返回null。
     */
    public static BigDecimal toBigDecimal(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return new BigDecimal(str);
    }

    /**
     * 功能：将字符串抓换为double，如果失败返回默认值。
     *
     * @param str 字符串
     * @param defaultValue 失败时返回的默认值
     *
     * @return double
     */
    public static double toDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 功能：将字符串抓换为float，如果失败返回默认值。
     *
     * @param str 字符串
     * @param defaultValue 失败时返回的默认值
     *
     * @return float
     */
    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 功能：将字符串抓换为long，如果失败返回默认值。
     *
     * @param str 字符串
     * @param defaultValue 失败时返回的默认值
     *
     * @return long
     */
    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 功能：将字符串抓换为int，如果失败返回默认值。
     *
     * @param str 字符串
     * @param defaultValue 失败时返回的默认值
     *
     * @return int
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * 得到两个 <code>double</code>值中最大的一个.
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     *
     * @return 最大的值
     */
    public static float getMax(float a, float b) {
        if (Float.isNaN(a)) {
            return b;
        } else if (Float.isNaN(b)) {
            return a;
        } else {
            return Math.max(a, b);
        }
    }

    /**
     * <p>
     * 得到数组中最大的一个.
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     *
     * @return 得到数组中最大的一个.
     *
     * @throws IllegalArgumentException 如果 <code>数组</code> 是 <code>null</code>
     * @throws IllegalArgumentException 如果 <code>数组</code>是空
     */
    public static float getMax(float[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        float max = array[0];
        for (int j = 1; j < array.length; j++) {
            max = getMax(array[j], max);
        }

        return max;
    }

    /**
     * <p>
     * 得到数组中最大的一个.
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     *
     * @return 得到数组中最大的一个.
     *
     * @throws IllegalArgumentException 如果 <code>数组</code> 是 <code>null</code>
     * @throws IllegalArgumentException 如果 <code>数组</code>是空
     */
    public static double getMax(double[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        double max = array[0];
        for (int j = 1; j < array.length; j++) {
            max = getMax(array[j], max);
        }

        return max;
    }

    /**
     * <p>
     * 得到两个 <code>double</code>值中最大的一个.
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     *
     * @return 最大的值
     */
    public static double getMax(double a, double b) {
        if (Double.isNaN(a)) {
            return b;
        } else if (Double.isNaN(b)) {
            return a;
        } else {
            return Math.max(a, b);
        }
    }

    /**
     * <p>
     * 得到两个float中最小的一个。
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     *
     * @return double值最小的
     */
    public static float getMin(float a, float b) {
        if (Float.isNaN(a)) {
            return b;
        } else if (Float.isNaN(b)) {
            return a;
        } else {
            return Math.min(a, b);
        }
    }

    /**
     * <p>
     * 返回数组中最小的数值。
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     *
     * @return 数组里面最小的float
     *
     * @throws IllegalArgumentException 如果<code>数组</code>是<code>null</code>
     * @throws IllegalArgumentException 如果<code>数组</code>是空
     */
    public static float getMin(float[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("数组不能为null。");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("数组不能为空。");
        }

        // Finds and returns min
        float min = array[0];
        for (int i = 1; i < array.length; i++) {
            min = getMin(array[i], min);
        }

        return min;
    }

    /**
     * <p>
     * 返回数组中最小的double。
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     *
     * @return 数组里面最小的double
     *
     * @throws IllegalArgumentException 如果<code>数组</code>是<code>null</code>
     * @throws IllegalArgumentException 如果<code>数组</code>是空
     */
    public static double getMin(double[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("数组不能为null。");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("数组不能为空。");
        }
        // Finds and returns min
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            min = getMin(array[i], min);
        }
        return min;
    }

    /**
     * <p>
     * 得到两个double中最小的一个。
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     *
     * @return double值最小的
     */
    public static double getMin(double a, double b) {
        if (Double.isNaN(a)) {
            return b;
        } else if (Double.isNaN(b)) {
            return a;
        } else {
            return Math.min(a, b);
        }
    }

    /**
     * 返回两个double的商 first除以second。
     *
     * @param first 第一个double
     * @param second 第二个double
     *
     * @return double
     */
    public static double divideDouble(double first, double second) {
        BigDecimal b1 = BigDecimal.valueOf(first);
        BigDecimal b2 = BigDecimal.valueOf(second);
        return b1.divide(b2).doubleValue();
    }

    /**
     * 返回两个double的乘积 first*second。
     *
     * @param first 第一个double
     * @param second 第二个double
     *
     * @return double
     */
    public static double multiplyDouble(double first, double second) {
        BigDecimal b1 = BigDecimal.valueOf(first);
        BigDecimal b2 = BigDecimal.valueOf(second);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 返回两个double的差值 first-second。
     *
     * @param first 第一个double
     * @param second 第二个double
     *
     * @return double
     */
    public static double subtractDouble(double first, double second) {
        BigDecimal b1 = BigDecimal.valueOf(first);
        BigDecimal b2 = BigDecimal.valueOf(second);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 返回两个double的和值 first+second。
     *
     * @param first 第一个double
     * @param second 第二个double
     *
     * @return double
     */
    public static double sumDouble(double first, double second) {
        BigDecimal b1 = BigDecimal.valueOf(first);
        BigDecimal b2 = BigDecimal.valueOf(second);
        return b1.add(b2).doubleValue();
    }

    /**
     * 格式化double指定位数小数。例如将11.123格式化为11.1。
     *
     * @param value 原double数字。
     * @param decimals 小数位数。
     *
     * @return 格式化后的double，注意为硬格式化不存在四舍五入。
     */
    public static String formatDouble(double value, int decimals) {
        String doubleStr = "" + value;
        int index = doubleStr.indexOf(".") != -1 ? doubleStr.indexOf(".")
                : doubleStr.indexOf(",");
        // Decimal point can not be found...
        if (index == -1) {
            return doubleStr;
        }
        // Truncate all decimals
        if (decimals == 0) {
            return doubleStr.substring(0, index);
        }
        int len = index + decimals + 1;
        if (len >= doubleStr.length()) {
            len = doubleStr.length();
        }
        double d = Double.parseDouble(doubleStr.substring(0, len));
        return String.valueOf(d);
    }

    /**
     * 生成一个指定位数的随机数，并将其转换为字符串作为函数的返回值。
     *
     * @param numberLength 随机数的位数。
     *
     * @return String 注意随机数可能以0开头。
     */
    public static String randomNumber(int numberLength) {
        // 记录生成的每一位随机数
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numberLength; i++) {
            // 每次生成一位,随机生成一个0-10之间的随机数,不含10。
            Double ranDouble = Math.floor(Math.random() * 10);
            sb.append(ranDouble.intValue());
        }
        return sb.toString();
    }

    /**
     * 功能：生成一个在最大数和最小数之间的随机数。会出现最小数，但不会出现最大数。
     *
     * @param minNum 最小数
     * @param maxNum 最大数
     *
     * @return int
     */
    public static int randomNumber(int minNum, int maxNum) {
        if (maxNum <= minNum) {
            throw new RuntimeException("maxNum必须大于minNum!");
        }
        // 计算出来差值
        int subtract = maxNum - minNum;
        Double ranDouble = Math.floor(Math.random() * subtract);
        return ranDouble.intValue() + minNum;
    }

    /**
     * 功能：生成一个在最大数和最小数之间的随机数。会出现最小数，但不会出现最大数。<br/>
     * 但不随机notin数组中指定的数字， 如果可随机的范围较小，可能会一直随机不到，或者随机的很慢。
     *
     * @param minNum 最小数
     * @param maxNum 最大数
     * @param notin 不随机数组这些数字
     *
     * @return int
     */
    public static int randomNumber(int minNum, int maxNum, Integer[] notin) {
        if (notin.length >= (maxNum - minNum)) {
            throw new RuntimeException("notin数组的元素已经把可以随机的都排除了，无法得到随机数!");
        }
        while (true) {
            int num = randomNumber(minNum, maxNum);
            if (!CollectionUtil.arrayContain(notin, num)) {
                return num;
            }
        }
    }
}
