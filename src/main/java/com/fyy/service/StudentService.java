package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.dto.ForgetPasswordDto;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.PersonalInfoDto;
import com.fyy.pojo.dto.UserDto;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.PersonalInfoVo;

/**
 *
 * @date 2024-05-16 17:31:41
 * @description
 */
public interface StudentService extends IService<Student> {
    //获取学生对象
    Student getStudent(LoginDto student);
    //添加学生
    void addStudent(UserDto student);

    //添加老师
    void addTeacher(String classCode);
    //获取个人主页信息
    PersonalInfoVo getPersonalInfo();
    void modifyPersonalInfo(PersonalInfoDto personalInfoDto);
    String forgetPassword(ForgetPasswordDto forgetPasswordDto);
}
