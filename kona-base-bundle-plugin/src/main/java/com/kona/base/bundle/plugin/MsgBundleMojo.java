package com.kona.base.bundle.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
@Mojo(name="bundle", requiresProject = false, defaultPhase= LifecyclePhase.INSTALL)
public class MsgBundleMojo extends AbstractMojo {

    @Parameter(property = "bundle.codeHead")
    private String codeHead;

    @Parameter(property = "bundle.targetClass")
    private String targetClass;

    @Parameter(defaultValue = "bundle/template.properties")
    private String templatePath;

    @Parameter
    private File bundleFile;

    @Parameter
    private File templateFile;

    /** 包前缀 */
    private static final String STANDARD_PACKAGE_PREFIX = "src/main/java/";


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Properties bundleProps = PropertiesUtil.getProps(bundleFile);
        Properties templateProps = templateFile != null ? PropertiesUtil.getProps(templateFile) : PropertiesUtil.getProps(templatePath);
        if (null == targetClass || "".equals(targetClass)) {
            throw new IllegalArgumentException("生成Bundle-message, 包路径{packagePath}不能为空");
        }

        int lastIndex = targetClass.lastIndexOf(".");
        String sourcePackage  = targetClass.substring(0, lastIndex);
        String className = targetClass.substring(lastIndex + 1);
        String targetPackage = STANDARD_PACKAGE_PREFIX + sourcePackage.replace(".", "/");

        BufferedWriter bw = null;
        try {
            // 1.创建包路径
            JavaFileUtil.createPackage(targetPackage);

            // 2.创建java文件
            bw = JavaFileUtil.createBlankJavaFile(targetPackage + "/" +  className + ".java");

            // 3.写入消息内容
            JavaFileUtil.writeMsgJavaFile(bundleProps, templateProps, bw, sourcePackage, className, codeHead);

        } catch (Exception e) {
            throw new RuntimeException("创建消息文件失败Bundle-Msg");
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
