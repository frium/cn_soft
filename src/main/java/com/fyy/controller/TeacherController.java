package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.context.BaseContext;
import com.fyy.pojo.dto.*;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.StudentScoreVo;
import com.fyy.service.ScoreService;
import com.fyy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @date 2024-05-15 22:06:14
 * @description
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    TeacherService teacherService;

    @PostMapping("/login")
    public R<Teacher> teacherLogin(@RequestBody LoginDto teacher) {
        return R.success(teacherService.getTeacher(teacher));
    }

    @PostMapping("/register")
    public R<String> teacherRegister(@RequestBody UserDto teacher) {
        teacherService.addTeacher(teacher);
        return R.success();
    }
    //获取指定的考试中所有学生的成绩
    @GetMapping("/getAllStudentsScores")
    public R<List<StudentScoreVo>> getAllStudentsScores(@RequestBody PageDto pageDto) {
        return R.success(scoreService.getAllStudentsScores(pageDto));
    }

    //获取所有考试title
    @GetMapping("/getAllScores")
    public R<List<String>> getAllScores(@RequestBody PageDto pageDto){
        return R.success(scoreService.getAllScores(pageDto));
    }

    //添加学生成绩
    @PostMapping("/addStudentScores")
    public R<String> addStudentScores(@RequestBody ScoreDto score) {
        scoreService.addStudentScore(score);
        return R.success();
    }
    //找回密码
    @PostMapping("/forgetPassword")
    public R<String> forgetPassword(@RequestBody ForgetPasswordDto forgetPasswordDto) {
        return R.success(teacherService.forgetPassword(forgetPasswordDto));
    }

    //退出登录
    @GetMapping("/logout")
    public R<?> logout() {
        BaseContext.removeCurrentId();
        return R.success();
    }

    //通过导入excel添加学生的成绩
}
