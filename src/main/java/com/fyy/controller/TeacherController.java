package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.StudentVo;
import com.fyy.server.ScoreService;
import com.fyy.server.TeacherService;
import com.fyy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
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
    public R<Teacher>teacherLogin(@RequestBody Teacher teacher, HttpServletResponse response){
        if(teacherService.getTeacher(teacher)!=null){
            //设置token到请求头中
            response.setHeader("token", JwtUtil.createToken());
            return R.success(teacher);//看到时候需要返回的数据是什么
        }
        return R.error("登录失败,请检查账号或者密码是否有误");
    }
    @PostMapping("/register")
    public R<String>teacherRegister(@RequestBody Teacher teacher){
        if(teacherService.addTeacher(teacher)){
            return R.success("注册成功");
        }
        return  R.error("注册失败,身份证或手机号已被注册");
    }

    @GetMapping("/getAllStudentsScores")
    public R<List<StudentVo>>getAllStudentsScores(String ID,int page,int pageSize,String title){
        return R.success(scoreService.getAllStudentsScores(ID,page,pageSize,title));
    }

    //添加学生成绩
    @PostMapping("/addStudentScores")
    public R<String> addStudentScores(@RequestBody Score score){
        if(scoreService.addStudentScore(score)) return R.success("添加成绩成功!");
        else return R.error("添加成绩失败,请稍后再试");
    }

    //通过导入excel添加学生的成绩
}
