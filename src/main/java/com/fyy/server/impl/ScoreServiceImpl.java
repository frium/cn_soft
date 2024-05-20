package com.fyy.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.pojo.entity.Score;
import com.fyy.mapper.ScoreMapper;
import com.fyy.server.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Score getAllScores(String ID) {
        
        return scoreMapper.selectById(ID);
    }
}
