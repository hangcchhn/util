package hn.cch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * 字节流
 */
public class StreamUtil {

    private static Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static InputStream toInput(File file){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("File Input Stream Error");
            return null;
        }
    }


    public static InputStream toInput(String string){

        byte[] bytes = StringUtil.toBytes(string);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return byteArrayInputStream;

    }

    /**
     * 文件字节流：写
     * @param file 文件
     * @param append 追加
     * @return
     */
    public static OutputStream toOutput(File file, boolean append){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
            return fileOutputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("File Output Stream Error");
            return null;
        }
    }


//    public static File fromOutput(OutputStream outputStream){
//
//    }
//
//    public static String fromOutput(OutputStream outputStream){
//
//    }
    
    public static void main(String[] args) {
        
    }


}
