package com.kona.base.bundle.plugin;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
public class JavaFileUtil {
    /**
     * 创建包路径
     *
     * @param sourcePackage 原始配置包路径
     * @return 是否创建成功
     */
    public static Boolean createPackage(String sourcePackage) {
        File dir = new File(sourcePackage);
        if(!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    /**
     * 创建空的java文件
     *
     * @param javaFileName java文件名称
     * @return 返回文件流
     * @throws IOException IO异常
     */
    public static BufferedWriter createBlankJavaFile(String javaFileName) throws IOException {
        File file = new File(javaFileName);
        if (file.exists()) {
            file.delete();
        }

        boolean newFile = file.createNewFile();
        if (!newFile) {
            return null;
        }

        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
    }

    /**
     * 生成java文件
     *
     * @param bundleProps 消息配置文件
     * @param templateProps 模板配置
     * @param bw java文件写io
     * @param packagePath 包path
     * @param className 文件名
     * @throws IOException IO异常
     */
    public static void writeMsgJavaFile(Properties bundleProps, Properties templateProps, BufferedWriter bw, String packagePath, String className, String codeHead) throws IOException {
        Enumeration<?> enumeration = bundleProps.propertyNames();
        if (enumeration == null) {
            return;
        }

        int index = 0;
        while (enumeration.hasMoreElements()) {
            if (index == 0) {
                String packageInfo = "package " + packagePath + "; \n \n";
                String classComment = templateProps.getProperty("classDoc") + "\n";
                String classInfo = templateProps.getProperty("class").replace("#{class}", className)  + " { \n \n";

                bw.write(packageInfo); //导包
                bw.write(classComment); //类注释
                bw.write(classInfo); //类声明
            } else {
                String filedName = (String) enumeration.nextElement();
                String message = bundleProps.getProperty(filedName);

                // 文档信息
                String filedCommentCode = templateProps.getProperty("filedDoc").replace("#{code}", codeHead + filedName);
                String filedComment = filedCommentCode.replace("#{msg}", message) + "\n";

                //字段信息
                String filedLeft = templateProps.getProperty("filed").replace("#{filed}", codeHead + filedName);
                String filedRight =  "\"" + filedName + "\"" + "; \n \n";
                String filed = filedLeft + " = " + filedRight;
                bw.write(filedComment);
                bw.write(filed);
            }

            index++;
        }

        bw.write("} \n \n");
    }
}
