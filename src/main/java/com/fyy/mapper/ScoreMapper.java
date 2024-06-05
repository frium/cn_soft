package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentScoreVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:49:53
 * @description
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    List<StudentScoreVo> getAllStudentsScores(Long id, int offset, int pageSize, String title,boolean fetchAll);
    List<StudentScoreVo> getStudentScores(Long id);
    List<String> getAllScores(int offset, int pageSize,String title);
    List<Score> getStudentScoresLimit5(Long id, List<String> subjects);
}
