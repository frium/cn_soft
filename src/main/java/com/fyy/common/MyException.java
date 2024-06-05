package com.fyy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @date 2024-05-21 20:36:37
 * @description
 */
@Getter
@AllArgsConstructor
public class MyException extends RuntimeException {


    private StatusCodeEnum statusCodeEnum;

    private final Integer code;

    private final String message;

    private final Integer httpStatusCode;

    public MyException(StatusCodeEnum statusCodeEnum) {
        this.statusCodeEnum = statusCodeEnum;
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
        this.httpStatusCode = statusCodeEnum.getHttpStatusCode();
    }

    public MyException(String message) {
        this.code = StatusCodeEnum.FAIL.getCode();
        this.message = message;
        this.httpStatusCode = StatusCodeEnum.FAIL.getHttpStatusCode();
    }
    public MyException(String message,int code) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = StatusCodeEnum.FAIL.getHttpStatusCode();
    }

}
