package com.fyy.utils;

import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;

import java.util.regex.Pattern;

/**
 *
 * @date 2024-05-30 20:19:20
 * @description
 */
public class ValueCheckUtil {
    public static void checkPhone(String phone) {
        if (!Pattern.matches("^1[3456789]\\d{9}$", phone)) {
            throw new MyException(StatusCodeEnum.PHONE_ERROR);
        }
    }
    public static void checkPersonalId(String personalId) {
        if (!Pattern.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", personalId)) {
            throw new MyException(StatusCodeEnum.PERSONAL_ID_ERROR);
        }

    }

    public static String checkUsername(String username) {
        if (Pattern.matches("^1[3456789]\\d{9}$", username)) {
            return "phone";
        } else if (Pattern.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", username)) {
            return "personalId";
        } else {
            throw new MyException(StatusCodeEnum.VALUE_ERROR);
        }
    }
}
