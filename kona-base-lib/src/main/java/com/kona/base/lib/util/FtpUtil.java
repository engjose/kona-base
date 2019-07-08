package com.kona.base.lib.util;

import com.kona.base.lib.exception.ServerException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
public class FtpUtil {

    /**
     * 上传文件到FTP服务器
     *
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @param directory 目录
     * @param file 文件
     */
    public static void uploadFile(String host, Integer port, String username, String password, String directory, File file) throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(60000);//定义连接时间
        ftpClient.setControlEncoding("UTF-8");
        try {
            int reply;
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new ServerException("9999", "连接FTP服务器失败");
            }

            // 切换文件夹
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            try {
                ftpClient.changeWorkingDirectory(directory);
            } catch (Exception e) {
                ftpClient.makeDirectory(directory);
                ftpClient.changeWorkingDirectory(directory);
            }

            // 上传文件
            InputStream is = new FileInputStream(file);
            ftpClient.storeFile(file.getName(), is);
            is.close();
            ftpClient.logout();
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }
    }

    /**
     * downLoad file from ftp
     *
     * @param hostname
     * @param port
     * @param username
     * @param password
     * @param pathname
     * @param filename
     * @param localPath
     */
    public static void downloadFile(String hostname, int port, String username, String password, String pathname, String filename, String localPath) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFtpClient(hostname, port, username, password);
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localPath + "/" + file.getName());
                    OutputStream os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                    // do no thing
                }
            }
        }
    }

    private static FTPClient getFtpClient(String hostname, int port, String username, String password) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(hostname, port);
        ftpClient.login(username, password);
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            ftpClient.disconnect();
            throw new ServerException("9999", "连接FTP服务器失败");
        }

        return ftpClient;
    }
}
