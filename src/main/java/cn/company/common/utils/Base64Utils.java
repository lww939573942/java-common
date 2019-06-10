package cn.company.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64加密解密
 *
 * @author donaldhan
 */
public class Base64Utils {

    /**
     * 加密
     */
    public static String encoder(String data) throws UnsupportedEncodingException {
        Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = data.getBytes("UTF-8");

        return encoder.encodeToString(textByte);
    }

    /**
     * 解密
     */
    public static String decoder(String data) throws UnsupportedEncodingException {
        Base64.Decoder decoder = Base64.getDecoder();

        return new String(decoder.decode(data), "UTF-8");
    }
}
