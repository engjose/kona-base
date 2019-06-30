package com.kona.base.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author : Yuan.Pan 2019/6/29 1:44 PM
 */
public interface BaseEnum {

    String DEFAULT_CODE_NAME = "code";
    String DEFAULT_DESC_NAME = "desc";

    /**
     * enum get default code
     *
     * @return return code
     */
    default String getCode() {
        return getVal(DEFAULT_CODE_NAME);
    }

    /**
     * get enum desc
     *
     * @return return enum desc
     */
    default String getDesc() {
        return getVal(DEFAULT_DESC_NAME);
    }

    /**
     * mapping desc by code
     *
     * @param enumClazz enum-Clazz
     * @param code  code
     * @param <T> T
     * @return desc
     */
    static <T extends  Enum<T>> String mappingDesc(Class<T> enumClazz, String code) {
        return ((BaseEnum) valueOfEnum(enumClazz, code)).getDesc();
    }

    /**
     * parse value 2 enum
     *
     * @param enumClazz {param-0}: enum class
     * @param code {param-1}: enum code
     * @param <T> {param-2}: Generics
     * @return {return enum}
     */
    static <T extends  Enum<T>> T valueOfEnum(Class<T> enumClazz, String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("enum value can not null");
        }

        if (enumClazz.isAssignableFrom(BaseEnum.class) || !BaseEnum.class.isAssignableFrom(enumClazz)) {
            throw new IllegalArgumentException("enum must impl BaseEnum");
        }

        T[] enumConstantList = enumClazz.getEnumConstants();
        if (null == enumConstantList || enumConstantList.length == 0) {
            throw new IllegalArgumentException("parse enum value null");
        }

        for (T element : enumConstantList) {
            BaseEnum baseEnum = (BaseEnum) element;
            if (code.equals(baseEnum.getCode())) {
                return element;
            }
        }

        throw new IllegalArgumentException("parse enum value err");
    }

    default String getVal(String type) {
        try {
            Field field = this.getClass().getDeclaredField(type);
            if (null == field) {
                return null;
            }
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
