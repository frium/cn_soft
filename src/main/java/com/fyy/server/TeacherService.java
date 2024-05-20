package com.fyy.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.entity.Teacher;

/**
 *
 * @date 2024-05-15 22:13:12
 * @description
 */
public interface TeacherService extends IService<Teacher> {
    //老师账号登录
    Teacher getTeacher(Teacher teacher);
    boolean addTeacher(Teacher teacher);

}
