package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.pojo.entity.Score;
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
    List<StudentVo> getAllStudentsScores(String ID,int offset,int page,String title);
    List<Score> getStudentScores(String ID);
}
