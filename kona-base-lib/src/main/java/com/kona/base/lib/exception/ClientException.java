package com.kona.base.lib.exception;

import com.kona.base.model.enums.ErrorLevelEnum;
import com.kona.base.model.enums.MonitorLevelEnum;
import lombok.Data;

/**
 * @author : Yuan.Pan 2019/6/29 3:06 PM
 */
@Data
public class ClientException extends BaseException {

    public ClientException(String code, String message) {
        super(code, message, ErrorLevelEnum.CLIENT_ERR, MonitorLevelEnum.GREEN_MONITOR, null);
    }

    public ClientException(String code, String message, MonitorLevelEnum monitor) {
        super(code, message, ErrorLevelEnum.CLIENT_ERR, monitor, null);
    }

    public ClientException(String code, String message, Object[] params) {
        super(code, message, ErrorLevelEnum.CLIENT_ERR, MonitorLevelEnum.GREEN_MONITOR, params);
    }
}
