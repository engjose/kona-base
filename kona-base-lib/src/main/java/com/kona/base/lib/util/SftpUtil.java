package com.kona.base.lib.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author : JOSE 2018/11/7 11:15 AM
 */
@Slf4j
public class SftpUtil {

    private Session sshSession;

    /**
     * 上传文件到SFTP服务器
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param directory
     * @param file
     */
    public static void uploadFile(String host, Integer port, String username, String password, String directory, File file) throws JSchException {
        SftpUtil sftpUtil = new SftpUtil();
        try {
            ChannelSftp sftp = sftpUtil.connect(host, port, username, password);
            if (sftp != null) {
                sftpUtil.uploadFile(sftp, directory, file);
            }
        } catch (Exception e) {
            log.error("FTP登录失败: IP:{}, e-{}", host, e);
            throw e;
        }
    }

    /**
     * 登录Sftp
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     * @throws JSchException
     */
    private ChannelSftp connect(String host, Integer port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        jsch.getSession(username, host, port);
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        Channel channel = sshSession.openChannel("sftp");
        channel.connect() ;
        return (ChannelSftp) channel;
    }

    /**
     * 上传文件到指定目录
     *
     * @param sftp
     * @param directory
     * @param file
     */
    private void uploadFile(ChannelSftp sftp, String directory, File file){
        try {
            sftp.cd(directory);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            log.error("上传文件失败, dir:{}, fileName:{}, e-{}",directory, file.getName(), e);
        } finally {
            if (sftp != null) {
                sftp.disconnect() ;
                sftp.exit();
            }
            if (sshSession != null) {
                sshSession.disconnect();
                sshSession = null;
            }
        }
    }
}
