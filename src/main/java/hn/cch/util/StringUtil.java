package hn.cch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 字符串工具类
 */
public class StringUtil {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    //字符集
    private static String charset = "UTF-8";

    /**
     * 按指定编码格式将字符串转化为字节数组
     * @param string
     * @return
     */
    public static byte[] toBytes(String string) {
        try {
            return string.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            logger.error("UnsupportedEncodingException:", e);
            return null;
        }
        
    }

    /**
     * 按指定编码格式将字节数组转化为字符串
     * @param bytes
     * @return
     */
    public static String fromBytes(byte[] bytes) {
        
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            logger.error("UnsupportedEncodingException:", e);
            return null;

        }
    }

}
