package com.kona.base.model.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Yuan.Pan 2019/6/29 1:59 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumnAnno {
    int index();
    String label();
    String format() default "yyyy-MM-dd HH:mm:ss";
}
