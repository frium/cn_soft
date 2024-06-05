package com.fyy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @date 2024-05-21 21:12:29
 * @description
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {
    SUCCESS(200,"操作成功",200),
    FAIL(-1, "操作失败",200),
    NOT_FOUND(4040, "未找到相关内容",200),
    VALUE_ERROR(4041,"参数有误",500),
    USER_EXIST(1001,"手机或身份证已被注册",200),
    LOGIN_FAIL(1002,"登录失败,请检查账号或者密码是否有误",200),
    NOT_LOGIN(1003,"用户未登陆",401),
    ERROR_VERIFY(1004,"验证码错误",200),
    USER_NOT_EXIST(1004, "用户不存在",200),
    PERSONAL_ID_ERROR(1005,"身份证格式有误",200),
    PHONE_ERROR(1006,"手机号格式有误",200),
    PASSWORD_ERROR(1007,"密码格式有误",200),
    AI_CONNECT_FAIL(5001,"获取ai连接失败!",500),
    FILE_LARGE(6001,"传输文件过大",200);

    private final Integer code;
    private final String desc;
    private final Integer httpStatusCode;
}
