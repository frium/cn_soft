package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.PageDto;
import com.fyy.pojo.dto.ScoreDto;
import com.fyy.pojo.dto.UserDto;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.StudentVo;
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

    @GetMapping("/getAllStudentsScores")
    public R<List<StudentVo>> getAllStudentsScores(@RequestBody PageDto pageDto) {
        return R.success(scoreService.getAllStudentsScores(pageDto));
    }

    //添加学生成绩
    @PostMapping("/addStudentScores")
    public R<String> addStudentScores(@RequestBody ScoreDto score) {
        scoreService.addStudentScore(score);
        return R.success();
    }

    //通过导入excel添加学生的成绩
}
