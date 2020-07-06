package hn.cch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字节数组工具类
 */
public class BytesUtil {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 将十六进制字符串转化为字节数组
     * @param string
     * @return
     */
    public static byte[] fromHex(String string) {

        int length = string.length();
        if (length % 2 != 0){
            logger.error("length odd even");
            return null;
        }
        byte[] bytes = new byte[length / 2];
        for (int i = 0, j = 0; i <  length; i+=2,j++) {
            String str = string.substring(i, i+2);
            // byte b = Byte.parseByte(str, 16);
            byte b = (byte) Integer.parseInt(str, 16);
            bytes[j] = b;
        }
        return bytes;
    }

    /**
     * 将字节数组转化十六进制字符串
     * @param bytes
     * @return
     */
    public static String toHex(byte[] bytes) {

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b).toUpperCase();
            //两位大写|不足填零
            String str = hex.length() == 1 ? "0" + hex : hex.substring(hex.length() - 2);
            buffer.append(str);
        }
        return buffer.toString();
    }


}
