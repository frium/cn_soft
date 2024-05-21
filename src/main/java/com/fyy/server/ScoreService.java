package com.fyy.server;

import com.baomidou.mybatisplus.extension.service.IService;
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
    List<StudentVo> getAllStudentsScores(String ID,int page,int pageSize,String title);
    //添加学生的分数
    boolean addStudentScore(Score score);
    //获取学生成绩
    List<Score> getStudentScores(String ID);
}
