package com.kona.base.model.enums;

/**
 * @author : JOSE 2019/5/7 1:58 PM
 */
public enum MonitorLevelEnum implements BaseEnum {
    GREEN_MONITOR("0", "绿色告警"),
    YELLOW_MONITOR("1", "黄色告警"),
    RED_MONITOR("2", "红色告警");

    private String code;
    private String desc;

    MonitorLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
