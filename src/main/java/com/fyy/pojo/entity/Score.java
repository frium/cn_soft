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
    private Long id;
    private Long studentId;
    private Float chinese;
    private Float math;
    private Float english;
    private Float physics;
    private Float chemistry;
    private Float history;
    private Float politics;
    private Float geography;
    private Float biology;
    private String createTime;
    private String updateTime;
    private String title;
}
