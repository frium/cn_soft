package com.fyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.mapper.TeacherMapper;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.PageDto;
import com.fyy.pojo.dto.UserDto;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.entity.Teacher;
import com.fyy.service.TeacherService;
import com.fyy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 * @date 2024-05-15 22:14:13
 * @description
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpSession httpSession;

    @Override
    public Teacher getTeacher(LoginDto loginDto) {
        Teacher t;
        if (loginDto.getUsername().length() == 11) {
            t = lambdaQuery().eq(Teacher::getPhone, loginDto.getUsername()).eq(Teacher::getPassword, loginDto.getPassword()).one();
        } else if (loginDto.getUsername().length() == 18) {
            t = lambdaQuery().eq(Teacher::getPersonalId, loginDto.getUsername()).eq(Teacher::getPassword, loginDto.getPassword()).one();
        }else {
            throw new MyException(StatusCodeEnum.FAIL);
        }
        if (t != null) {
            response.setHeader("token", JwtUtil.createToken());
            httpSession.setAttribute("userRole", "teacher");
        } else {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        return t;
    }

    @Override
    public void addTeacher(UserDto userDto) {
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Teacher t = lambdaQuery().eq(Teacher::getPhone, userDto.getPhone()).or().eq(Teacher::getPersonalId, userDto.getPersonalId()).one();
        if (t != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
        //给老师设置一个六位数的班级码
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String classCode = uuid.substring(0, 6);
        Teacher teacher = new Teacher();
        teacher.setClassCode(classCode);
        BeanUtils.copyProperties(userDto, teacher);
        save(teacher);
    }


}

