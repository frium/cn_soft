package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.dto.*;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.PersonalInfoVO;
import com.fyy.pojo.vo.StudyPlanVO;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:31:41
 * @description
 */
public interface StudentService extends IService<Student> {
    Student getStudent(LoginDTO student);
    void addStudent(RegisterDTO student);
    void addTeacher(String classCode);
    PersonalInfoVO getPersonalInfo();
    void modifyPersonalInfo(PersonalInfoDTO personalInfoDto);
    String forgetPassword(ForgetPasswordDTO forgetPasswordDto);
    String customizedPlan(PlanDTO planDto);
    List<StudyPlanVO> getHistoryPlan();
}
