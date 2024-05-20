package com.fyy.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @date 2024-05-15 17:23:03
 * @description
 */
@Data
public class Teacher implements Serializable {
    private String sex;
    private String name;
    private Long ID;
    private String password;
    private Long phone;
    private String personalId;
    private String classCode;
}
