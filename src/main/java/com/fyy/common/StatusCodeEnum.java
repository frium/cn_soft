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
    SUCCESS(200,"操作成功"),
    FAIL(-1, "操作失败"),
    NOT_FOUND(4040, "未找到相关内容"),
    USER_EXIST(1001,"手机或身份证已被注册"),
    LOGIN_FAIL(1002,"登录失败,请检查账号或者密码是否有误");



    private final Integer code;

    private final String desc;
}
