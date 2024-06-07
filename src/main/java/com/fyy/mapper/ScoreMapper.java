package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentScoreVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:49:53
 * @description
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    List<StudentScoreVO> getAllStudentsScores(Long id, int offset, int pageSize, String title, boolean fetchAll);
    List<StudentScoreVO> getMyAllScores(Long id, int offset, int pageSize,String title);
    List<String> getAllScores(int offset, int pageSize,String title);
    List<Score> getStudentScoresLimit5(Long id, List<String> subjects);
}
