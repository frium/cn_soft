package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.pojo.dto.PageDto;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentScoreVo;
import com.fyy.pojo.vo.StudentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:49:53
 * @description
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    List<StudentScoreVo> getAllStudentsScores(Long ID, int offset, int pageSize, String title);
    List<Score> getStudentScores(Long ID);
    List<String> getAllScores(int offset, int pageSize);
}
