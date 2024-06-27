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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @date 2024-05-15 22:14:13
 * @description
 */
@Slf4j
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
    RedisTemplate<Object, Object> redisTemplate;
    @Value("${jwt.key}")
    String key;
    @Value("${jwt.ttl}")
    long ttl;

    /**
     * description:登录
     *
     * @Param loginDTO: 登录dto
     */
    @Override
    public void getTeacher(LoginDTO loginDTO) {
        log.info("教师用户 {} 登录 {}", loginDTO.getUsername(), LocalDateTime.now());
        Teacher t;
        //用户名校验,判断是手机号登录还是身份证登录
        if (ValueCheckUtil.checkUsername(loginDTO.getUsername()).equals("phone")) {
            t = lambdaQuery().eq(Teacher::getPhone, loginDTO.getUsername()).eq(Teacher::getPassword, loginDTO.getPassword()).one();
        } else {
            t = lambdaQuery().eq(Teacher::getPersonalId, loginDTO.getUsername()).eq(Teacher::getPassword, loginDTO.getPassword()).one();
        }
        if (t != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("teacherId", t.getId());
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Authorization", JwtUtil.createToken(key, ttl, claims));
            httpSession.setAttribute("userRole", "teacher");
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
    public void addTeacher(RegisterDTO registerDTO) {
        if (!registerDTO.getVerify().equals(redisTemplate.opsForValue().get("verify")))
            throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        ValueCheckUtil.checkPhone(String.valueOf(registerDTO.getPhone()));
        ValueCheckUtil.checkPersonalId(registerDTO.getPersonalId());
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Teacher t = lambdaQuery().eq(Teacher::getPhone, registerDTO.getPhone()).or().eq(Teacher::getPersonalId, registerDTO.getPersonalId()).one();
        Student s = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getPhone, registerDTO.getPhone())
                .or().eq(Student::getPersonalId, registerDTO.getPersonalId()));
        if (t != null || s != null) {
            throw new MyException(StatusCodeEnum.USER_EXIST);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String classCode = uuid.substring(0, 6);
        Teacher teacher = new Teacher();
        teacher.setClassCode(classCode);
        teacher.setName("教师" + classCode);
        teacher.setSex("男");
        BeanUtils.copyProperties(registerDTO, teacher);
        save(teacher);
    }

    /**
     * description: 修改密码
     * @Param forgetPasswordDTO: 修改密码dto
     * @return: boolean 修改密码是否成功
     */
    @Override
    public boolean forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        return lambdaUpdate().eq(Teacher::getPhone, forgetPasswordDTO.getPhone())
                .eq(Teacher::getPersonalId, forgetPasswordDTO.getPersonalId()).
                set(Teacher::getPassword, forgetPasswordDTO.getPassword()).update();
    }

    /**
     * description:导出成绩表
     * @Param title: 成绩的标题
     * @return: void
     */
    @Override
    public void loadScores(String title) {
        Long currentId = BaseContext.getCurrentId();
        log.info("教师用户 {} 下载成绩表 {} {}", currentId, title, LocalDateTime.now());
        List<StudentScoreVO> allStudentsScores = scoreMapper.getAllStudentsScores(currentId, 0, 0, title, true);
        ExcelUtil.writeStudentScoresToExcel(allStudentsScores, response);
    }

    /**
     * description:通过excel导入学生成绩
     * @Param excel: excel表
     * @return: void
     */
    @Override
    public void uploadScores(MultipartFile excel) {
        Long currentId = BaseContext.getCurrentId();
        log.info("教师用户 {} 上传成绩表 {} {}", currentId, excel, LocalDateTime.now());
        try {
            List<Score> studentScoreVos = ExcelUtil.parseExcel(excel);
            scoreService.saveBatch(studentScoreVos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

