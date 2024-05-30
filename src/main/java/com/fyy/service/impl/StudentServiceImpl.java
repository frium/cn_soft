package com.fyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.mapper.StudentMapper;
import com.fyy.mapper.TeacherMapper;
import com.fyy.pojo.dto.ForgetPasswordDto;
import com.fyy.pojo.dto.LoginDto;
import com.fyy.pojo.dto.PersonalInfoDto;
import com.fyy.pojo.dto.RegisterDto;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.PersonalInfoVo;
import com.fyy.service.StudentService;
import com.fyy.utils.JwtUtil;
import com.fyy.utils.ValueCheckUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @date 2024-05-16 17:32:08
 * @description
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpSession httpSession;
    @Autowired
    TeacherMapper teacherMapper;
    @Value("${jwt.key}")
    String key;
    @Value("${jwt.ttl}")
    Long ttl;

    @Override
    public Student getStudent(LoginDto loginDto) {
        Student s = null;
        if (ValueCheckUtil.checkPassword(loginDto.getPassword())) {
            if (ValueCheckUtil.checkUsername(loginDto.getUsername()).equals("phone")) {
                s = lambdaQuery().eq(Student::getPhone, loginDto.getUsername()).eq(Student::getPassword, loginDto.getPassword()).one();
            } else {
                s = lambdaQuery().eq(Student::getPersonalId, loginDto.getUsername()).eq(Student::getPassword, loginDto.getPassword()).one();
            }
        }
        if (s != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("studentId", s.getID());
            response.setHeader("token", JwtUtil.createToken(key, ttl, claims));//设置token到请求头中
            httpSession.setAttribute("userRole", "student");
        } else {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        return s;
    }

    @Override
    public void addStudent(RegisterDto userDto) {
        if(!userDto.getVerify().equals(redisTemplate.opsForValue().get("verify"))) throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Student s = lambdaQuery().eq(Student::getPhone, userDto.getPhone()).or().eq(Student::getPersonalId, userDto.getPersonalId()).one();
        Teacher t = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>()
                .eq(Teacher::getPhone, userDto.getPhone())
                .or().eq(Teacher::getPersonalId, userDto.getPersonalId()));
        if (s != null && t != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
        Student student = new Student();
        BeanUtils.copyProperties(userDto, student);
        save(student);
    }


    @Override
    public void addTeacher(String classCode) {
        Long currentId = BaseContext.getCurrentId();
        //先在数据库中查找看classCode是否正确
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teacher::getClassCode, classCode);
        Teacher teacher = teacherMapper.selectOne(lambdaQueryWrapper);
        if (teacher == null) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        studentMapper.addTeacher(currentId, classCode);
    }

    @Override
    public PersonalInfoVo getPersonalInfo() {
        Long currentId = BaseContext.getCurrentId();
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getID, currentId));
        PersonalInfoVo personalInfoVo = new PersonalInfoVo();
        BeanUtils.copyProperties(student, personalInfoVo);
        Teacher teacher = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getID, student.getTeacherId()));
        if (teacher != null) {
            personalInfoVo.setTeacherName(teacher.getName());
        }
        return personalInfoVo;
    }

    @Override
    public void modifyPersonalInfo(PersonalInfoDto personalInfoDto) {
        Long currentId = BaseContext.getCurrentId();
        Student student = new Student();
        BeanUtils.copyProperties(personalInfoDto, student);
        student.setID(currentId);
        if (studentMapper.updateById(student) == 0) {
            throw new MyException(StatusCodeEnum.FAIL);
        }
    }

    @Override
    public String forgetPassword(ForgetPasswordDto forgetPasswordDto) {
        Student student = lambdaQuery().eq(Student::getPhone, forgetPasswordDto.getPhone())
                .eq(Student::getPersonalId, forgetPasswordDto.getPersonalId()).one();
        if (student == null) throw new MyException(StatusCodeEnum.USER_NOT_EXIST);
        else return student.getPassword();
    }

}
