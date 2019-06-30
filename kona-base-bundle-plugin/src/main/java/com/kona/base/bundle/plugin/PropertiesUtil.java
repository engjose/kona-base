package com.kona.base.bundle.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(value == null){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){
        String value = props.getProperty(key.trim());
        if(null == value){
            value = defaultValue;
        }
        return value.trim();
    }

    public static Properties getProps(String sourcePath) {
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(sourcePath),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
        return props;
    }

    public static Properties getProps(File file) {
        props = new Properties();
        try {
            InputStream inputStream = new FileInputStream(file);
            props.load(new InputStreamReader(inputStream,"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
        return props;
    }
}
