package com.fyy.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @date 2024-05-15 17:18:20
 * @description
 */
@Data
public class Student implements Serializable {
    private Long teacherId;
    private String sex;
    private String name;
    private Long id;
    private String password;
    private String phone;
    private String personalId;
    private String studentId;
}
