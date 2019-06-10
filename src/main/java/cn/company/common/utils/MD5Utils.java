package cn.company.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 *
 * @author donaldhan
 */
public class MD5Utils {
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * MD5编码
     *
     * @param contentStr 要编码的内容
     *
     * @return MD5编码之后的内容
     */
    public static String encryption(String contentStr) throws NoSuchAlgorithmException {
        byte[] btInput = null;
        try {
            btInput = contentStr.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException("不支持UTF-8字符编码");
        }
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        mdInst.update(btInput);
        byte[] md = mdInst.digest();
        int j = md.length;
        String str = "";
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str += HEX_DIGITS[byte0 >>> 4 & 0xf] + HEX_DIGITS[byte0 & 0xf];
        }

        return str;
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;

        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
