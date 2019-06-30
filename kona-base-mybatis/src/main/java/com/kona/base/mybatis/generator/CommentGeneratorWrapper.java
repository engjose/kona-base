package com.kona.base.mybatis.generator;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
public class CommentGeneratorWrapper extends CommentGeneratorAdaptor {

    private Properties properties;

    public CommentGeneratorWrapper() {
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // 获取自定义的 properties
        this.properties.putAll(properties);
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String author = properties.getProperty("author");
        String dateFormat = properties.getProperty("dateFormat", "yyyy-MM-dd");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

        // 获取表注释
        String remarks = introspectedTable.getRemarks();

        topLevelClass.addJavaDocLine("/**");
        if (null != remarks && !remarks.trim().equals("")) {
            topLevelClass.addJavaDocLine(" * " + remarks);
        }

        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author " + (StringUtils.isNotBlank(author) ? author : "Generator"));
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @date " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 获取列注释
        String remarks = introspectedColumn.getRemarks();
        if (null != remarks && !remarks.trim().equals("")) {
            field.addJavaDocLine("/** " + remarks + " */");
        }
    }
}
