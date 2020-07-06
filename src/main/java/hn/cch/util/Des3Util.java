package hn.cch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Des3Util {

    private static Logger logger = LoggerFactory.getLogger(Des3Util.class);
    /**
     * 数据加密标准：
     * DES
     * <p>
     * DESede
     * <p>
     * ASE
     * <p>
     * DESede/ECB/PKCS5Padding
     */

//    private static final String algorithm = "DES";
    private static final String algorithm = "DESede";
//    private static final String algorithm = "AES";


    // public static final byte[] key = key();

    public static final byte[] key = BytesUtil.fromHex("7629B5BCE9D99B809D2C7FB02F45A87CC798F25252C85473");

    public static byte[] key() {
        byte[] key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            SecretKey secretKey = keyGenerator.generateKey();
            logger.info("Key algorithm : " + secretKey.getAlgorithm());
            key = secretKey.getEncoded();
            logger.info("Key Length : " + key.length);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }

    public enum CRYPT {
        EN,//加密
        DE//解密
    }

    /**
     * @param crypt 1：加密  2：解密
     * @param data  数据
     * @param key   密钥
     * @return
     */
    public static byte[] crypt(CRYPT crypt, byte[] data, byte[] key) {
        byte[] temp = null;
        try {
            // 密钥加算法
            SecretKey secretKey = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            // 加密或解密
            switch (crypt) {
                case EN:
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    break;
                case DE:
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    break;
                default:
                    logger.error("crypt error : CRYPT.EN|CRYPT.DE");
            }
            // 数据处理
            temp = cipher.doFinal(data);
       // 异常处理
        } catch (InvalidKeyException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (BadPaddingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return temp;
    }


}
