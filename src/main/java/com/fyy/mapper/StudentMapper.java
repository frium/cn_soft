package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.StudentVo;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @date 2024-05-15 22:10:38
 * @description
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    StudentVo getStudentScores(String ID);
}
