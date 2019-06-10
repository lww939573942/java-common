package cn.company.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author donaldhan
 */
public class RSAUtils {
    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer, String> genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        Map<Integer, String> keyMap = new HashMap<>();
        //0表示公钥
        keyMap.put(0, publicKeyString);
        //1表示私钥
        keyMap.put(1, privateKeyString);

        return keyMap;
    }

    /**
     * RSA公钥加密
     *
     * @param str 加密字符串
     * @param publicKey 公钥
     *
     * @return String 密文
     *
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String str, String publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        return Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
    }

    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     * @param privateKey 私钥
     *
     * @return 明文
     *
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decrypt(String str, String privateKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        return new String(cipher.doFinal(inputByte));
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data
     * @param privateKey
     *
     * @return
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(byte[] data, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        //解密私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey2);
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data
     * @param publicKey
     * @param sign
     *
     * @return
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifySign(byte[] data, String publicKey, String sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        //解密公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicKey2);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * map参数转key1=value1&key2=value2格式
     *
     * @param map
     *
     * @return
     */
    public static String getParamStr(Map map) {
        Set<String> nameList = map.keySet();
        List<String> listParam = new ArrayList<>();
        for (String param : nameList) {
            if (!param.equals("sign")) {
                listParam.add(param);
            }
        }
        /*
         * 将接收到的参数进行字典排序,根据排序后的参数与参数值进行加密
         */
        Collections.sort(listParam);
        StringBuffer strValue = new StringBuffer();
        for (int i = 0; i < listParam.size(); i++) {
            if (i != listParam.size() - 1) {
                strValue.append(listParam.get(i)).append("=").append(map.get(listParam.get(i))).append("&");
            } else {
                strValue.append(listParam.get(i)).append("=").append(map.get(listParam.get(i)));
            }
        }
        return strValue.toString();
    }


    /**
     * 对参数进行加签
     *
     * @param request
     * @param request
     *
     * @return
     *
     */
    public static String getParamStr(HttpServletRequest request) {
        Enumeration<String> nameList = request.getParameterNames();
        List<String> listParam = new ArrayList<>();
        while (nameList.hasMoreElements()) {
            String name = nameList.nextElement();
            if (!name.equals("sign")) {
                listParam.add(name);
            }
        }
        Collections.sort(listParam);
        StringBuffer strValue = new StringBuffer();
        for (int i = 0; i < listParam.size(); i++) {
            if (i != listParam.size() - 1) {
                strValue.append(listParam.get(i)).append("=").append(request.getParameter(listParam.get(i))).append("&");
            } else {
                strValue.append(listParam.get(i)).append("=").append(request.getParameter(listParam.get(i)));
            }
        }
        return strValue.toString();
    }

    /**
     * 私钥格式转换（pkcs8转pkcs1，即java转非java）
     *
     * @param pkcs8
     * @return
     * @throws Exception
     */
    public static String pkcs8ToPkcs1(String pkcs8) throws Exception{
        byte[] pkcs8Bytes = Base64.decodeBase64(pkcs8);
        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(pkcs8Bytes);
        ASN1Encodable asn1Encodable = pkInfo.parsePrivateKey();
        ASN1Primitive primitive = asn1Encodable.toASN1Primitive();
        byte[] privateKeyPKCS1 = primitive.getEncoded();
        return Base64.encodeBase64String(privateKeyPKCS1);
    }

}