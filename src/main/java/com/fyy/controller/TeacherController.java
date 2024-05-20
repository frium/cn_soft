package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.entity.Teacher;
import com.fyy.server.TeacherService;
import com.fyy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @date 2024-05-15 22:06:14
 * @description
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

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
}
