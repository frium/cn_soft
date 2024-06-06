package com.fyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.mapper.ScoreMapper;
import com.fyy.mapper.StudentMapper;
import com.fyy.mapper.TeacherMapper;
import com.fyy.pojo.dto.*;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.SparkClient;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.PersonalInfoVo;
import com.fyy.pojo.vo.StudyPlanVo;
import com.fyy.service.StudentService;
import com.fyy.utils.AIUtil;
import com.fyy.utils.JwtUtil;
import com.fyy.utils.ValueCheckUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @date 2024-05-16 17:32:08
 * @description
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    SparkClient sparkClient;
    @Autowired
    ScoreMapper scoreMapper;
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
            response.setHeader("Authorization", JwtUtil.createToken(key, ttl, claims));//设置token到请求头中
            httpSession.setAttribute("userRole", "student");
        } else {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        return s;
    }

    @Override
    public void addStudent(RegisterDto userDto) {
        if (!userDto.getVerify().equals(redisTemplate.opsForValue().get("verify"))) throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        ValueCheckUtil.checkPhone(String.valueOf(userDto.getPhone()));
        ValueCheckUtil.checkPersonalId(userDto.getPersonalId());
        ValueCheckUtil.checkPassword(userDto.getPassword());
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Student s = lambdaQuery().eq(Student::getPhone, userDto.getPhone()).or().eq(Student::getPersonalId, userDto.getPersonalId()).one();
        Teacher t = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>()
                .eq(Teacher::getPhone, userDto.getPhone())
                .or().eq(Teacher::getPersonalId, userDto.getPersonalId()));
        if (s != null || t != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
        Student student = new Student();
        //生成学号
        String yearPart = String.valueOf(Year.now().getValue());
        StringBuilder randomPart = new StringBuilder(8);
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            randomPart.append(random.nextInt(10));
        }
        BeanUtils.copyProperties(userDto, student);
        student.setStudentId(yearPart+randomPart);
        student.setName("学生"+yearPart+randomPart);
        student.setSex("男");
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
        ValueCheckUtil.checkPhone(String.valueOf(personalInfoDto.getPhone()));
        ValueCheckUtil.checkPersonalId(personalInfoDto.getPersonalId());
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Student s = lambdaQuery().eq(Student::getPhone, personalInfoDto.getPhone()).or().eq(Student::getPersonalId, personalInfoDto.getPersonalId()).one();
        Teacher t = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>()
                .eq(Teacher::getPhone, personalInfoDto.getPhone())
                .or().eq(Teacher::getPersonalId, personalInfoDto.getPersonalId()));
        if (s != null && t != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
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

    @Override
    public String customizedPlan(PlanDto planDto) {
        //获取当前学生的近五次成绩
        Long currentId = BaseContext.getCurrentId();
        String key ="historyPlan"+currentId;
        List<Score> scores = scoreMapper.getStudentScoresLimit5(currentId,planDto.getSubjects());//获取需要定制计划的科目的成绩
        StringBuilder question= new StringBuilder("你是我的班主任,接下来我需要你帮助我去定制一个学习计划,你只需要考虑" + planDto.getSubjects() +
                "这科目,其他的不需要你考虑,这是我最近几次的这几科的考试成绩");
        for (Score score : scores) {
            question.append(score);
        }
        question.append("我的目标是").append(planDto.getTarget()).append("我预定的学习时间是").append(planDto.getTime())
                .append("不要有总结之类的话,直接给我一个计划即可,但是尽可能的详细一点,并以班主任和学生交谈的口吻进行温柔的回答");
        AIUtil aiUtil = new AIUtil(sparkClient);
        String aiAnswer = aiUtil.getAIAnswer(question.toString());
        //获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        //从redis中获取数据,为null的话就添加,不为null就在原本的基础上添加 List
        List<StudyPlanVo> studyPlanVos=(List<StudyPlanVo>)redisTemplate.opsForValue().get(key);
        StudyPlanVo studyPlanVo=new StudyPlanVo();
        studyPlanVo.setCreateTime(formattedDateTime);
        studyPlanVo.setPlan(aiAnswer);
        if (studyPlanVos == null) {studyPlanVos=new ArrayList<>();}
        studyPlanVos.add(studyPlanVo);
        redisTemplate.opsForValue().set("historyPlan"+currentId,studyPlanVos);
        studentMapper.addStudyPlan(currentId,aiAnswer,formattedDateTime);
        return aiAnswer;
    }

    @Override
    public List<StudyPlanVo> getHistoryPlan() {
        Long currentId = BaseContext.getCurrentId();
        String key ="historyPlan"+currentId;
        //从redis中获取
        List<StudyPlanVo> studyPlanVos=(List<StudyPlanVo>)redisTemplate.opsForValue().get(key);
        if(studyPlanVos==null){
            studyPlanVos=studentMapper.getHistoryPlan(currentId);
            redisTemplate.opsForValue().set(key,studyPlanVos);
        }
        return studyPlanVos;
    }

}
