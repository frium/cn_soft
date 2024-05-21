package com.fyy.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.mapper.TeacherMapper;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.entity.Teacher;
import com.fyy.pojo.vo.StudentVo;
import com.fyy.server.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 *
 * @date 2024-05-15 22:14:13
 * @description
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public Teacher getTeacher(Teacher teacher) {
        return lambdaQuery().eq(Teacher::getPhone, teacher.getPhone()).eq(Teacher::getPassword, teacher.getPassword()).one();
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        //查询数据库中是否有相同的身份证或者电话,有的话直接pass
        Teacher t = lambdaQuery().eq(Teacher::getPhone, teacher.getPhone()).or().eq(Teacher::getPersonalId, teacher.getPersonalId()).one();
        if (t != null) return false;
        //给老师设置一个六位数的班级码
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 截取 UUID 的前六位作为随机码
        String classCode = uuid.substring(0, 6);
        teacher.setClassCode(classCode);
        save(teacher);
        return true;
    }




}

