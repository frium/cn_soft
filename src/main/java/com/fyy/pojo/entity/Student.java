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
    private Long scoreId;
    private String sex;
    private String name;
    private Long ID;
    private String password;
    private Long phone;
    private String personalId;

}
