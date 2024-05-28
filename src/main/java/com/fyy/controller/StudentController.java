package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.context.BaseContext;
import com.fyy.pojo.dto.ForgetPasswordDto;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.PersonalInfoDto;
import com.fyy.pojo.dto.UserDto;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.PersonalInfoVo;
import com.fyy.service.ScoreService;
import com.fyy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:26:49
 * @description
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ScoreService scoreService;

    @PostMapping("/login")
    public R<Student> studentLogin(@RequestBody LoginDto student) {
        return R.success(studentService.getStudent(student));
    }

    @PostMapping("/register")
    public R<String> studentRegister(@RequestBody UserDto student) {
        studentService.addStudent(student);
        return R.success();
    }

    //查询学生的成绩
    @GetMapping("/getAllScores")
    public R<List<Score>> getAllScores() {
        return R.success(scoreService.getStudentScores());
    }

    //添加老师
    @GetMapping("/addTeacher")
    public R<String> addTeacher(String classCode) {
        studentService.addTeacher(classCode);
        return R.success();
    }

    //修改个人信息
    @PutMapping("/modifyPersonalInfo")
    public R<?> modifyPersonalInfo(@RequestBody PersonalInfoDto personalInfoDto) {
        studentService.modifyPersonalInfo(personalInfoDto);
        return R.success();
    }

    //获取个人主页信息
    @GetMapping("/getPersonalInfo")
    public R<PersonalInfoVo> getPersonalInfo() {
        return R.success(studentService.getPersonalInfo());
    }

    //找回密码
    @PostMapping("/forgetPassword")
    public R<String> forgetPassword(@RequestBody ForgetPasswordDto forgetPasswordDto) {
        return R.success(studentService.forgetPassword(forgetPasswordDto));
    }

    //退出登录
    @GetMapping("/logout")
    public R<?> logout() {
        BaseContext.removeCurrentId();
        return R.success();
    }

}
