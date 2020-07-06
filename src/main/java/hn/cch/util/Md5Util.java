package hn.cch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 * 消息摘要算法(第五版)
 * Message Digest Algorithm
 */
public class Md5Util {
    private static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    private static String algorithm = "MD5";

    public static String digest(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);//MD5算法
            byte[] bytes = md.digest(StringUtil.toBytes(input));//MD5加密
            String output = BytesUtil.toHex(bytes);//将字节数组转化为十六进制字符串
            return output;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
