package com.fyy.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.vo.StudentVo;

/**
 *
 * @date 2024-05-16 17:31:41
 * @description
 */
public interface StudentService extends IService<Student> {
    //获取学生对象
    Student getStudent(Student student);
    //添加学生
    boolean addStudent(Student student);
    //获取学生成绩
    StudentVo getStudentScores(String ID);
}
