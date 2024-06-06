package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.StudyPlanVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @date 2024-05-15 22:10:38
 * @description
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    void addTeacher(Long id,String classCode);
    void addStudyPlan(Long id,String plan,String time);
    List<StudyPlanVO> getHistoryPlan(Long id);
}
