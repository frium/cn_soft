package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.dto.ForgetPasswordDto;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.UserDto;
import com.fyy.pojo.entity.Teacher;

/**
 *
 * @date 2024-05-15 22:13:12
 * @description
 */
public interface TeacherService extends IService<Teacher> {
    //老师账号登录
    Teacher getTeacher(LoginDto teacher);
    //老师注册
    void addTeacher(UserDto teacher);
    String forgetPassword(ForgetPasswordDto forgetPasswordDto);
}
