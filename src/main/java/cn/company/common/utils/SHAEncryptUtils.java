package cn.company.common.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA加密工具
 *
 * @author donaldhan
 */
public class SHAEncryptUtils {
    /**
     * 对象用SHA-256的方法加密
     */
    public static String encryptBySHA256(Object data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String dataJson = JsonUtils.objectToJson(data);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataJson.getBytes("UTF-8"));

        return Hex.encodeHexString(md.digest());
    }
}
