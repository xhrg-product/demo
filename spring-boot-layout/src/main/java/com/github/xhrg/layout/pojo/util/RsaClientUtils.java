package com.github.xhrg.layout.pojo.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * 这个类仅仅是给 UserCenterClient.java使用的。 Rsa加密解密请使用 RsaUtils.java
 */
public class RsaClientUtils {

    /**
     * 非对称密钥算法
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCidgwAP4UvGfZG2TAfS7ookIj/zltg0t3zwgq8qIws7veg8aN4kU8E4Xc+KKefeJ7zcFp5G9Y4s0V7uhgKaZCzotPbqwnNj24qoNQfLkUXfwZSIGEsRFEP1to5b8eFo5IQu8OEt8w4Tz4TgZaXyoLPoMLQYLoud07VlxewrfZdvQIDAQAB";

    /**
     * 公钥解密
     *
     * @param decryptStr 待解密数据
     * @return byte[] 解密数据
     */
    public static String decryptByPublicKey(String decryptStr) throws Exception {

        byte[] data = Base64.decodeBase64(decryptStr);

        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 初始化公钥
        // 密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(PUBLIC_KEY));
        // 产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return new String(cipher.doFinal(data));
    }

    /**
     * 公钥加密
     *
     * @param encryptStr 待加密数据
     * @return byte[] 解密数据
     */
    public static String encryptByPublicKey(String encryptStr) {

        byte[] data = encryptStr.getBytes();
        // 实例化密钥工厂
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 初始化公钥
            // 密钥材料转换
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(PUBLIC_KEY));
            // 产生公钥
            PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
            // 数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.encodeBase64String(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 加密认证信息
     *
     * @param userName 用户名
     * @param password 密码
     * @return 加密后的认证信息
     * @throws
     */
    public static String encryptAuthInfo(String userName, String password) {

        String str = "{" + "    \"password\":\"" + password + "\"," + "    \"userName\":\"" + userName + "\","
                + "    \"timestamp\":\"" + System.currentTimeMillis() + "\"" + "}";

        return encryptByPublicKey(str);

    }

    public static void main(String[] args) {
        System.out.println(RsaClientUtils.encryptAuthInfo("zhangjinhuia", ""));
    }

}
