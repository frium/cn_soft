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
    public static boolean checkPhone(String phone){
        if(Pattern.matches("^1[3456789]\\d{9}$", phone)){
            return true;
        }
        throw new MyException(StatusCodeEnum.VALUE_ERROR);
    }
    public static boolean checkPassword(String password){
        if(password.length() > 7 && password.length() < 17){
            return true;
        }
        throw new MyException(StatusCodeEnum.VALUE_ERROR);
    }
    public static boolean checkPersonalId(String personalId){
        if( Pattern.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$",personalId)){
            return true;
        }
        throw new MyException(StatusCodeEnum.VALUE_ERROR);
    }
    public static String checkUsername(String username){
        if(checkPhone(username)){
            return "phone";
        }else if(checkPersonalId(username)){
            return "personalId";
        }else {
            throw new MyException(StatusCodeEnum.VALUE_ERROR);
        }
    }
}
