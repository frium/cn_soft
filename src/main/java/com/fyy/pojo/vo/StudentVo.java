package com.fyy.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentVo implements Serializable {
    private Long teacherId;
    private Long scoreId;
    private String sex;
    private String name;
    private Long ID;
    private String password;
    private Long phone;
    private String personalId;
    private Integer Chinese;
    private Integer Math;
    private Integer English;
    private Integer Physics;
    private Integer Chemistry;
    private Integer History;
    private Integer Politics;
    private Integer Geography;
    private Integer Biology;
    private String  createTime;
    private String  updateTime;
}