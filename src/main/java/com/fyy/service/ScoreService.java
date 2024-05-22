package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.dto.PageDto;
import com.fyy.pojo.dto.ScoreDto;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentVo;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:50:27
 * @description
 */
public interface ScoreService extends IService<Score> {
    //获取所有学生的分数
    List<StudentVo> getAllStudentsScores(PageDto pageDto);
    //添加学生的分数
    void addStudentScore(ScoreDto score);
    //获取学生成绩
    List<Score> getStudentScores(String ID);
}
