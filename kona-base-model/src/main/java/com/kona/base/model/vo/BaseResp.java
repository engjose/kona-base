package com.kona.base.model.vo;

import com.kona.base.model.enums.ErrorLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Yuan.Pan 2019/6/29 1:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class BaseResp<T> {

    /** 返回码 */
    private String code = "0000";

    /** 返回消息 */
    private String msg = "操作成功";

    /** 返回数据 */
    private T data;

    /** 错误级别 */
    private String errorLevel;

    public static BaseResp SUCCESS = new BaseResp("0000", "操作成功", null, null);
    public static BaseResp SERVER_SYS_ERROR = new BaseResp("5000", "系统异常", null, ErrorLevelEnum.SERVER_SYSTEM_ERR.getCode());
    public static BaseResp CLIENT_ERROR = new BaseResp("4000", "客户端异常", null, ErrorLevelEnum.CLIENT_ERR.getCode());
}
