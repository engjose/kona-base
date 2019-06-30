package com.kona.base.lib.exception;

import com.kona.base.model.enums.ErrorLevelEnum;
import com.kona.base.model.enums.MonitorLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Yuan.Pan 2019/6/29 3:04 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {

    /** 返回code */
    private String code;

    /** 返回信息 */
    private String message;

    /** 错误级别, 业务自己定 */
    private ErrorLevelEnum errorLevel;

    /** 是否需要加入监控, 业务自己决定 */
    private MonitorLevelEnum monitorLevel;

    /** 错误消息参数 */
    private Object[] params;

}
