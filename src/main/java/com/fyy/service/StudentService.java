package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.dto.*;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.PersonalInfoVo;
import com.fyy.pojo.vo.StudyPlanVo;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:31:41
 * @description
 */
public interface StudentService extends IService<Student> {
    Student getStudent(LoginDto student);
    void addStudent(RegisterDto student);
    void addTeacher(String classCode);
    PersonalInfoVo getPersonalInfo();
    void modifyPersonalInfo(PersonalInfoDto personalInfoDto);
    String forgetPassword(ForgetPasswordDto forgetPasswordDto);
    String customizedPlan(PlanDto planDto);
    List<StudyPlanVo> getHistoryPlan();
}
