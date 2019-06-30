package com.kona.base.lib.exception;

import com.kona.base.model.enums.ErrorLevelEnum;
import com.kona.base.model.vo.BaseResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * @author : Yuan.Pan 2019/6/29 11:03 PM
 */
@Slf4j
@SuppressWarnings("unchecked")
public abstract class ExceptionAdvance {

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResp handlerBindException(BindException ex) {
        log.error("[BIND_EXCEPTION]:", ex);
        return new BaseResp("4000", ex.getFieldError().getDefaultMessage(), null, ErrorLevelEnum.CLIENT_ERR.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResp handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("[METHOD_ARGUMENT_NOT_VALID_EXCEPTION]:", ex);
        return new BaseResp("4000", ex.getBindingResult().getFieldError().getDefaultMessage(), null, ErrorLevelEnum.CLIENT_ERR.getCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BaseResp handlerConstraintViolationException(ConstraintViolationException ex) {
        log.error("[CONSTRAINT_VIOLATION_EXCEPTION]:", ex);
        return new BaseResp("4000", ex.getConstraintViolations().iterator().next().getMessage(), null, ErrorLevelEnum.CLIENT_ERR.getCode());
    }

    @ExceptionHandler(ClientException.class)
    @ResponseBody
    public BaseResp handlerClientException(ClientException ex) {
        log.error("[CLIENT_EXCEPTION]:", ex);
        return new BaseResp(ex.getCode(), fillParam(ex.getMessage(), ex.getParams()), null, ErrorLevelEnum.CLIENT_ERR.getCode());
    }

    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public BaseResp handlerServerException(ServerException ex) {
        log.error("[SERVER_EXCEPTION]:", ex);
        return new BaseResp(ex.getCode(), fillParam(ex.getMessage(), ex.getParams()), null, ErrorLevelEnum.SERVER_BIZ_ERR.getCode());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public BaseResp handlerThrowable(Throwable th) {
        log.error("[SERVER_SYS_ERROR]:", th);
        return BaseResp.SERVER_SYS_ERROR;
    }

    private String fillParam(String message, Object[] params) {
        if (null != params && params.length > 0) {
            for (int index = 0; index < params.length; index++) {
                message = message.replace("{" + index + "}", String.valueOf(params[index]));
            }
        }

        return message;
    }
}
