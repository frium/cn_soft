package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Student;
import com.fyy.server.ScoreService;
import com.fyy.server.StudentService;
import com.fyy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
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
    public R<Student> studentLogin(@RequestBody Student student, HttpServletResponse response){
        if(studentService.getStudent(student)!=null){
            response.setHeader("token", JwtUtil.createToken());//设置token到请求头中
            return R.success(student);//看到时候需要返回的数据是什么
        }
        return R.error("登录失败,请检查账号或者密码是否有误");
    }
    @PostMapping("/register")
    public R<String> studentRegister(@RequestBody Student student){
        if(studentService.addStudent(student)) return R.success("注册成功");
        else return R.error("注册失败,身份证或手机号已被注册");
    }
    //查询学生的成绩
    @GetMapping("/getAllScores")
    public R<List<Score>> getAllScores(String ID){
        return R.success(scoreService.getStudentScores(ID));
    }

    //添加老师
    @GetMapping("/addTeacher")
    public R<String>addTeacher(String ID,String classCode){
        if(studentService.addTeacher(ID,classCode)) return R.success("添加成功");
        else return R.error("添加失败,请检查班级码是否正确");
    }
}
