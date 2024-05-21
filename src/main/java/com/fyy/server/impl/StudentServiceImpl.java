package com.fyy.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.mapper.StudentMapper;
import com.fyy.pojo.entity.Student;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.StudentVo;
import com.fyy.server.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @date 2024-05-16 17:32:08
 * @description
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student getStudent(Student student) {
        return lambdaQuery().eq(Student::getPhone, student.getPhone()).eq(Student::getPassword, student.getPassword()).one();
    }

    @Override
    public boolean addStudent(Student student) {
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Student s = lambdaQuery().eq(Student::getPhone, student.getPhone()).or().eq(Student::getPersonalId, student.getPersonalId()).one();
        if (s != null) return false;
        save(student);
        return true;
    }



    @Override
    public boolean addTeacher(String ID, String classCode) {
       return studentMapper.addTeacher(ID,classCode);//怎么判断成功和失败呢?
    }

}
