package com.fyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.mapper.ScoreMapper;
import com.fyy.mapper.StudentMapper;
import com.fyy.mapper.TeacherMapper;
import com.fyy.pojo.dto.ForgetPasswordDTO;
import com.fyy.pojo.dto.LoginDTO;
import com.fyy.pojo.dto.RegisterDTO;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.StudentScoreVO;
import com.fyy.service.ScoreService;
import com.fyy.service.TeacherService;
import com.fyy.utils.ExcelUtil;
import com.fyy.utils.JwtUtil;
import com.fyy.utils.ValueCheckUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    StudentMapper studentMapper;
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    HttpServletResponse response;
    @Autowired
    ScoreService scoreService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Value("${jwt.key}")
    String key;
    @Value("${jwt.ttl}")
    long ttl;

    @Override
    public Teacher getTeacher(LoginDTO loginDto) {
        Teacher t = null;
        if (ValueCheckUtil.checkPassword(loginDto.getPassword())) {
            if (ValueCheckUtil.checkUsername(loginDto.getUsername()).equals("phone")) {
                t = lambdaQuery().eq(Teacher::getPhone, loginDto.getUsername()).eq(Teacher::getPassword, loginDto.getPassword()).one();
            } else {
                t = lambdaQuery().eq(Teacher::getPersonalId, loginDto.getUsername()).eq(Teacher::getPassword, loginDto.getPassword()).one();
            }
        }
        if (t != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("teacherId", t.getID());
            response.setHeader("Authorization", JwtUtil.createToken(key, ttl, claims));
            httpSession.setAttribute("userRole", "teacher");
        } else {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        return t;
    }

    @Override
    public void addTeacher(RegisterDTO userDto) {
        if(!userDto.getVerify().equals(redisTemplate.opsForValue().get("verify"))) throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        ValueCheckUtil.checkPhone(String.valueOf(userDto.getPhone()));
        ValueCheckUtil.checkPersonalId(userDto.getPersonalId());
        ValueCheckUtil.checkPassword(userDto.getPassword());
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Teacher t = lambdaQuery().eq(Teacher::getPhone, userDto.getPhone()).or().eq(Teacher::getPersonalId, userDto.getPersonalId()).one();
        Student s = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getPhone, userDto.getPhone())
                .or().eq(Student::getPersonalId, userDto.getPersonalId()));
        if (t != null || s != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String classCode = uuid.substring(0, 6);
        Teacher teacher = new Teacher();
        teacher.setClassCode(classCode);
        teacher.setName("教师"+classCode);
        teacher.setSex("男");
        BeanUtils.copyProperties(userDto, teacher);
        save(teacher);
    }

    @Override
    public String forgetPassword(ForgetPasswordDTO forgetPasswordDto) {
        Teacher teacher = lambdaQuery().eq(Teacher::getPhone, forgetPasswordDto.getPhone())
                .eq(Teacher::getPersonalId, forgetPasswordDto.getPersonalId()).one();
        if (teacher == null) throw new MyException(StatusCodeEnum.USER_NOT_EXIST);
        else return teacher.getPassword();

    }

    @Override
    public void loadScores(String title) {
        Long currentId = BaseContext.getCurrentId();
        List<StudentScoreVO> allStudentsScores = scoreMapper.getAllStudentsScores(currentId, 0, 0, title, true);
        ExcelUtil.writeStudentScoresToExcel(allStudentsScores, response);
    }

    @Override
    public void uploadScores(MultipartFile excel) {
        try {
            List<Score> studentScoreVos = ExcelUtil.parseExcel(excel);
            scoreService.saveBatch(studentScoreVos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

