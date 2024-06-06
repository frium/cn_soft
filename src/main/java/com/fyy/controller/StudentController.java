package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.PersonalInfoDTO;
import com.fyy.pojo.dto.PlanDTO;
import com.fyy.pojo.vo.PersonalInfoVO;
import com.fyy.pojo.vo.StudentScoreVO;
import com.fyy.pojo.vo.StudyPlanVO;
import com.fyy.service.ScoreService;
import com.fyy.service.StudentService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生界面
 * @date 2024-05-16 17:26:49
 * @description
 */
@Validated
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ScoreService scoreService;

    @ApiOperation("查询所有成绩")
    @GetMapping("/getAllScores")
    public R<List<StudentScoreVO>> getAllScores() {
        return R.success(scoreService.getStudentScores());
    }

    @ApiOperation("添加老师")
    @PostMapping("/addTeacher")
    public R<String> addTeacher(@Valid @RequestParam String classCode) {
        studentService.addTeacher(classCode);
        return R.success();
    }

    @ApiOperation("修改个人信息")
    @PutMapping("/modifyPersonalInfo")
    public R<?> modifyPersonalInfo(@Valid @RequestBody PersonalInfoDTO personalInfoDto) {
        studentService.modifyPersonalInfo(personalInfoDto);
        return R.success();
    }

    @ApiOperation("获取个人信息")
    @GetMapping("/getPersonalInfo")
    public R<PersonalInfoVO> getPersonalInfo() {
        return R.success(studentService.getPersonalInfo());
    }

    @ApiOperation("定制学习计划")
    @PostMapping("/customizedPlan")
    public R<?> customizedPlan(@Valid @RequestBody PlanDTO planDto) {
        return R.success(studentService.customizedPlan(planDto));
    }

    @ApiOperation("获取历史定制的学习计划")
    @GetMapping("/getHistoryPlan")
    public R<List<StudyPlanVO>> getHistoryPlan() {
        return R.success(studentService.getHistoryPlan());
    }
}
