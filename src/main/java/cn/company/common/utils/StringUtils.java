package cn.company.common.utils;

import jodd.util.RandomString;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author donaldhan
 */
public final class StringUtils {
    private final static Logger logger = LoggerFactory.getLogger(StringUtils.class);
    static final Pattern URL_PATTERN = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
    static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
    static final Pattern TWO_POINT_FLOAT = Pattern.compile("^[0-9]+([.][0-9]{1,2})?$");

    /**
     * 判断是否是合法有效的URL地址
     */
    public static boolean isUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }

    public static String[] split(String src, String delimiter) {
        return StringUtil.split(src, delimiter);
    }

    public static String capitalize(String str) {
        return StringUtil.capitalize(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        return StringUtil.isBlank(str);
    }

    public static boolean checkEmail(String s) {
        String regex = "[\\w_]+@\\w+(\\.\\w+)+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 手机号验证
     *
     * @param str
     *
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     *
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equals(str2);
    }

    public static String md5(String str) {
        try {
            return DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("md5加密异常",e);
        }
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String getRandomStr(int count) {
        return RandomString.get().randomAlphaNumeric(count);
    }

    /**
     * 去掉前缀
     *
     * @param str
     * @param prefix
     *
     * @return
     */
    public static String removePrefix(String str, String prefix) {
        while (str.startsWith(prefix)) {
            str = str.substring(1);
        }
        return str;
    }

    public static boolean isPositiveIntegral(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        // 验证正整数
        p = Pattern.compile("[^1-9]/D*$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isNumeric(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

    public static boolean isTwoPointFloat(String str) {
        return TWO_POINT_FLOAT.matcher(str).matches();
    }
}
