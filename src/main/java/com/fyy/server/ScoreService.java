package com.fyy.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.entity.Score;

/**
 *
 * @date 2024-05-16 17:50:27
 * @description
 */
public interface ScoreService extends IService<Score> {
    //查询所有分数
    Score getAllScores(String ID);
}
