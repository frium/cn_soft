package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.PageDTO;
import com.fyy.pojo.dto.ScoreDTO;
import com.fyy.pojo.vo.StudentScoreVO;
import com.fyy.service.ScoreService;
import com.fyy.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 教师界面
 * @date 2024-05-15 22:06:14
 * @description
 */
@Validated
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    TeacherService teacherService;
    @ApiOperation("获取指定的考试中所有学生的成绩")
    @GetMapping("/getAllStudentsScores")
    public R<List<StudentScoreVO>> getAllStudentsScores(@Valid @RequestBody PageDTO pageDto) {
        return R.success(scoreService.getAllStudentsScores(pageDto));
    }
    @ApiOperation("获取所有考试title")
    @PostMapping("/getAllScoresTitle")
    public R<List<String>> getAllScoresTitle(@Valid @RequestBody PageDTO pageDto){
        return R.success(scoreService.getAllScoresTitle(pageDto));
    }

    @ApiOperation("删除考试成绩")
    @PostMapping("/deleteScore")
    public R<?> deleteScore(){
        return null;
    }
    @ApiOperation("通过学号添加学生成绩")
    @PostMapping("/addStudentScores")
    public R<String> addStudentScores(@Valid  @RequestBody ScoreDTO score) {
        scoreService.addStudentScore(score);
        return R.success();
    }


    @ApiOperation("通过导入excel添加学生的成绩")
    @PostMapping("/uploadScores")
    public R<String>uploadScores(@Valid  @RequestParam MultipartFile excel){
        teacherService.uploadScores(excel);
        return R.success();
    }

    @ApiOperation("将当次考试的成绩导出excel")
    @GetMapping("/loadScores")
    public R<String>loadScores(String title){
        teacherService.loadScores(title);
        return R.success();
    }

    @ApiOperation("查看自己的所有学生")
    @GetMapping("/getAllStudents")
    public R<?> getAllStudents(){
        return null;
    }

    @ApiOperation("删除学生")
    @DeleteMapping("/deleteStudent")
    public R<?>deleteStudent(){
        return null;
    }
}
