package com.kona.base.lib.exception;

import com.kona.base.model.enums.ErrorLevelEnum;
import com.kona.base.model.enums.MonitorLevelEnum;
import lombok.Data;

/**
 * @author : Yuan.Pan 2019/6/29 3:08 PM
 */
@Data
public class ServerException extends BaseException {

    public ServerException(String code, String message) {
        super(code, message, ErrorLevelEnum.SERVER_BIZ_ERR, MonitorLevelEnum.GREEN_MONITOR, null);
    }

    public ServerException(String code, String message, Object[] params) {
        super(code, message, ErrorLevelEnum.SERVER_BIZ_ERR, MonitorLevelEnum.GREEN_MONITOR, params);
    }

    public ServerException(String code, String message, MonitorLevelEnum monitorLevelEnum) {
        super(code, message, ErrorLevelEnum.SERVER_BIZ_ERR, monitorLevelEnum, null);
    }
}
