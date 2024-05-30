package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.StudentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @date 2024-05-16 17:28:30
 * @description
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
}
