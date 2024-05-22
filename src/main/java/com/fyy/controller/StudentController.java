package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.UserDto;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Student;
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
    public R<List<Score>> getAllScores(String ID) {
        return R.success(scoreService.getStudentScores(ID));
    }

    //添加老师
    @GetMapping("/addTeacher")
    public R<String> addTeacher(String ID, String classCode) {
        studentService.addTeacher(ID, classCode);
        return R.success();
    }


}
