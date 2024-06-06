package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.PageDto;
import com.fyy.pojo.dto.ScoreDto;
import com.fyy.pojo.vo.StudentScoreVo;
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
    public R<List<StudentScoreVo>> getAllStudentsScores(@Valid @RequestBody PageDto pageDto) {
        return R.success(scoreService.getAllStudentsScores(pageDto));
    }
    @ApiOperation("获取所有考试title")
    @PostMapping("/getAllScores")
    public R<List<String>> getAllScores(@Valid @RequestBody PageDto pageDto){
        return R.success(scoreService.getAllScores(pageDto));
    }

    @ApiOperation("通过学号添加学生成绩")
    @PostMapping("/addStudentScores")
    public R<String> addStudentScores(@Valid  @RequestBody ScoreDto score) {
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
}
