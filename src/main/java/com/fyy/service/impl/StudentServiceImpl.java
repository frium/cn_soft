package com.fyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.mapper.StudentMapper;
import com.fyy.mapper.TeacherMapper;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.UserDto;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.entity.Teacher;
import com.fyy.service.StudentService;
import com.fyy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @date 2024-05-16 17:32:08
 * @description
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpSession httpSession;
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public Student getStudent(LoginDto loginDto) {
        Student s;
        if (loginDto.getUsername().length() == 11) {
            s = lambdaQuery().eq(Student::getPhone, loginDto.getUsername()).eq(Student::getPassword, loginDto.getPassword()).one();
        } else if (loginDto.getUsername().length() == 18) {
            s = lambdaQuery().eq(Student::getPersonalId, loginDto.getUsername()).eq(Student::getPassword, loginDto.getPassword()).one();
        } else {
            throw new MyException(StatusCodeEnum.FAIL);
        }
        if (s != null) {
            response.setHeader("token", JwtUtil.createToken());//设置token到请求头中
            httpSession.setAttribute("userRole", "student");
        } else {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        return s;
    }

    @Override
    public void addStudent(UserDto userDto) {
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Student s = lambdaQuery().eq(Student::getPhone, userDto.getPhone()).or().eq(Student::getPersonalId, userDto.getPersonalId()).one();
        if (s != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
        Student student = new Student();
        BeanUtils.copyProperties(userDto, student);
        save(student);
    }


    @Override
    public void addTeacher(String ID, String classCode) {
        //先在数据库中查找看classCode是否正确
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teacher::getClassCode, classCode);
        Teacher teacher = teacherMapper.selectOne(lambdaQueryWrapper);
        if (teacher == null) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        studentMapper.addTeacher(ID, classCode);
    }

}
