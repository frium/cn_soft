package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.dto.PageDTO;
import com.fyy.pojo.dto.ScoreDTO;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentScoreVO;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:50:27
 * @description
 */
public interface ScoreService extends IService<Score> {
    //获取所有学生的分数
    List<StudentScoreVO> getAllStudentsScores(PageDTO pageDto);
    //获取所有考试的标题
    List<String> getAllScoresTitle(PageDTO pageDto);
    //添加学生的分数
    void addStudentScore(ScoreDTO score);
    //获取学生成绩
    List<StudentScoreVO> getMyAllScores(PageDTO pageDTO);
}
