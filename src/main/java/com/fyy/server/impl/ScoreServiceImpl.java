package com.fyy.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.pojo.entity.Score;
import com.fyy.mapper.ScoreMapper;
import com.fyy.pojo.vo.StudentVo;
import com.fyy.server.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:50:53
 * @description
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score>implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;
    @Override
    public List<StudentVo> getAllStudentsScores(String ID,int page,int pageSize,String title) {
        int offset=(pageSize-1)*page;
        return scoreMapper.getAllStudentsScores(ID,offset,page,title);
    }
    @Override
    public boolean addStudentScore(Score score) {
        return save(score);
    }
    @Override
    public List<Score> getStudentScores(String ID) {
        return scoreMapper.getStudentScores(ID);
    }
}
