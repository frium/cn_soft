package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.StudentVo;
import com.fyy.server.ScoreService;
import com.fyy.server.StudentService;
import com.fyy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @date 2024-05-16 17:26:49
 * @description
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
   private ScoreService scoreService;
    @Autowired
    private StudentService studentService;
    @PostMapping("/login")
    public R<Student> studentLogin(@RequestBody Student student, HttpServletResponse response){
        if(studentService.getStudent(student)!=null){
            //设置token到请求头中
            response.setHeader("token", JwtUtil.createToken());
            return R.success(student);//看到时候需要返回的数据是什么
        }
        return R.error("登录失败,请检查账号或者密码是否有误");
    }
    @PostMapping("/register")
    public R<String> studentRegister(@RequestBody Student student){
        if(studentService.addStudent(student)){
            return R.success("注册成功");
        }else {
            return R.error("注册失败,身份证或手机号已被注册");
        }
    }
    //查询学生的成绩
    @GetMapping("/getScores")
    public R<StudentVo> getScores(String ID){
        //多表查询成绩
        return R.success(studentService.getStudentScores(ID));
    }
}
