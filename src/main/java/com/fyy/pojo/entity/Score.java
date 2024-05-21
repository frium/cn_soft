package com.fyy.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @date 2024-05-16 17:41:17
 * @description
 */
@Data
public class Score implements Serializable {
    private Long ID;
    private Long studentId;
    private Integer Chinese;
    private Integer Math;
    private Integer English;
    private Integer Physics;
    private Integer Chemistry;
    private Integer History;
    private Integer Politics;
    private Integer Geography;
    private Integer Biology;
    private String createTime;
    private String updateTime;
    private String title;
}
