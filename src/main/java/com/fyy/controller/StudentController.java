package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.PersonalInfoDto;
import com.fyy.pojo.dto.PlanDto;
import com.fyy.pojo.vo.PersonalInfoVo;
import com.fyy.pojo.vo.StudentScoreVo;
import com.fyy.pojo.vo.StudyPlanVo;
import com.fyy.service.ScoreService;
import com.fyy.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生界面
 * @date 2024-05-16 17:26:49
 * @description
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ScoreService scoreService;

    @ApiOperation("查询所有成绩")
    @GetMapping("/getAllScores")
    public R<List<StudentScoreVo>> getAllScores() {
        return R.success(scoreService.getStudentScores());
    }

    @ApiOperation("添加老师")
    @PostMapping("/addTeacher")
    public R<String> addTeacher(@RequestParam String classCode) {
        studentService.addTeacher(classCode);
        return R.success();
    }

    @ApiOperation("修改个人信息")
    @PutMapping("/modifyPersonalInfo")
    public R<?> modifyPersonalInfo(@RequestBody PersonalInfoDto personalInfoDto) {
        studentService.modifyPersonalInfo(personalInfoDto);
        return R.success();
    }

    @ApiOperation("获取个人信息")
    @GetMapping("/getPersonalInfo")
    public R<PersonalInfoVo> getPersonalInfo() {
        return R.success(studentService.getPersonalInfo());
    }

    @ApiOperation("定制学习计划")
    @PostMapping("/customizedPlan")
    public R<?> customizedPlan(@RequestBody PlanDto planDto) {
        return R.success(studentService.customizedPlan(planDto));
    }

    @ApiOperation("获取历史定制的学习计划")
    @GetMapping("/getHistoryPlan")
    public R<List<StudyPlanVo>> getHistoryPlan() {
        return R.success(studentService.getHistoryPlan());
    }
}
