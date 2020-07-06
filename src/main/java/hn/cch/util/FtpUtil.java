package hn.cch.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FtpUtil {

    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    //ftp mode :  true(active)|false(passive)
    private static boolean mode = false;

    /**
     * 连接并登陆FTP
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    public static FTPClient openFtp(String host, int port, String username, String password) {

        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(host, port);

            boolean login = ftpClient.login(username, password);
            if (login) {
                logger.info("login Ftp success");
                boolean reply = FTPReply.isPositiveCompletion(ftpClient.getReplyCode());
                if (reply){
                    logger.info("Ftp reply success");

                    //ftp mode
                    if (mode){// 主动模式
                        ftpClient.enterLocalActiveMode();
                    } else {// 被动模式
                        ftpClient.enterLocalPassiveMode();
                    }
                    return ftpClient;
                }else {
                    logger.error("Ftp reply failed");
                    return null;
                }

            } else {
                logger.error("login Ftp failed");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Ftp error : " + e.getMessage());
            return null;
        }

    }


    /**
     * 使用FTP上传文件
     * @param ftpClient
     * @param inputStream 输入流
     * @param remoteFile 绝对路径
     * @return
     */
    public static boolean uploadFile(FTPClient ftpClient, InputStream inputStream, String remoteFile) {
        try {

            //设置文件类型
            if (!ftpClient.setFileType(FTP.BINARY_FILE_TYPE)) {
                logger.error("FTP file error : binary");
                return false;
            }
            //
			String remotePath = remoteFile.substring(0, remoteFile.lastIndexOf("/"));
			String remoteName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
            // 存在就跳转目录路径，不存在就创建并跳转目录路径
            if (!FtpUtil.changePath(ftpClient, remotePath)) {
                logger.error("FTP change path error");
                return false;
            }
			
            // 按绝对路径上传文件
            // if (ftpClient.storeFile(remoteFile, inputStream)) {
            // 按相对路径上传文件
            if (ftpClient.storeFile(remoteName, inputStream)) {
                logger.info("FTP upload success : " + remoteFile);
                return true;
            } else {
                logger.info("FTP upload fail : " + remoteFile);
                return false;
            }


        } catch (IOException e) {
            e.printStackTrace();
            logger.error("FTP upload error : " + e.getMessage());
            return false;
        }
    }


    public static void downloadFile(FTPClient ftpClient, String remoteFile, OutputStream outputStream) {
		
		try {
			if (ftpClient.retrieveFile(remoteFile, outputStream)){
				logger.info("ftp download success");
			}else{
				logger.error("ftp download fail");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 不存在就创建并跳转目录路径
	 *
	 * @param ftpClient
	 * @param remoteDirs
	 * @return
	 */
	public static boolean createDirs(FTPClient ftpClient, String remoteDirs) {
		//
		try {
			
			String[] remoteDires = remoteDirs.split("/");
			for (String remoteDir : remoteDires) {
				// 检查文件夹是否存在
				if (!ftpClient.changeWorkingDirectory(remoteDir)) {
					//空表示当前路径
					if (remoteDir.equals("")) {
						continue;
					}
					if (ftpClient.makeDirectory(remoteDir)){
						if (ftpClient.changeWorkingDirectory(remoteDir)){
							continue;
						}
					}
					logger.error("FTP create dirs fail : " + remoteDir);
					return false;
				}
			}
			logger.error("FTP create dirs success : " + remoteDirs);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("FTP create dirs error : " + e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * 存在就跳转目录路径，不存在就创建并跳转目录路径
	 * @param ftpClient
	 * @param remotePath
	 * @return
	 */
	public static boolean changePath(FTPClient ftpClient, String remotePath){
		try {
			if (!ftpClient.changeWorkingDirectory(remotePath)){
				if (!FtpUtil.createDirs(ftpClient, remotePath)){
					logger.error("FTP change path fail : " + remotePath);
					return false;
				}
			}
			logger.error("FTP change path success : " + remotePath);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("FTP change path error : " + e.getMessage());
			return false;
		}
	}
	
	
	public static boolean fileExist(FTPClient ftpClient, String remoteFile){
		try {
			InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);
			int repleCode = ftpClient.getReplyCode();
			//FTPReply.FILE_UNAVAILABLE = 550
			if (inputStream == null || repleCode == 550){
				return false;
			}
			//
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
    public static boolean deleteFile(FTPClient ftpClient, String remoteFile){
        if (fileExist(ftpClient, remoteFile)){
            try {
                if (ftpClient.deleteFile(remoteFile)){
                    logger.debug("Ftp delete success");
                    return true;
                }else{
                    logger.error("Ftp delete failed");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Ftp delete error");
                return false;
            }
        }else {
            logger.debug("Ftp not exist");
            return true;
        }
    }

	public static boolean writeFile(FTPClient ftpClient, String writeText, String remoteFile) {



		try {
			// 将字符串转化为流
			InputStream inputStream = StreamUtil.toInput(writeText);

			//设置文件类型
			if (!ftpClient.setFileType(FTP.BINARY_FILE_TYPE)) {
				logger.error("FTP file error : binary");
				return false;
			}
			//
			String remotePath = remoteFile.substring(0, remoteFile.lastIndexOf("/"));
			String remoteName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
			// 存在就跳转目录路径，不存在就创建并跳转目录路径
			if (!FtpUtil.changePath(ftpClient, remotePath)) {
				logger.error("FTP change path error");
				return false;
			}

			// 按绝对路径上传文件
			// if (ftpClient.storeFile(remoteFile, inputStream)) {
			// 按相对路径上传文件
			if (ftpClient.storeFile(remoteName, inputStream)) {
				logger.info("FTP upload success : " + remoteFile);
				return true;
			} else {
				logger.info("FTP upload fail : " + remoteFile);
				return false;
			}


		} catch (IOException e) {
			e.printStackTrace();
			logger.error("FTP upload error : " + e.getMessage());
			return false;
		}
	}
	
	public static String readFile(FTPClient ftpClient, String remoteFile) {
		try {
			InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);
			String readText = StringUtil.fromBytes(FileUtil.toBytes(inputStream));
			return readText;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


    public static void closeFtp(FTPClient ftpClient) {
        try {
            boolean logout = ftpClient.logout();
            if (logout) {
                logger.info("logout Ftp success");
            } else {
                logger.error("logout Ftp failed");
            }
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("logout Ftp error : " + e.getMessage());
        }


    }
}
