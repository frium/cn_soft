package com.fyy.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @date 2024-05-15 17:14:28
 * @description
 */
@Data
public class R<T> implements Serializable {

    private Integer code;

    private String msg; //错误信息

    private T data; //数据

    public static <T> R<T> success(T object) {
        R<T> result = new R<>();
        result.code = StatusCodeEnum.SUCCESS.getCode();
        result.msg = StatusCodeEnum.SUCCESS.getDesc();
        result.data=object;
        return result;
    }

    public static <T> R<T> success() {
        R<T> result = new R<>();
        result.code = StatusCodeEnum.SUCCESS.getCode();
        result.msg = StatusCodeEnum.SUCCESS.getDesc();
        return result;
    }

    public static <T> R<T> error(String msg) {
        R<T> result = new R<>();
        result.code = StatusCodeEnum.FAIL.getCode();
        result.msg = msg;
        return result;
    }
    //?
    public static <T> R<T> error(StatusCodeEnum statusCodeEnum) {
        R<T> result = new R<>();
        result.code = StatusCodeEnum.FAIL.getCode();
        result.msg = statusCodeEnum.getDesc();
        return result;
    }
}