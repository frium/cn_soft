package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.dto.ForgetPasswordDto;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.RegisterDto;
import com.fyy.pojo.entity.Teacher;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @date 2024-05-15 22:13:12
 * @description
 */
public interface TeacherService extends IService<Teacher> {
    //老师账号登录
    Teacher getTeacher(LoginDto teacher);
    //老师注册
    void addTeacher(RegisterDto teacher);
    String forgetPassword(ForgetPasswordDto forgetPasswordDto);
    void loadScores(String title);
    void uploadScores(MultipartFile excel);
}
