package com.kona.base.model.enums;

/**
 * @author : JOSE 2019/5/7 1:57 PM
 */
public enum ErrorLevelEnum implements BaseEnum {
    CLIENT_ERR("0", "客户端异常"),
    SERVER_BIZ_ERR("1", "服务端业务异常"),
    SERVER_SYSTEM_ERR("2", "服务端系统异常");

    private String code;
    private String desc;

    ErrorLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
