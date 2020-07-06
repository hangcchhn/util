package hn.cch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 读取文件输入流数据返回字节数组
     * @param inputStream
     * @return
     */
    public static byte[] toBytes(InputStream inputStream) {
        
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("file read error : " + e.getMessage());
            return null;
        }
    }

    /**
     * 将数据写入文件中：文件不存在会自动创建
     * @param outputStream
     * @param bytes
     */
    public static void fromBytes(OutputStream outputStream, byte[] bytes) {

        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("file write error : " + e.getMessage());
            return;
        }
    }



    


    /**
     * 获取包含的文件
     * @param file 必须存在而且是文件夹（否则返回空）
     * @return
     */
    public static File[] getFiles(File file) {

        if (!file.exists()){
            logger.error("config error : " + file.getPath() + "is not exists");
            return null;
        }
        if (!file.isDirectory()){
            logger.error("config error : " + file.getPath() + "is not directory");
            return null;
        }

        File[] files = file.listFiles();
        return files;
    }



}
