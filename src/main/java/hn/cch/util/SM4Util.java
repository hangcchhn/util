package hn.cch.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

public class SM4Util {

    private static Logger logger = LoggerFactory.getLogger(SM4Util.class);

    private static final String algorithm = "SM4";
    private static final int mode = 1;
    private static final int size = 128;

    public enum CRYPT {
        EN,//加密
        DE//解密
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] key() throws NoSuchPaddingException {
        byte[] key = null;
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
            keyGenerator.init(size, new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            logger.info("Key algorithm : " + secretKey.getAlgorithm());
            key = secretKey.getEncoded();
            logger.info("Key Length : " + key.length);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return key;
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

            Cipher cipher = Cipher.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
            cipher.init(mode, secretKeySpec);
            // 加密或解密
            switch (crypt) {
                case EN:
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                    break;
                case DE:
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
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
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return temp;
    }


    public static void main(String[] args) {

        String json = "{\"key\":\"value\",\"date_time\":\"2020-06-26 12:30:00\"}";
        String key = "0123456789abcdef";

        logger.info("bytes:{}", Arrays.toString(StringUtil.toBytes(key)));

        byte[] data = SM4Util.crypt(CRYPT.EN, StringUtil.toBytes(json), StringUtil.toBytes(key));
        logger.info("byte hex:{}", BytesUtil.toHex(data));
        data = BytesUtil.fromHex("86EA1D791B657B29843C10FF49D84021D4C706C02889BB479B1A26E9EBDF0A0418494A7F2741784CA5E83FD159EA99143B7D8E57EE37C650082ACA153C15BA35");
        //data = BytesUtil.fromHex("86EA1D791B657B29843C10FF49D84021D4C706C02889BB479B1A26E9EBDF0A0418494A7F2741784CA5E83FD159EA99149C2F823147BE58ACA82823894DB5BB5B");

        logger.info("byte hex:{}", BytesUtil.toHex(data));


        // System.out.println(Sm4Utils.verifyEcb(key, cipher, json));// true

        byte[] temp = SM4Util.crypt(CRYPT.DE, data, StringUtil.toBytes(key));

        logger.info("byte len:{}", StringUtil.fromBytes(temp).length());
        logger.info("byte hex:{}", StringUtil.fromBytes(temp));

    }
}
