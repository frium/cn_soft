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
import com.fyy.pojo.entity.*;
import com.fyy.pojo.vo.PersonalInfoVO;
import com.fyy.pojo.vo.StudyPlanVO;
import com.fyy.service.StudentService;
import com.fyy.utils.AIUtil;
import com.fyy.utils.JwtUtil;
import com.fyy.utils.ValueCheckUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    /**
     * description:登录
     *
     * @Param loginDTO: 登录dto
     */
    @Override
    public void getStudent(LoginDTO loginDTO) {
        log.info("学生用户 {} 登录 {}", loginDTO.getUsername(), LocalDateTime.now());
        Student s;
        //用户名校验,判断是手机号登录还是身份证登录
        if (ValueCheckUtil.checkUsername(loginDTO.getUsername()).equals("phone")) {
            s = lambdaQuery().eq(Student::getPhone, loginDTO.getUsername()).eq(Student::getPassword, loginDTO.getPassword()).one();
        } else {
            s = lambdaQuery().eq(Student::getPersonalId, loginDTO.getUsername()).eq(Student::getPassword, loginDTO.getPassword()).one();
        }
        if (s != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("studentId", s.getId());
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Authorization", JwtUtil.createToken(key, ttl, claims));//设置token到请求头中
            httpSession.setAttribute("userRole", "student");
        } else {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
    }

    /**
     * description:注册
     * @Param registerDTO: 注册dto
     * @return: void
     */
    @Override
    public void addStudent(RegisterDTO registerDTO) {
        //数据校验
        if (!registerDTO.getVerify().equals(redisTemplate.opsForValue().get("verify")))
            throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        ValueCheckUtil.checkPhone(String.valueOf(registerDTO.getPhone()));
        ValueCheckUtil.checkPersonalId(registerDTO.getPersonalId());
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Student s = lambdaQuery().eq(Student::getPhone, registerDTO.getPhone()).or().eq(Student::getPersonalId, registerDTO.getPersonalId()).one();
        Teacher t = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>()
                .eq(Teacher::getPhone, registerDTO.getPhone())
                .or().eq(Teacher::getPersonalId, registerDTO.getPersonalId()));
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
        BeanUtils.copyProperties(registerDTO, student);
        student.setStudentId(yearPart + randomPart);
        student.setName("学生" + yearPart + randomPart);
        student.setSex("男");
        save(student);
    }

   /**
    * description: 修改密码
    * @Param forgetPasswordDTO: 修改密码dto
    * @return: boolean 修改密码是否成功
   */
    @Override
    public boolean forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        return lambdaUpdate().eq(Student::getPhone, forgetPasswordDTO.getPhone())
                .eq(Student::getPersonalId, forgetPasswordDTO.getPersonalId())
                .set(Student::getPassword, forgetPasswordDTO.getPassword()).update();
    }


    /**
     * description:学生添加老师
     * @Param classCode:教师班级码
     * @return: void
     */
    @Override
    public void addTeacher(String classCode) {
        Long currentId = BaseContext.getCurrentId();
        log.info("学生用户 {} 添加老师 {} {}", currentId, classCode,LocalDateTime.now());
        //先在数据库中查找看classCode是否正确
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teacher::getClassCode, classCode);
        Teacher teacher = teacherMapper.selectOne(lambdaQueryWrapper);
        if (teacher == null) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        studentMapper.addTeacher(currentId, classCode);
    }

    /**
     * description:获取个人主页的信息
     * @return: com.fyy.pojo.vo.PersonalInfoVO
     */
    @Override
    public PersonalInfoVO getPersonalInfo() {
        Long currentId = BaseContext.getCurrentId();
        log.info("学生用户 {} 获取个人信息  {}", currentId,LocalDateTime.now());
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getId, currentId));
        PersonalInfoVO personalInfoVo = new PersonalInfoVO();
        BeanUtils.copyProperties(student, personalInfoVo);
        Teacher teacher = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getId, student.getTeacherId()));
        if (teacher != null) {
            personalInfoVo.setTeacherName(teacher.getName());
        }
        return personalInfoVo;
    }

    /**
     * description:修改个人主页的信息
     * @Param personalInfoDTO: 个体信息dto
     * @return: void
     */
    @Override
    public void modifyPersonalInfo(PersonalInfoDTO personalInfoDTO) {
        Long currentId = BaseContext.getCurrentId();
        log.info("学生用户 {} 修改个人信息 {}", currentId,LocalDateTime.now());
        ValueCheckUtil.checkPhone(String.valueOf(personalInfoDTO.getPhone()));
        ValueCheckUtil.checkPersonalId(personalInfoDTO.getPersonalId());
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Student s = lambdaQuery().eq(Student::getPhone, personalInfoDTO.getPhone()).or().eq(Student::getPersonalId, personalInfoDTO.getPersonalId()).one();
        Teacher t = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>()
                .eq(Teacher::getPhone, personalInfoDTO.getPhone())
                .or().eq(Teacher::getPersonalId, personalInfoDTO.getPersonalId()));
        if (s != null && t != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
        Student student = new Student();
        BeanUtils.copyProperties(personalInfoDTO, student);
        student.setId(currentId);
        if (studentMapper.updateById(student) == 0) {
            throw new MyException(StatusCodeEnum.FAIL);
        }
    }


    /**
     * description:定制学习计划
     * @Param planDTO: 计划dto
     * @return: java.lang.String
     */
    @Override
    public StudyPlanVO customizedPlan(PlanDTO planDTO) {
        //获取当前学生的近五次成绩
        Long currentId = BaseContext.getCurrentId();
        log.info("学生用户 {} 定制学习计划 {}", currentId,LocalDateTime.now());
        String key = "historyPlan" + currentId;
        List<Score> scores = scoreMapper.getStudentScoresLimit5(currentId);//获取需要定制计划的科目的成绩
        StringBuilder question = new StringBuilder("你是我的班主任,接下来我需要你帮助我去定制一个学习计划,你只需要考虑" + planDTO.getSubjects() +
                "这科目,其他的不需要你考虑,这是我最近几次的这几科的考试成绩");
        for (Score score : scores) {
            question.append(score);
        }
        question.append("这是一些详细的辅助你帮助我的信息").append(planDTO.getTarget()).append("我想要达到的程度是").append(planDTO.getLevel())
                .append("我预定的学习时间是").append(planDTO.getTime())
                .append("不要有总结之类的话,以亲爱的同学为开头," +
                        "直接给我一个计划即可,但是尽可能的详细一点,并以班主任和学生交谈的口吻进行温柔的回答");
        AIUtil aiUtil = new AIUtil(sparkClient);
        String aiAnswer = aiUtil.getAIAnswer(question.toString());
        //获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        //从redis中获取数据,为null的话就添加,不为null就在原本的基础上添加 List
        List<StudyPlanVO> studyPlanVOS = (List<StudyPlanVO>) redisTemplate.opsForValue().get(key);
        StudyPlanVO studyPlanVo = new StudyPlanVO();
        studyPlanVo.setCreateTime(formattedDateTime);
        studyPlanVo.setPlan(aiAnswer);
        if (studyPlanVOS == null) {
            studyPlanVOS = new ArrayList<>();
        }
        studyPlanVo.setTitle("学习方案("+(studyPlanVOS.size()+1)+")");
        studyPlanVo.setStatus(false);
        studyPlanVOS.add(studyPlanVo);
        redisTemplate.opsForValue().set("historyPlan" + currentId, studyPlanVOS);
        studentMapper.addStudyPlan(currentId,studyPlanVo);

        return studyPlanVo;
    }

    /**
     * description:获取历史定制的学习计划
     * @return: java.util.List<com.fyy.pojo.vo.StudyPlanVO>
     */
    @Override
    public List<StudyPlanVO> getHistoryPlan() {
        Long currentId = BaseContext.getCurrentId();
        log.info("学生用户 {} 获取历史的学习计划 {}", currentId,LocalDateTime.now());
        String key = "historyPlan" + currentId;
        //从redis中获取
        List<StudyPlanVO> studyPlanVOs = (List<StudyPlanVO>) redisTemplate.opsForValue().get(key);
        if (studyPlanVOs == null) {
            studyPlanVOs = studentMapper.getHistoryPlan(currentId);
            redisTemplate.opsForValue().set(key, studyPlanVOs);
        }
        return studyPlanVOs;
    }

}
